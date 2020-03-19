package com.candela.workflow.bean;

public class HrmDepartment {

    private Integer personId;
    private Integer departmentId;
    private Integer level ;

    private Integer departmentA;
    private Integer departmentB;
    private Integer departmentC;

    public HrmDepartment() {
    }

    public HrmDepartment(Integer personId, Integer departmentId, Integer level, Integer departmentA, Integer departmentB, Integer departmentC) {
        this.personId = personId;
        this.departmentId = departmentId;
        this.level = level;
        this.departmentA = departmentA;
        this.departmentB = departmentB;
        this.departmentC = departmentC;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDepartmentA() {
        return departmentA;
    }

    public void setDepartmentA(Integer departmentA) {
        this.departmentA = departmentA;
    }

    public Integer getDepartmentB() {
        return departmentB;
    }

    public void setDepartmentB(Integer departmentB) {
        this.departmentB = departmentB;
    }

    public Integer getDepartmentC() {
        return departmentC;
    }

    public void setDepartmentC(Integer departmentC) {
        this.departmentC = departmentC;
    }

    @Override
    public String toString() {
        return "HrmDepartment{" +
                "personId=" + personId +
                ", departmentId=" + departmentId +
                ", level=" + level +
                ", departmentA=" + departmentA +
                ", departmentB=" + departmentB +
                ", departmentC=" + departmentC +
                '}';
    }
}
