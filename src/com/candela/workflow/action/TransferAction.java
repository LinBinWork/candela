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
public class TransferAction extends BaseBean implements Action {
	
    private Map<String, String> getPropertyMap(Property[] property){
        Map<String, String> m = new HashMap<String, String>();
        for(Property p : property){
            m.put( p.getName(), Util.null2String(p.getValue()) );
        }
        return m;
    }
    private String value = "";

    Logger log=Logger.getLogger(this.getClass());

    public String execute(RequestInfo requestinfo) {

        System.out.println("进入Action requestid=" + requestinfo.getRequestid());
        String requestid = requestinfo.getRequestid();  //请求ID
        RecordSet rs = new RecordSet();
        String sql = "";

       


        //取主表数据
        Property[] properties = requestinfo.getMainTableInfo().getProperty();
        Map<String,String> map = this.getPropertyMap(properties);
        String yjbm = map.get("yjbm");
        String ejbm = map.get("ejbm");
        String sjbm = map.get("sjbm");
        String dryjbm = map.get("dryjbm");
        String drejbm = map.get("drejbm");
        String drsjbm = map.get("drsjbm");
        
        //原部门
        if(!"".equals(sjbm)){
        	sql = "update  formtable_main_31 set ybm  = "+sjbm+" where requestid = "+requestid;
        }
        if("".equals(sjbm) && !"".equals(ejbm)){
        	sql = "update  formtable_main_31 set ybm  = "+ejbm+" where requestid = "+requestid;
        }
        if("".equals(sjbm) && "".equals(ejbm)){
        	sql = "update  formtable_main_31 set ybm  = "+yjbm+" where requestid = "+requestid;
        }
        value = sql;
        rs.execute(sql);
        //调入部门
        if(!"".equals(drsjbm)){
        	sql = "update  formtable_main_31 set drbm  = "+drsjbm+" where requestid = "+requestid;
        }
        if("".equals(drsjbm) && !"".equals(drejbm)){
        	sql = "update  formtable_main_31 set drbm  = "+drejbm+" where requestid = "+requestid;
        }
        if("".equals(drsjbm) && "".equals(drejbm)){
        	sql = "update  formtable_main_31 set drbm  = "+dryjbm+" where requestid = "+requestid;
        }
        value = sql;
        rs.execute(sql);



        //控制流程流转，增加以下两行，流程不会向下流转，表单上显示返回的自定义错误信息
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
    }


}
