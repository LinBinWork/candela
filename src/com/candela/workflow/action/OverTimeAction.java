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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author linbin
 * @Create 2019-08-16-15:17
 */
public class OverTimeAction extends BaseBean implements Action {
	
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
        RecordSet rs = new RecordSet();
        KQFormatData kqFormatData = new KQFormatData();

       


        //取主表数据
        Property[] properties = requestinfo.getMainTableInfo().getProperty();
        Map<String,String> map = this.getPropertyMap(properties);
        String sqr = map.get("sqr");
        String jbksrq = map.get("jbksrq");
        String jbjsrq = map.get("jbjsrq");
        String jblx = map.get("jblx");
        String sjsc = map.get("sjsc");
        String sjjbjssj = map.get("sjjbjssj");
        String sjjbkssj = map.get("sjjbkssj");
        String hour = sjjbjssj.substring(1,2);
        int h = Integer.parseInt(hour);
        String year = jbksrq.substring(0,4);
        String month = jbksrq.substring(5,7);
        String sql = "";
        
        log.info("year: "+year+"---month: "+month);
        
        String beginTime = "08:30:00";
        String endTime = "18:30:00";

     if("3".equals(jblx)){
         sql = "insert into hrmschedulesign(userId,userType,signDate,signTime,isInCom,signFrom) values ("+sqr+",1,'"+jbjsrq+"','"+beginTime+"',1,'importExcel')";
         rs.execute(sql);
         System.out.println(sql);
         log.info(sql);



         if(h>=6){
             sql = "insert into hrmschedulesign(userId,userType,signDate,signTime,isInCom,signFrom) values ("+sqr+",1,'"+jbjsrq+"','"+endTime+"',1,'importExcel')";
             rs.execute(sql);
             System.out.println(sql);
             log.info(sql);
         }
         kqFormatData.formatKqDate(sqr,jbjsrq);

     }
     else{
    	 sql = "insert into kq_balanceofleave(leaveRulesId,resourceId,belongYear,extraAmount,status,belongMonth,overtimeType,effectiveDate,isDelete)"+
    		 "values(5,"+sqr+",'"+year+"',"+sjsc+",0,'"+month+"',4,'"+jbksrq+"','0')";
         //sql = "update kq_balanceofleave set baseAmount = baseAmount + "+sjsc+" where resourceId = "+sqr+" and leaveRulesId = 5";
         rs.execute(sql);
         log.info(sql);
     }



        //控制流程流转，增加以下两行，流程不会向下流转，表单上显示返回的自定义错误信息
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
    }


}
