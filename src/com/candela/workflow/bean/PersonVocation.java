package com.candela.workflow.bean;

public class PersonVocation {

    private int id;
    private int location;
    private String companyDate;
    private String expirationDate;
    private double companyWorkYear;
    private String executeDate;
    private double vocation;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public double getCompanyWorkYear() {
        return companyWorkYear;
    }

    public void setCompanyWorkYear(double companyWorkYear) {
        this.companyWorkYear = companyWorkYear;
    }

    public double getVocation() {
        return vocation;
    }

    public void setVocation(double vocation) {
        this.vocation = vocation;
    }

    public String getCompanyDate() {
        return companyDate;
    }

    public void setCompanyDate(String companyDate) {
        this.companyDate = companyDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    @Override
    public String toString() {
        return "PersonVocation{" +
                "id=" + id +
                ", location=" + location +
                ", companyDate='" + companyDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", companyWorkYear=" + companyWorkYear +
                ", vocation=" + vocation +
                '}';
    }
}
