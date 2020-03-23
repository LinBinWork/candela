package com.candela.workflow.bean;

import java.util.HashMap;
import java.util.Map;

public class HrmResource {
    private int id;
    private String lastname;
    private int departmentid;
    private int location;
    private int joblevel;
    private int seclevel;
    private int managerid;
    private Map<Integer ,Department> map = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerid() {
        return managerid;
    }

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getJoblevel() {
        return joblevel;
    }

    public void setJoblevel(int joblevel) {
        this.joblevel = joblevel;
    }

    public int getSeclevel() {
        return seclevel;
    }

    public void setSeclevel(int seclevel) {
        this.seclevel = seclevel;
    }

    public Map<Integer, Department> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Department> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "HrmResource{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", departmentid=" + departmentid +
                ", location=" + location +
                ", joblevel=" + joblevel +
                ", seclevel=" + seclevel +
                ", managerid=" + managerid +
                ", map=" + map +
                '}';
    }
}
