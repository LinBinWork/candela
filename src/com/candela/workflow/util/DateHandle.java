package com.candela.workflow.util;

import java.util.ArrayList;
import java.util.List;

public class DateHandle {
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	public DateHandle() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DateHandle(int year, int month, int day, int hour, int minute,
			int second) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	public void toDate(String date){
		String[] array = date.split("-");
		year = Integer.parseInt(array[0]);
		month = Integer.parseInt(array[1]);
		day = Integer.parseInt(array[2]);
	}
	public void toTime(String time){
		String[] array = time.split(":");
		hour = Integer.parseInt(array[0]);
		minute = Integer.parseInt(array[1]);
		second = 0;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	@Override
	public String toString() {
		return "DateHandle [day=" + day + ", hour=" + hour + ", minute="
				+ minute + ", month=" + month + ", second=" + second
				+ ", year=" + year + "]";
	}
	
	

}
