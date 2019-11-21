package com.candela.workflow.action;

import com.candela.workflow.bean.Person;
import com.candela.workflow.bean.Sign;
import com.candela.workflow.bean.Time;
import com.candela.workflow.util.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SetCardJob extends  BaseCronJob {
	
	private String sql = null;
	private List<Sign> list = new ArrayList();
	private

	Log log = LogFactory.getLog(this.getClass());

    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
	
	String nowtime = form.format(new Date());

	@Override
	public void execute() {

		RecordSet rs = new RecordSet();
		DateUtils dateUtils = new DateUtils();

		//1.获取昨天在11点-12点打卡的打卡数据

		sql = "select id,userId,userType,signDate,signTime,signFrom" +
				" from hrmschedulesign where signDate = DATEADD(d,-1,'"+nowtime+"') and signTime>='23:00:00' and signTime <= '23:59:59'";

		rs.execute(sql);
		while (rs.next()){
			Sign sign = new Sign();
			sign.setUserId(rs.getString(2));
			sign.setUserType(rs.getString(3));
			sign.setSignDateBefore(rs.getString(4));
			sign.setSignTime(rs.getString(5));
			sign.setSignFrom(rs.getString(6));
			list.add(sign);
		}

		//2.获取今天上班卡时间，并在此基础上将补卡时间提前1小时

		for(int i = 0;i<list.size();i++){
			sql = "select  id,userid,signDate,signTime,signfrom " +
					" from hrmschedulesign where userid = "+list.get(i).getUserId()+" and signdate = '"+nowtime+"' order by signTime";
			rs.execute(sql);

			if(rs.next()){
				list.get(i).setSignBefore(rs.getString(4));
				list.get(i).setSignDateAfter(nowtime);
				list.get(i).setId(rs.getString(1));
				Time time = dateUtils.parseTime(rs.getString(4));
				LocalTime localTime = LocalTime.of(time.getHour(), time.getMinute(), time.getSecond());
				String afterTime = localTime.minusHours(1).toString()+":00";
				list.get(i).setSignAfter(afterTime);

				//维护考勤
				sql = "update hrmschedulesign set signtime = '"+afterTime+"' where id = "+list.get(i).getId();
				rs.execute(sql);

				//4.插入数据至日志表
				sql = "insert into SignCardLog values ("+list.get(i).getId()+","+list.get(i).getUserId() + ",'"+list.get(i).getSignDateBefore()+"','"+list.get(i).getSignTime()+"','"+list.get(i).getSignDateAfter()+"','" +list.get(i).getSignBefore()+"','"+list.get(i).getSignAfter()+"',1)";
				rs.execute(sql);

			}


		}


		
			
	}

}
