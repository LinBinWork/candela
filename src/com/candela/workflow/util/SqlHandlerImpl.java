package com.candela.workflow.util;

import weaver.conn.RecordSet;

import java.util.Map;

/**
 * @Author linbin
 * @Create 2019-11-18-17:25
 */
public class SqlHandlerImpl implements SqlHandler {
    private RecordSet rs = new RecordSet();
    private String sql = "";
    @Override
    public String create(String table, Map<String, String> map) {
        String s1 = "insert into "+table+"(";
        String s2 = "";
        String s3 = "";
        String s4 = "";

        for(Map.Entry<String,String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            s2 = s2+key+",";
            s3 = s3+"?,";
            s4 = s4+value+",";

        }
        s2=s2.substring(0,s2.length()-1);
        s3=s3.substring(0,s3.length()-1);
        s4=s4.substring(0,s4.length()-1);
        Object[] objects = s4.split(",");
        sql = s1+s2+") values("+s3+")";
        System.out.println(s4);
        rs.executeUpdate(sql,objects);
        return sql;
    }

    @Override
    public String update(String table, Map<String, String> map) {
        return null;
    }

    @Override
    public String drop(String table, Map<String, String> map) {
        return null;
    }

    @Override
    public String get(String table, Map<String, String> map) {
        return null;
    }
}
