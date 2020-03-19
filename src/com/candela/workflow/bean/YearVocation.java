package com.candela.workflow.bean;

public class YearVocation {
    private int resourceid;
    private double hour;
    private int location;
    private double baseVocation;
    private double releaseVocation;
    private double days;
    private String expiredDay;
    private String startDate;
    private int workDay;
    private double workYears;
    private int round;
    private int status;
    private int belongYear;

    public int getBelongYear() {
        return belongYear;
    }

    public void setBelongYear(int belongYear) {
        this.belongYear = belongYear;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public double getHour() {
        return hour;
    }

    public void setHour() {
       if(location==5){
           this.hour = 8;
       }
       else{
           this.hour = 7.5;
       }
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public double getBaseVocation() {
        return baseVocation;
    }

    public void setBaseVocation() {
        this.baseVocation = this.days * this.hour;
    }

    public double getReleaseVocation() {
        return releaseVocation;
    }

    public void setReleaseVocation(double releaseVocation) {
        this.releaseVocation = releaseVocation;
    }

    public double getDays() {
        return days;
    }

    public void setDays() {
        this.days  = Math.floor(5+this.workYears);
    }

    public String getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(String expiredDay) {
        this.expiredDay = expiredDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getWorkDay() {
        return workDay;
    }

    public void setWorkDay(int workDay) {
        this.workDay = workDay;
    }

    public double getWorkYears() {
        return workYears;
    }

    public void setWorkYears() {
        this.workYears = this.workDay/365;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus() {
        if(this.workYears<0.5){
            this.status = 0;
        }
        else{
            this.status = 1;
        }
    }


}
