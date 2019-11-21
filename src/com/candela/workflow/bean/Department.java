package com.candela.workflow.bean;

import java.util.List;

public class Department {
	
	private String id ;
	private String deptname;
	private Person manager;
	private List<Person> persons;
	
	
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Department(String id, String deptname, Person manager) {
		super();
		this.id = id;
		this.deptname = deptname;
		this.manager = manager;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public Person getManager() {
		return manager;
	}
	public void setManager(Person manager) {
		this.manager = manager;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	@Override
	public String toString() {
		return "Department [deptname=" + deptname + ", id=" + id + ", manager="
				+ manager + ", persons=" + persons + "]";
	}
	
	
}
