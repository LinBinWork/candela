package com.candela.workflow.bean;

public class HrmPerson {
	
	private String id;
	private String lastname;
	private String departmentid;
	private String managerid;
	private String seclevel;
	private String joblevel;
	private String departmentA;
	private String departmentB;
	private String departmentC;
	private String locationid;
	private String companyStartdate;
	private String formDate;
	private String startDate;
	private String endDate;
	private String status;
	public HrmPerson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HrmPerson(String id, String lastname, String departmentid,
			String managerid, String seclevel, String joblevel,
			String departmentA, String departmentB, String departmentC,
			String locationid, String companyStartdate, String formDate,
			String startDate, String endDate, String status) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.departmentid = departmentid;
		this.managerid = managerid;
		this.seclevel = seclevel;
		this.joblevel = joblevel;
		this.departmentA = departmentA;
		this.departmentB = departmentB;
		this.departmentC = departmentC;
		this.locationid = locationid;
		this.companyStartdate = companyStartdate;
		this.formDate = formDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getManagerid() {
		return managerid;
	}
	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}
	public String getSeclevel() {
		return seclevel;
	}
	public void setSeclevel(String seclevel) {
		this.seclevel = seclevel;
	}
	public String getJoblevel() {
		return joblevel;
	}
	public void setJoblevel(String joblevel) {
		this.joblevel = joblevel;
	}
	public String getDepartmentA() {
		return departmentA;
	}
	public void setDepartmentA(String departmentA) {
		this.departmentA = departmentA;
	}
	public String getDepartmentB() {
		return departmentB;
	}
	public void setDepartmentB(String departmentB) {
		this.departmentB = departmentB;
	}
	public String getDepartmentC() {
		return departmentC;
	}
	public void setDepartmentC(String departmentC) {
		this.departmentC = departmentC;
	}
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	public String getCompanyStartdate() {
		return companyStartdate;
	}
	public void setCompanyStartdate(String companyStartdate) {
		this.companyStartdate = companyStartdate;
	}
	public String getFormDate() {
		return formDate;
	}
	public void setFormDate(String formDate) {
		this.formDate = formDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HrmPerson [companyStartdate=" + companyStartdate
				+ ", departmentA=" + departmentA + ", departmentB="
				+ departmentB + ", departmentC=" + departmentC
				+ ", departmentid=" + departmentid + ", endDate=" + endDate
				+ ", formDate=" + formDate + ", id=" + id + ", joblevel="
				+ joblevel + ", lastname=" + lastname + ", locationid="
				+ locationid + ", managerid=" + managerid + ", seclevel="
				+ seclevel + ", startDate=" + startDate + ", status=" + status
				+ "]";
	}
	
	
	
	
	
}
