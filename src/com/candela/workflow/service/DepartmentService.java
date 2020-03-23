package com.candela.workflow.service;

import com.candela.workflow.bean.Department;
import com.candela.workflow.bean.HrmResource;
import weaver.conn.RecordSet;

import java.util.*;

public class DepartmentService {
    private String sql  = "";
    private RecordSet rs = new RecordSet();
    public Map<Integer,Department> getDepartments(){
        Map<Integer,Department> departments= new HashMap<>();
       sql = "select id,departmentname,supdepid,tlevel from HrmDepartment";
       rs.executeQuery(sql);
       while(rs.next()){
           Department department = new Department();
           department.setId(rs.getInt(1));
           department.setDepartmentName(rs.getString(2));
           department.setParentId(rs.getInt(3));
           department.setLevel(rs.getInt(4));
           if(department.getLevel()==2){
               department.setHasParent(false);
           }
           else {
               department.setHasParent(true);
           }
           if(department.getLevel()==4){
               department.setHasSon(false);
           }
           else{
               department.setHasSon(true);
           }
           departments.put(department.getId(),department);

       }
       return  departments;
    }
    public List<HrmResource> getResource(Map<Integer,Department> map){
        sql = "select id,lastname,locationid,departmentid,joblevel,seclevel,managerid from hrmresource where status <> 5 ";
        rs.executeQuery(sql);
        List<HrmResource> list = new ArrayList<>();
        while(rs.next()){
            HrmResource resource = new HrmResource();
            Map<Integer,Department> depts = new HashMap<>();
            resource.setId(rs.getInt(1));
            resource.setLastname(rs.getString(2));
            resource.setLocation(rs.getInt(3));
            resource.setDepartmentid(rs.getInt(4));
            resource.setJoblevel(rs.getInt(5));
            resource.setSeclevel(rs.getInt(6));
            resource.setManagerid(rs.getInt(7));

            //
            Department department = map.get(resource.getDepartmentid());
            depts.put(department.getLevel()-1,department);

            if(department.isHasParent()){
                Department department1 = map.get(department.getParentId());
                depts.put(department1.getLevel()-1,department1);
                if(department1.isHasParent()){
                    Department department2 = map.get(department1.getParentId());
                    depts.put(department2.getLevel()-1,department2);
                }
            }

           resource.setMap(depts);
            list.add(resource);
        }
        return list;

    }
    public void execute(List<HrmResource> list){
        for (HrmResource hr :list){
            if(hr.getMap().get(1) != null){
                sql = "update hrmresource set departmentA = ? where id = ?";
                rs.executeUpdate(sql,hr.getMap().get(1).getId(),hr.getId());
            }
            if(hr.getMap().get(2) != null){
                sql = "update hrmresource set departmentB = ? where id = ?";
                rs.executeUpdate(sql,hr.getMap().get(2).getId(),hr.getId());
            }
            if(hr.getMap().get(3) != null){
                sql = "update hrmresource set departmentC = ? where id = ?";
                rs.executeUpdate(sql,hr.getMap().get(3).getId(),hr.getId());
            }
        }
    }

}
