package com.candela.workflow.action;

import com.engine.kq.biz.KQFormatData;
import com.engine.kq.biz.KQWorkTime;
import com.engine.kq.entity.TimeScopeEntity;
import com.engine.kq.entity.WorkTimeEntity;
import com.weaver.general.BaseBean;
import com.weaver.general.Util;
import org.apache.log4j.Logger;
import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author linbin
 * @Create 2019-08-16-15:17
 */
public class InitTimeAction extends BaseBean implements Action {

	private String sql  = "";
    private Map<String, String> getPropertyMap(Property[] property){
        Map<String, String> m = new HashMap<String, String>();
        for(Property p : property){
            m.put( p.getName(), Util.null2String(p.getValue()) );
        }
        return m;
    }
    Logger log=Logger.getLogger(this.getClass());

    public String execute(RequestInfo requestinfo) {

        System.out.println("进入Action requestid=" + requestinfo.getRequestid());
        String requestid = requestinfo.getRequestid();  //请求ID



        //取主表数据
        Property[] properties = requestinfo.getMainTableInfo().getProperty();
        Map<String,String> map = this.getPropertyMap(properties);
        RecordSet rs = new RecordSet();

        String oazh = map.get("oazh");
        String zj = map.get("zj");
        String rzrq = map.get("rzrq");
        String deptA = map.get("yjbm");
        String deptB = map.get("ejbm");
        String deptC = map.get("sjbm");
        int seclevel = 0;
        String id = "";
        String deptid = "";
        String manager = "";
        String leader = "";
        KQFormatData kqFormatData = new KQFormatData();

        //获取人员id
         sql  = "select id,departmentid from hrmresource where loginid = "+oazh;
        rs.execute(sql);
        if(rs.next()){
            id = rs.getString(1);
            deptid = rs.getString(2);
            log.info("sql:>>>>>>>>>>>"+sql);
        }
        KQWorkTime kqWorkTime = new KQWorkTime();
        WorkTimeEntity workTime = kqWorkTime.getWorkTime(id,rzrq);
        List<TimeScopeEntity> wlist = workTime.getWorkTime();
        String beginTime = wlist.get(0).getBeginTime()+":00";
        
        //更新入职日期上班打卡时间
        sql = "insert into hrmschedulesign(userId,userType,signDate,signTime,isInCom,signFrom) values ("+id+",1,'"+rzrq+"','"+beginTime+"',1,'importExcel')";
        rs.execute(sql);
        log.info("sql:>>>>>>>>>>>"+sql);
        
        //刷新考勤缓存
        kqFormatData.formatKqDate(id,rzrq);
        
       //获取职级
        int joblevel = Integer.parseInt(zj);

       switch(joblevel){
           case 2:
               seclevel = 20;
               break;
           case 3:
               seclevel = 30;
               break;
           case 4:
               seclevel = 40;
               break;
           case 5 :
               seclevel = 80;
               break;
           case 6:
               seclevel=90;
               break;
       }
       //更新职级
       sql = "update hrmresource set seclevel = "+seclevel+" ,companystartdate =  '"+rzrq+"' where id = "+id;
       rs.execute(sql);
        log.info("sql:>>>>>>>>>>>"+sql);





        //控制流程流转，增加以下两行，流程不会
        // 下流转，表单上显示返回的自定义错误信息
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
    }


}
