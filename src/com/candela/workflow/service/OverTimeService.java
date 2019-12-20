package com.candela.workflow.service;

import com.candela.workflow.bean.ScheduleSign;
import com.candela.workflow.bean.Time;
import com.candela.workflow.util.DateUtils;
import com.engine.kq.biz.KQFormatData;
import weaver.conn.RecordSet;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OverTimeService {
    private List<ScheduleSign> list = new ArrayList<>();
    private String sql = "select resourceid,kqdate,signindate,signintime,signoutdate,signouttime,signinid,signoutid " +
            "from dbo.kq_format_detail" +
            " where signintime <> '' and signouttime <> '' and (signouttime >= '23:00:00' or signouttime < '06:30:00')" +
            "and kqdate <= convert(char(100),dateadd(day,-1,GETDATE()),23)";
    private RecordSet rs = new RecordSet();
    private RecordSet rs_u = new RecordSet();
    private String update_sql = "";
    private String insert_sql = "";
    private String select_sql = "";

    private KQFormatData kqFormatData = new KQFormatData();

    private SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

    private String nowtime = form.format(new Date());


    public List<ScheduleSign> getList(){
        rs.executeQuery(sql);
        while(rs.next()){
            ScheduleSign scheduleSign = new ScheduleSign();
            scheduleSign.setResourceid(rs.getString(1));
            scheduleSign.setKqdate(rs.getString(2));
            scheduleSign.setSignindate(rs.getString(3));
            scheduleSign.setSignintime(rs.getString(4));
            scheduleSign.setSignoutdate(rs.getString(5));
            scheduleSign.setSignouttime(rs.getString(6));
            scheduleSign.setSigninid(rs.getString(7));
            scheduleSign.setSignoutid(rs.getString(8));

            if(scheduleSign.getSignouttime().compareTo("23:59:59") < 0){
                scheduleSign.setStatus(1);
            }
            if(scheduleSign.getSignouttime().compareTo("06:00:00") < 0){
                scheduleSign.setStatus(2);
            }
            if(scheduleSign.getSignouttime().compareTo("06:00:00") > 0 && scheduleSign.getSignouttime().compareTo("06:30:00") < 0){
                scheduleSign.setStatus(3);
            }
            list.add(scheduleSign);
        }
        return list;
    }
    public void  handle(List<ScheduleSign> list ){
        for( ScheduleSign sign : list){
            /**
             * 加班未过12点，允许次日迟到一小时
             */
            com.candela.workflow.bean.Date parseDate = DateUtils.parseDate(sign.getKqdate());
            String kqdate = DateUtils.PlusDay(parseDate, 1).toString();
            if(sign.getStatus()==1){
                sql = "select signintime,signinid from kq_format_detail where resourceid = ? and kqdate = convert(char(100),dateadd(day,1,?),23)";
                rs.executeQuery(sql,sign.getResourceid(),sign.getKqdate());
                if(rs.next()){
                    String signintime = rs.getString(1);
                    String signinid = rs.getString(2);
                    //日志表无记录则执行改动作，有记录则不重复操作
                    if(!"".equals(signinid) && !"".equals(signintime) ){
                        select_sql = "select id from SignCardLog where id = ?";
                        rs_u.executeQuery(select_sql,signinid);
                        rs_u.next();
                        if("".equals(rs_u.getString(1))){

                            //插入SignCardLog日志表记录
                            insert_sql = "insert into SignCardLog(id,userid,kqdate,signtime) values(?,?,?,?)";
                            rs_u.executeUpdate(insert_sql,signinid,sign.getResourceid(),sign.getKqdate(),signintime);

                            //更新次日上班卡时间
                            Time time = DateUtils.parseTime(signintime);
                            LocalTime time2 =  DateUtils.PlusHour(time, -1);
                            String plusHour = "";
                            if(time2.getHour() <= 8){
                                plusHour= "09:30:00";
                            }
                            else{

                                plusHour = time2.toString();
                            }
                            update_sql = "update hrmschedulesign set signtime = ? ,signFrom = 'AutoSign' where id = ? and signFrom <> 'AutoSign'";
                            rs_u.executeUpdate(update_sql,plusHour,signinid);
                        }

                    }
                }
            }
            /**
             * 加班超过12点，补次日上班卡
             */
            if(sign.getStatus()==2){
                select_sql = "select * from hrmschedulesign where userid =? and signdate = ? and signfrom = ?";
                rs_u.executeQuery(select_sql,sign.getResourceid(),kqdate,"AutoSignUp");
                rs_u.next();
                //若已有该操作则不重复操作
                if("".equals(rs_u.getString(1))){
                    insert_sql = "insert into hrmschedulesign(userId,userType,signDate,signTime,isInCom,signFrom) values(?,1,convert(char(100),dateadd(day,1,?),23),'09:30:00',1,'AutoSignUp')";
                    rs_u.executeUpdate(insert_sql,sign.getResourceid(),sign.getKqdate());
                }
            }
            /**
             * 加班超过6点，次日补全天卡
             */
            if(sign.getStatus()==3){
                select_sql = "select * from hrmschedulesign where userid =? and signdate = ? and signfrom in ('AutoSignUp','AutoSignOff')";
                rs_u.executeQuery(select_sql,sign.getResourceid(),kqdate);
                rs_u.next();
                //若已有该操作则不重复操作
                if("".equals(rs_u.getString(1))){
                    insert_sql = "insert into hrmschedulesign(userId,userType,signDate,signTime,isInCom,signFrom) values(?,1,convert(char(100),dateadd(day,1,?),23),'09:30:00',1,'AutoSignUp')";
                    rs_u.executeUpdate(insert_sql,sign.getResourceid(),sign.getKqdate());
                    insert_sql = "insert into hrmschedulesign(userId,userType,signDate,signTime,isInCom,signFrom) values(?,1,convert(char(100),dateadd(day,1,?),23),'18:30:00',1,'AutoSignOff')";
                    rs_u.executeUpdate(insert_sql,sign.getResourceid(),sign.getKqdate());
                }
            }
            //刷新考勤报表

            kqFormatData.formatKqDate(sign.getResourceid(),kqdate);

        }
    }
}
