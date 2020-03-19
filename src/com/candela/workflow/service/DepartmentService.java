package com.candela.workflow.service;

import com.candela.workflow.bean.HrmDepartment;
import com.weaver.general.Util;
import weaver.conn.RecordSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentService {

    private List<HrmDepartment> list = new ArrayList<>();

    private RecordSet p_rs = new RecordSet();
    private RecordSet c_rs = new RecordSet();

    private String p_sql = "";
    private String c_sql = "";


    public List<HrmDepartment> getList(){
        p_sql = "select id,departmentid from hrmresource ";
        p_rs.executeQuery(p_sql);
        while(p_rs.next()){
            HrmDepartment hrmDepartment = new HrmDepartment();
            hrmDepartment.setPersonId(p_rs.getInt(1));
            hrmDepartment.setDepartmentId(p_rs.getInt(2));
            list.add(hrmDepartment);
        }

       for(int i =0 ;i<list.size();i++){
           p_sql = "select tlevel,id from hrmdepartment where id = ?";
           p_rs.executeQuery(p_sql,list.get(i).getDepartmentId());
           p_rs.next();
           Map<Integer, Integer> dept = handleDept(p_rs.getInt(1), p_rs.getInt(2));

           if(dept.containsKey(1)){
               list.get(i).setDepartmentA(dept.get(1));
           }
           if(dept.containsKey(2)){
               list.get(i).setDepartmentB(dept.get(2));
           }
           if(dept.containsKey(3)){
               list.get(i).setDepartmentC(dept.get(3));
           }

       }
       return list;

    }
    public void execute(List<HrmDepartment> list){
        for(HrmDepartment hrm : list){
            p_sql = "update hrmresource  set departmentA = ? where id = ?" ;
            p_rs.executeUpdate(p_sql,hrm.getDepartmentA(),hrm.getPersonId());
            if(hrm.getDepartmentB() != null){
                p_sql = "update hrmresource  set departmentB = ? where id = ?" ;
                p_rs.executeUpdate(p_sql,hrm.getDepartmentB(),hrm.getPersonId());
            }
            if(hrm.getDepartmentC() != null){
                p_sql = "update hrmresource  set departmentC = ? where id = ?" ;
                p_rs.executeUpdate(p_sql,hrm.getDepartmentC(),hrm.getPersonId());
            }

        }

    }
    public Map<Integer,Integer> handleDept(int tlevel,int deptid){
        Map<Integer, Integer> map = new HashMap<>();
       if(tlevel==2){
           map.put(1,deptid);
       }
       if(tlevel==3){
           map.put(2,deptid);
           p_sql = "select supdepid from hrmdepartment where id = ?";
           c_rs.executeQuery(p_sql,deptid);
           c_rs.next();
           map.put(1,c_rs.getInt(1));

       }
       if(tlevel==4){
           map.put(3,deptid);
           p_sql = "select supdepid from hrmdepartment where id = ?";
           c_rs.executeQuery(p_sql,deptid);
           c_rs.next();
           map.put(2,c_rs.getInt(1));
           p_sql = "select supdepid from hrmdepartment where id = ?";
           c_rs.executeQuery(p_sql,map.get(2));
           c_rs.next();
           map.put(1,c_rs.getInt(1));

       }
       return  map;
    }
}
