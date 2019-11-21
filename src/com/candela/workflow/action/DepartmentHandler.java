package com.candela.workflow.action;

import java.util.ArrayList;
import java.util.List;

import weaver.conn.RecordSet;

import com.candela.workflow.bean.HrmPerson;
import com.weaver.general.BaseBean;
import com.weaver.general.Util;



public class DepartmentHandler extends BaseBean{
	private String sql ;
	private List<HrmPerson> list  = new ArrayList<HrmPerson>();

	public void execute(){
		RecordSet rs = new RecordSet();
		
		sql = "select id,lastname,departmentid,managerid,seclevel,joblevel,locationid,companyStartdate,probationenddate,startdate,enddate,status from hrmresource " ;
		rs.execute(sql);
		while(rs.next()){
			HrmPerson hrmPerson = new HrmPerson();
			hrmPerson.setId(rs.getString(1));
			hrmPerson.setLastname(rs.getString(2));
			hrmPerson.setDepartmentid(rs.getString(3));
			hrmPerson.setManagerid(rs.getString(4));
			hrmPerson.setSeclevel(rs.getString(5));
			hrmPerson.setJoblevel(rs.getString(6));
			hrmPerson.setLocationid(rs.getString(7));
			hrmPerson.setCompanyStartdate(rs.getString(8));
			hrmPerson.setFormDate(rs.getString(9));
			hrmPerson.setStartDate(rs.getString(10));
			hrmPerson.setEndDate(rs.getString(11));
			hrmPerson.setStatus(rs.getString(12));
			list.add(hrmPerson);
			
		}
		for(int i=0;i<list.size();i++){
			sql = "select id,departmentname,tlevel,supdepid from hrmdepartment where id = "+list.get(i).getDepartmentid();
			rs.execute(sql);
			
			if(rs.next()){
				if("4".equals(rs.getString(3))){
					list.get(i).setDepartmentC(Util.null2String(rs.getString(1)));
					list.get(i).setDepartmentB(Util.null2String(rs.getString(4)));
					sql = "select id,departmentname,tlevel,supdepid from hrmdepartment where id = "+rs.getString(4);
					rs.execute(sql);
					rs.next();
					list.get(i).setDepartmentA(Util.null2String(rs.getString(1)));
				}
				if("3".equals(rs.getString(3))){
					list.get(i).setDepartmentB(Util.null2String(rs.getString(1)));
					list.get(i).setDepartmentA(Util.null2String(rs.getString(4)));
				}
				if("2".equals(rs.getString(3))){
					list.get(i).setDepartmentA(Util.null2String(list.get(i).getDepartmentid()));
				}
				
			}
		}
		for(HrmPerson p : list){
			sql = "update hrmresource set departmentA = "+p.getDepartmentA()+" ,departmentB = "+p.getDepartmentB()+" ,departmentC = "+p.getDepartmentC()+" " +
					" where id = "+p.getId();
		}
		System.out.println(list);
		
	}

}
