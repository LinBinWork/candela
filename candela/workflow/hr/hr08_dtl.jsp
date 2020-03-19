<%@page import="org.json.JSONObject" %>
<%@page import="org.json.JSONArray" %>
<%@page import="weaver.conn.RecordSet" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.*" %>
<%@ page import="org.json.JSONException" %>

<%
    String date = Util.null2String(request.getParameter("date")); //加班日期
    String userid = Util.null2String(request.getParameter("userid")); //申请人

    JSONObject result = new JSONObject();
    List list = new ArrayList();
    RecordSet rs = new RecordSet();
    String sql = "";
    boolean isHoliday = false;

    sql = "select groupid,serialid from kq_format_detail where kqdate = '"+date+"' and resourceid =  " +userid;

    rs.execute(sql);
    rs.next();


    if("NULL".equals(rs.getString(1)) &&  "NULL".equals(rs.getString(1))){
        isHoliday = true;
    }


    sql = "select signtime from HrmScheduleSign where (signtime > '06:30'  and signdate = '"+date+"' or (signtime < '06:30' and signdate = dateadd(day,1,'"+date+"'))) and userId = "+userid+" order by signdate asc,signtime asc";

    rs.execute(sql);

    while(rs.next()){
        if(!"".equals(rs.getString(1))){
            String signtime = Util.null2String(rs.getString(1));
            list.add(signtime);
        }
    }

    if(list.size()>=2){
        result.put("begin",list.get(0));
        result.put("end",list.get(list.size()-1));
        result.put("isHoliday",true);

        out.print(result);

    }







%>


