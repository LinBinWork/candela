package com.candela.workflow.service;

import com.candela.workflow.bean.Vocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.conn.RecordSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VocationService {
    private String sql;
    private RecordSet rs = new RecordSet();
    private RecordSet rs2 = new RecordSet();

    Log log = LogFactory.getLog(this.getClass());
    SimpleDateFormat form1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat form2 = new SimpleDateFormat("yyyy");

    String date = form1.format(new Date());
    String year = form2.format(new Date());

    public void execute(){
        String lastDay = year+"-12-31";
        String firstDay = year +"-01-01";
        sql = "select id,locationid,startdate,companyworkyear,SUBSTRING(startdate,5,10) workday,datediff(YEAR,startdate,?) round from hrmresource where status <> 5 order by id";
        rs.executeQuery(sql,date);
        List<Vocation> list = new ArrayList<>();
        while(rs.next()){
            Vocation vocation = new Vocation();
            vocation.setResourceid(rs.getInt(1));
            vocation.setLocation(rs.getInt(2));
            vocation.setCompanyday(rs.getString(3));
            vocation.setWorkYear(rs.getDouble(4));
            vocation.setWorkday(rs.getString(5));
            vocation.setRound(rs.getInt(6));
            vocation.setHour();
            vocation.setDays();
            vocation.setStatus();
            if(vocation.getRound()==0){
                sql = "select DATEDIFF(DAY,?,?) workday";
                rs2.executeQuery(sql,vocation.getCompanyday(),date);
                rs2.next();
                double baseVocation = Math.floor(rs2.getInt(1) / 365.0 * vocation.getHour() * 2) / 2;
                vocation.setBaseVocation(baseVocation);
            }
            else{
                String workDay = year+vocation.getWorkday();
                //入职日期大于当前时间
                if(workDay.compareTo(date)>=0){
                    sql = "select DATEDIFF(DAY,?,?) workday";
                    rs2.executeQuery(sql,firstDay,date);
                    rs2.next();
                    double baseVocation = Math.floor(rs2.getInt(1) / 365.0 * vocation.getDays()* vocation.getHour() * 2) / 2;

                    vocation.setBaseVocation(baseVocation);
                }
                //入职日期小于当前日期
                else {
                    sql = "select DATEDIFF(DAY,?,?) workday";
                    rs2.executeQuery(sql,firstDay,workDay);
                    rs2.next();
                    double baseVocation = Math.floor(rs2.getInt(1) / 365.0 *(vocation.getDays()-1)* vocation.getHour() * 2) / 2;
                    sql = "select DATEDIFF(DAY,?,?) workday";
                    rs2.executeQuery(sql,workDay,date);
                    rs2.next();
                    baseVocation = baseVocation + Math.floor(rs2.getInt(1) / 365.0 * vocation.getDays()* vocation.getHour() * 2) / 2;
                    vocation.setBaseVocation(baseVocation);
                }

            }
            sql = "update kq_balanceOfLeave set baseAmount = ? where belongYear = ? and leaveRulesId  = 2 and resourceId = ?";
            rs.executeUpdate(sql,vocation.getBaseVocation(),year,vocation.getResourceid());
            list.add(vocation);
        }
        log.info(list);
    }
}
