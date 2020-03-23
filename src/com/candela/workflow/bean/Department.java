package com.candela.workflow.bean;

public class Department {
    private int id;
    private String departmentName;
    private int level;
    private int parentId;
    private int sonId;
    private boolean hasParent;
    private boolean hasSon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSonId() {
        return sonId;
    }

    public void setSonId(int sonId) {
        this.sonId = sonId;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public boolean isHasSon() {
        return hasSon;
    }

    public void setHasSon(boolean hasSon) {
        this.hasSon = hasSon;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", sonId=" + sonId +
                ", hasParent=" + hasParent +
                ", hasSon=" + hasSon +
                '}';
    }
}
