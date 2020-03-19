package com.candela.workflow.bean;

public class Vocation {
    private int resourceid;
    private int location;
    private double hour;
    private double  baseVocation;
    private double releaseVocation;
    private double workYear;
    private double part;
    private int days;
    private String companyday;
    private String workday;
    private int status;
    private int round;

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public double getHour() {
        return hour;
    }

    public void setHour() {
        if(location==5){
            this.hour = 8;
        }else{
            this.hour = 7.5;
        }
    }

    public double getBaseVocation() {
        return baseVocation;
    }

    public void setBaseVocation(double baseVocation) {
        this.baseVocation = baseVocation;
        if(workYear<0.5){
            this.baseVocation = 0 ;
        }
    }

    public double getReleaseVocation() {
        return releaseVocation;
    }

    public void setReleaseVocation(double releaseVocation) {
        this.releaseVocation = releaseVocation;
    }

    public double getWorkYear() {
        return workYear;
    }

    public void setWorkYear(double workYear) {
        this.workYear = workYear;
    }

    public double getPart() {
        return part;
    }

    public void setPart(double part) {
        this.part = part;
    }

    public int getDays() {
        return days;
    }

    public void setDays() {
        this.days = (int) (5+Math.floor(workYear));
        if(days>=15){
            days = 15;
        }
    }

    public String getCompanyday() {
        return companyday;
    }

    public void setCompanyday(String companyday) {
        this.companyday = companyday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus() {
        if(workYear<0.5){
            this.status = 0;
        }
        else if(workYear>=0.5 && workYear<=1){
            this.status = 1;
        }
        else {
            this.status = 2;
        }
    }

    @Override
    public String toString() {
        return "Vocation{" +
                "resourceid=" + resourceid +
                ", location=" + location +
                ", hour=" + hour +
                ", baseVocation=" + baseVocation +
                ", releaseVocation=" + releaseVocation +
                ", workYear=" + workYear +
                ", part=" + part +
                ", days=" + days +
                ", companyday='" + companyday + '\'' +
                ", workday='" + workday + '\'' +
                ", status=" + status +
                ", round=" + round +
                '}';
    }
}
