package com.candela.workflow.util;

import com.candela.workflow.bean.Date;
import com.candela.workflow.bean.Time;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @Author linbin
 * @Create 2019-11-15-16:21
 */
public class DateUtils {
    public static Date parseDate(String src){
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
    public static Time parseTime(String src){
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

    public static LocalTime  PlusHour(Time time,int hour){
        LocalTime localTime = LocalTime.of(time.getHour(), time.getMinute(), time.getSecond());
        LocalTime plusHours = localTime.plusHours(hour);
        return plusHours;
    }
    public static LocalDate PlusDay(Date date, long day){
        LocalDate localDate = LocalDate.of(date.getYear(),date.getMonth(),date.getDay());
        LocalDate localDate1 = localDate.plusDays(day);
        return localDate1;
    }



}
