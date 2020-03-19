package com.candela.workflow.service;

import com.candela.workflow.bean.Vocation;
import com.candela.workflow.bean.YearVocation;
import weaver.conn.RecordSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class YearVocationService {
    private RecordSet rs = new RecordSet();
    private String sql;
    SimpleDateFormat form1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat form2 = new SimpleDateFormat("yyyy");

    String date = form1.format(new Date());
    String year = form2.format(new Date());
    public List<YearVocation> execute(){

        sql = "select id,locationid,DATEDIFF(DAY,startdate,?) workday,datediff(YEAR,startdate,?) round ,startdate from hrmresource where status <> 5 ";
        rs.executeQuery(sql,date,date);
        List<YearVocation> list = new ArrayList<>();
        while (rs.next()){
            YearVocation vocation = new YearVocation();
            vocation.setResourceid(rs.getInt(1));
            vocation.setLocation(rs.getInt(2));
            vocation.setWorkDay(rs.getInt(3));
            vocation.setRound(rs.getInt(4));
            vocation.setStartDate(rs.getString(5));
            vocation.setHour();
            vocation.setWorkYears();
            vocation.setDays();
            vocation.setStatus();
            vocation.setBaseVocation();
            list.add(vocation);
        }
        return  list;
    }
    public void handle(List<YearVocation> list){
        Iterator<YearVocation> it = list.iterator();
        while(it.hasNext()){
            YearVocation vocation = it.next();
            sql = "update kq_balanceOfLeave set baseAmount = ? where belongYear = ? and leaveRulesId  = 2 and resourceId = ?";
            rs.executeUpdate(sql,vocation.getBaseVocation(),year,vocation.getResourceid());

        }

    }
}
