package com.candela.workflow.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;

import com.candela.workflow.bean.Person;

public class AnnualLeaveJob  extends  BaseCronJob {
	
	private String sql = null;
	private List<Person> list = new ArrayList<Person>();
	
	Log log = LogFactory.getLog(this.getClass());
	private double vacation = 0;
	
    String format = "yyyyMMdd ";
	
    //String requestId = request.getRequestid();
    
    SimpleDateFormat form = new SimpleDateFormat("yyyy");
	
	String nowtime = form.format(new Date());

	@Override
	public void execute() {
		
		
		sql = "select id,lastname,companystartdate,DATEDIFF(D,companystartdate,GETDATE()) days,locationid from hrmresource where status <> 5"+
		" and DATEDIFF(d,companystartdate,GETDATE()) > 180 ";

		RecordSet rs = new RecordSet();

		rs.execute(sql);
		while(rs.next()){
			Person person = new Person();
			person.setId(rs.getString(1));
			person.setLastname(rs.getString(2));
			person.setCompanystartdate(rs.getString(3));
			person.setDays(rs.getString(4));
			person.setLocationid(rs.getString(5));
			list.add(person);
		}
		for(Person p : list){
			
			double days = Integer.parseInt(p.getDays());
			if(days <= 365){
				vacation = days*5/365*p.getHour();
			}
			else{
				vacation = 5*p.getHour()+(days/365-1)*p.getHour();
			}
			vacation = Math.floor(vacation);
			sql = "update kq_balanceOfLeave set BaseAmount =  "+vacation+" where leaveRulesId = 2 and resourceid = "+p.getId()+" and belongyear = "+nowtime+"";
			log.info(sql);
			rs.execute(sql);
		}

		
			
	}

}
