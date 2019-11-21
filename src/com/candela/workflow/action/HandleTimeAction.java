package com.candela.workflow.action;

import java.util.HashMap;
import java.util.Map;

import com.candela.workflow.util.SqlHandler;
import com.candela.workflow.util.SqlHandlerImpl;
import org.apache.log4j.Logger;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

import com.engine.kq.biz.KQFormatData;
import com.weaver.general.BaseBean;

public class HandleTimeAction extends BaseBean implements Action {
	 private Map<String, String> getPropertyMap(Property[] property){
	        Map<String, String> m = new HashMap<String, String>();
	        for(Property p : property){
	            m.put( p.getName(), Util.null2String(p.getValue()) );
	        }
	        return m;
	    }
	    Logger log=Logger.getLogger(this.getClass());


	@Override
	public String execute(RequestInfo requestinfo) {
		  //取主表数据
        Property[] properties = requestinfo.getMainTableInfo().getProperty();
        Map<String,String> map = this.getPropertyMap(properties);
        RecordSet rs = new RecordSet();
        KQFormatData kqFormatData = new KQFormatData();
        String sql = "";
        String date = map.get("rq");
        String sqr = map.get("sqr");
        String begin = map.get("sbdksj")+":00";
        String end = map.get("xbdksj")+":00";
        Map<String, String> datas = new HashMap<>();
        datas.put("userid",sqr);
        datas.put("signdate",date);
        datas.put("signtime",begin);

        SqlHandler sqlHandler = new SqlHandlerImpl();

        sqlHandler.create("hrmschedulesign",datas);

        if(!"".equals(map.get("sbdksj"))){
        	
        	/*sql = "insert into hrmschedulesign(userId,userType,signtype,signDate,signTime,isInCom,signFrom) values ("+sqr+",1,1,'"+date+"','"+begin+"',1,'importExcel')";
        	rs.execute(sql);
        	log.info("sql:>>>>>>>>>>>"+sql);*/
        }
        if(!"".equals(map.get("xbdksj"))){
        	
        	/*sql = "insert into hrmschedulesign(userId,userType,signtype,signDate,signTime,isInCom,signFrom) values ("+sqr+",1,2,'"+date+"','"+end+"',1,'importExcel')";
        	rs.execute(sql);
        	log.info("sql:>>>>>>>>>>>"+sql);*/
        }
        kqFormatData.formatKqDate(sqr,date);
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
	}

}
