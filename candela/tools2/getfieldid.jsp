<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="weaver.conn.RecordSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.*" %>

<%
	String workflowId = Util.null2String(request.getParameter("workflowId"));
	RecordSet rs = new RecordSet();
	//new BaseBean().writeLog("============="+rs.getDBType());
	JSONObject ids = new JSONObject();
	String sql = "select a.detailtable FROM WORKFLOW_BILLFIELD A LEFT JOIN WORKFLOW_BASE B ON A.BILLID = B.FORMID WHERE B.ID = '"+workflowId+"' ";
	if("sqlserver".equalsIgnoreCase(rs.getDBType())){
		sql+=" AND (A.DETAILTABLE !='')  ";
	}else if("oracle".equalsIgnoreCase(rs.getDBType())){
		sql+=" AND (A.DETAILTABLE IS NOT NULL)  ";
	}
	sql+=" GROUP BY A.Detailtable order by A.Detailtable";
	rs.execute(sql);
	int index = 1;
	List<String> detailList = new ArrayList<String>();
	while(rs.next()){
		detailList.add(rs.getString(1));			//获取明细表 表名 主表可由明细表去除后缀_dt1获得
	}
	
	sql = "select  A.ID,A.FIELDNAME,A.Detailtable,A.fieldhtmltype FROM WORKFLOW_BILLFIELD A LEFT JOIN WORKFLOW_BASE B ON A.BILLID = B.FORMID WHERE B.ID = '"+workflowId+"'";
	if("sqlserver".equalsIgnoreCase(rs.getDBType())){
		sql+=" AND (A.DETAILTABLE ='')  ";
	}else if("oracle".equalsIgnoreCase(rs.getDBType())){
		sql+=" AND (A.DETAILTABLE IS NULL)  ";
	}
	
	rs.execute(sql);
	JSONObject fieldType = new JSONObject();
	JSONObject mid = new JSONObject();
	while(rs.next()){
		mid.put(rs.getString(2), "field" +rs.getString(1));  //获取主表 field - id json键值对
		fieldType.put("f"+rs.getString(1),rs.getString(4));  //获取主表 id - 类型 json键值对
	}
	ids.put("mid", mid);
	ids.put("ft", fieldType);
	for(String dtabel : detailList){
		//String Detailtable =rs.getString("Detailtable");
		
		
		
		JSONObject did = new JSONObject();
		sql = "select  A.ID,A.FIELDNAME,A.Detailtable,A.fieldhtmltype FROM WORKFLOW_BILLFIELD A LEFT JOIN WORKFLOW_BASE B ON A.BILLID = B.FORMID WHERE B.ID = "+workflowId+" AND A.DETAILTABLE='"+dtabel+"'";
		rs.execute(sql);
		while(rs.next()){
			fieldType.put("f"+rs.getString(1),rs.getString(4));	//获取明细表 id - 类型 json键值对
			did.put(rs.getString(2), "field" +rs.getString(1)); //获取明细表 field - id json键值对
		}
		String didid =dtabel.split("_")[3];
		didid = didid.substring(2, didid.length());;
		
		ids.put("did"+didid, did);
		index++;
	}
	out.clear();
	out.print(ids);
%>


