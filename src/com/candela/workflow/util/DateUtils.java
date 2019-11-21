package com.candela.workflow.util;

import com.candela.workflow.bean.Date;
import com.candela.workflow.bean.Time;

/**
 * @Author linbin
 * @Create 2019-11-15-16:21
 */
public class DateUtils {
    public Date parseDate(String src){
        String[] split = src.split("-");
        Date date = new Date();
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);

        date.setYear(year);
        date.setMonth(month);
        date.setDay(day);
        return date;
    }
    public Time parseTime(String src){
        String[] split = src.split(":");
        Time time = new Time();
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);

        time.setHour(hour);
        time.setMinute(minute);
        time.setSecond(second);
        return time;
    }


}
