package com.candela.workflow.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

import com.candela.workflow.util.DateHandle;
import com.engine.kq.biz.KQFormatData;
import com.engine.kq.biz.KQWorkTime;
import com.engine.kq.entity.TimeScopeEntity;
import com.engine.kq.entity.WorkTimeEntity;
import com.weaver.general.BaseBean;

public class OutTimeAction extends BaseBean implements Action {
	private String beginCard;
	private String endCard;
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
        String begindate = map.get("wcksrq");
        String enddate = map.get("wcjsrq");
        String sqr = map.get("sqr");
        String begintime = map.get("wckssj");
        String endtime = map.get("wcjssj");
        String sc = map.get("sc");
        
        DateHandle dh1 = new DateHandle();
        dh1.toDate(begindate);
        dh1.toTime(begintime);
        DateHandle dh2 = new DateHandle();
        dh2.toDate(enddate);
        dh2.toTime(endtime);
        
        beginCard = "09:30:00";
        endCard = "18:30:00";
        
        
        
        LocalDateTime  sdt1 = new LocalDateTime(dh1.getYear(),dh1.getMonth(),dh1.getDay(),9,30,0);
        LocalDateTime  sdt2 = new LocalDateTime(dh1.getYear(),dh1.getMonth(),dh1.getDay(),18,30,0);
        LocalDateTime  ldt1 = new LocalDateTime(dh1.getYear(),dh1.getMonth(),dh1.getDay(),dh1.getHour(),dh1.getMinute(),dh1.getSecond());
        LocalDateTime  ldt2 = new LocalDateTime(dh2.getYear(),dh2.getMonth(),dh2.getDay(),dh2.getHour(),dh2.getMinute(),dh2.getSecond());
        
        if(ldt1.getHourOfDay()<=sdt1.getHourOfDay()){
        	  sql = "insert into hrmschedulesign(userId,userType,signtype,signDate,signTime,isInCom,signFrom) values ("+sqr+",1,1,'"+begindate+"','"+beginCard+"',1,'importExcel')";
              rs.execute(sql);
              log.info("sql:>>>>>>>>>>>"+sql);

        }
        if(ldt2.getHourOfDay()>=sdt2.getHourOfDay()){
        	sql = "insert into hrmschedulesign(userId,userType,signtype,signDate,signTime,isInCom,signFrom) values ("+sqr+",1,2,'"+enddate+"','"+endCard+"',1,'importExcel')";
        	rs.execute(sql);
        	log.info("sql:>>>>>>>>>>>"+sql);
        	
        }
        kqFormatData.formatKqDate(sqr,begindate);

        
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
	}

}
