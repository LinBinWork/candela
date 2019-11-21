package com.candela.workflow.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import crivia.db.i.DataSet;
import crivia.eca.ECA;
import crivia.eca.carry.SubTable;

import weaver.conn.RecordSet;
import weaver.general.DynamicServlet;
import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.interfaces.schedule.BaseIntervalJob;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;
import weaver.soa.workflow.request.Row;

/**
 * 创建转正流程---workflowid ---9------formtable_main_72-----表名
 * ============================================================================
 *
 * @author 王伟
 *         --------------------------------------------------------------------
 *         --------
 * @date 2019-10-17
 *       --------------------------------------------------------------
 *       --------------
 * @version 版本号 V1.0
 *          ============================================================
 *          ================
 */
public class CreateRenewal extends weaver.interfaces.schedule.BaseCronJob {

	Log log = LogFactory.getLog(this.getClass());

	private String sql = "";
	private List<Map> list = new ArrayList<Map>();

	String format = "yyyyMMdd ";

	// String requestId = request.getRequestid();

	SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

	String nowtime = form.format(new Date());

	public void execute() {

		log.error("==HR04============自动创建续签流程=======");

		RecordSet rs = new RecordSet();
		sql = "select a.id cid,lastname,a.departmentid,GETDATE() sqrq,locationid,workcode,jobtitle,"
				+ " departmentA,departmentB,departmentC,managera,managerb,"
				+ " educationlevel,companystartdate,workyear,enddate,joblevel from "
				+ " hrmresource a"
				+ " where a.status = 1 "
				+ " and a.enddate = convert(char(100),dateadd(day,90,GETDATE()),23)";

		rs.execute(sql);

		while(rs.next()){
			Map<String,String > datas = new HashMap<>();
			String sqr=rs.getString("cid");//续签人员id
			String lastname=rs.getString("lastname");//续签人员id
			String sqrbm=rs.getString("departmentid");//部门
			String bgdd=rs.getString("locationid");//办公地点
			String gh=rs.getString("workcode");//工号
			String gw=rs.getString("jobtitle");//岗位
			String yjbm=rs.getString("departmentA");//一级部门
			String ejbm=rs.getString("departmentB");//二级部门
			String sjbm=rs.getString("departmentC");//三级部门
			String xl=rs.getString("educationlevel");//学历
			String rzrq=rs.getString("companystartdate");//入职日期
			String htdqrq=rs.getString("enddate");//合同到期日期
			String gwjb=rs.getString("joblevel");//岗位级别

			datas.put("sqr",sqr);
			datas.put("sqrbm",sqrbm);
			datas.put("bgdd",bgdd);
			datas.put("gh",gh);
			datas.put("gw",gw);
			if(!"".equals(yjbm)){
				datas.put("yjbm",yjbm);
			}
			if(!"".equals(ejbm)){
				datas.put("ejbm",ejbm);
			}
			if(!"".equals(sjbm)){
				datas.put("sjbm",sjbm);
			}

			datas.put("xl",xl);
			datas.put("rzrq",rzrq);
			datas.put("htdqrq",htdqrq);
			datas.put("gwjb",gwjb);
			datas.put("tjsj",nowtime);
			try {
				ECA.newRequestCreater("HR04-劳动合同续签申请流程-"+lastname+"("+nowtime+")", datas , "1" ,"11" );
			} catch (Exception e) {
				log.error("HR04-劳动合同续签申请流程=====异常结果= "+e);
			}
		}


	}

}
