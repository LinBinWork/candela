<%@page import="org.json.JSONObject" %>
<%@page import="org.json.JSONArray" %>
<%@page import="weaver.conn.RecordSet" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.*" %>
<%@ page import="org.json.JSONException" %>

<%
    String day1 = Util.null2String(request.getParameter("day1")); //开始日期
    String day2 = Util.null2String(request.getParameter("day2")); //结束日期
    String userid = Util.null2String(request.getParameter("userid")); //申请人
    String choice = Util.null2String(request.getParameter("choice")); //加班类型
    JSONObject result = new JSONObject();
    RecordSet rs = new RecordSet();
    String up  = "";
    String off  = "";
    String sql ="";

    if("0".equals(choice)){
        if(day1.equals(day2)){
            sql = "select signtime from HrmScheduleSign " +
                    "where  signtime > '06:30:00' and userid = "+userid+" and signdate = '"+day1+"' order by signtime asc";
            rs.execute(sql);
            if (rs.next()) {
                up = Util.null2String(rs.getString(1));
                up = up.substring(0,5);
                result.put("up", up);
                result.put("sql",sql);
            }
            sql = "select signtime from HrmScheduleSign " +
                    "where  signtime > '"+up+"' and userid = "+userid+" and signdate = '"+day2+"' order by signtime desc";
            rs.execute(sql);
            if (rs.next()) {
                off = Util.null2String(rs.getString(1));
                off = off.substring(0,5);
                result.put("off", off);
                result.put("sql",sql);
            }
        }
        else{
            sql = "select signtime from HrmScheduleSign " +
                    "where  signtime > '06:30:00' and userid = "+userid+" and signdate = '"+day1+"' order by signtime asc";
            rs.execute(sql);
            if (rs.next()) {
                up = Util.null2String(rs.getString(1));
                up = up.substring(0,5);
                result.put("up", up);
                result.put("sql",sql);
            }
            sql = "select signtime from HrmScheduleSign " +
                    "where  signtime < '06:30:00' and userid = "+userid+" and signdate = '"+day2+"' order by signtime desc";
            rs.execute(sql);
            if (rs.next()) {
                off = Util.null2String(rs.getString(1));
                off = off.substring(0,5);
                result.put("off", off);
                result.put("sql",sql);
            }
        }

    }
    if("3".equals(choice)){
        if(day1.equals(day2) ){
            sql = "select signtime,signdate from HrmScheduleSign " +
                    "where  signtime < '06:30:00' and userid = "+userid+" and signdate >= '"+day2+"' order by signdate asc,signtime desc";
            rs.execute(sql);
            if (rs.next()) {
                result.put("signdate",rs.getString(2));
                off = Util.null2String(rs.getString(1));
                off = off.substring(0,5);
                result.put("off", off);
                result.put("sql",sql);
            }
        }
        else{
            sql = "select signtime from HrmScheduleSign " +
                    "where  signtime < '06:30:00' and userid = "+userid+" and signdate = '"+day2+"' order by signtime desc";
            rs.execute(sql);
            if (rs.next()) {
                off = Util.null2String(rs.getString(1));
                off = off.substring(0,5);
                result.put("off", off);
                result.put("sql",sql);
            }
        }

    }

    out.print(result);



%>


