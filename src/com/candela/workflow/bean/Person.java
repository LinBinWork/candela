package com.candela.workflow.bean;

public class Person {
	
	private String id;
	private String  lastname;
	private String companystartdate;
	private String  days;
	private String locationid;
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String id, String lastname, String companystartdate,
			String days, String locationid) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.companystartdate = companystartdate;
		this.days = days;
		this.locationid = locationid;
	}

	public double getHour(){
		double hours = 7.5 ;
		if("5".equals(locationid)){
			hours = 8.0;
		}
		return hours;
		
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
	public String getCompanystartdate() {
		return companystartdate;
	}
	public void setCompanystartdate(String companystartdate) {
		this.companystartdate = companystartdate;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	@Override
	public String toString() {
		return "Person [companystartdate=" + companystartdate + ", days="
				+ days + ", id=" + id + ", lastname=" + lastname
				+ ", locationid=" + locationid + "]";
	}
	
	
	

}
