package com.candela.workflow.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
 * @author 王伟
 * ----------------------------------------------------------------------------
 * @date  2019-10-17
 * ----------------------------------------------------------------------------
 * @version 版本号 V1.0 
 * ============================================================================
 */
public class CreatePositive extends  weaver.interfaces.schedule.BaseCronJob {
	
	           
	            
			Log log = LogFactory.getLog(this.getClass());
		
			
			
			
		    String format = "yyyyMMdd ";
			
		    //String requestId = request.getRequestid();
		    
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
			
			String nowtime = form.format(new Date());

	
			
			
	public  void execute() 
	{
		
		
		

		log.error("==HR02============自动创建转正流程=======");
		 
		
	
		RecordSet rs = new RecordSet();
		
	    String sql = " select a.probationenddate,a.id cid,departmentid ,departmenta,departmentB,departmentC,lastname, " 
	    		+" locationid,startdate,probationenddate,workcode,managera,managerb,managerc,joblevel,educationlevel,jobtitle, b.tlevel tlevel  "
 +" from hrmresource a  left join HrmDepartment b on a.departmentid = b.id   where a.status =0  "

+"  and a.probationenddate = convert(char(100),dateadd(day,10,GETDATE()),23) ";
	  
        
	    rs.execute(sql);
	    
	    while (rs.next()){
			Map<String, String> datas  = new HashMap<String, String>();
			String sqr=rs.getString("cid");//转正人员id
			String sqrbm=rs.getString("departmentid");//部门
			String lastname=rs.getString("lastname");//姓名
			String gh=rs.getString("workcode");//工号
			String rzrq=rs.getString("startdate");//入职日期
			String yjzzrq=rs.getString("probationenddate");//转正日期
			String yjbms=rs.getString("departmenta");//一级部门
			String ejbms=rs.getString("departmentb");//二级部门
			String sjbms=rs.getString("departmentc");//三级部门
			String yjbmfzr=rs.getString("managera");//一级部门负责人
			String ejbmfzr=rs.getString("managerb");//二级部门负责人
			String bgdd=rs.getString("locationid");//办公地点
			String gw=rs.getString("jobtitle");//岗位
			String gwjb=rs.getString("joblevel");//岗位级别
			String xl=rs.getString("educationlevel");//学历

			datas.put("sqr",sqr);
			datas.put("sqrbm",sqrbm);
			datas.put("lastname",lastname);
			datas.put("gh",gh);
			datas.put("rzrq",rzrq);
			datas.put("yjzzrq",yjzzrq);
			if(!"".equals(yjbms)){
				datas.put("yjbms",yjbms);
			}
			if(!"".equals(ejbms)){
				datas.put("ejbms",ejbms);
			}
			if(!"".equals(sjbms)){
				datas.put("sjbms",sjbms);
			}
			datas.put("bgdd",bgdd);
			datas.put("gw",gw);
			datas.put("gwjib",gwjb);
			datas.put("xl",xl);
			datas.put("sqrq",nowtime);


			try {
				ECA.newRequestCreater("HR02-人员转正申请流程-"+lastname+"("+nowtime+")", datas , "1" ,"9" );

			} catch (Exception e) {

				log.error("HR02-人员转正申请流程=====异常结果= "+e);
			}
		}
	    
		/*if(rs.next()){
			
			Map<String, String> datas  = new HashMap<String, String>();

			for (int i = 0; i < rs.getCounts(); i++)
			{
				String sqr=rs.getString("cid");//转正人员id
			    String sqrbm=rs.getString("departmentid");//部门
			    String lastname=rs.getString("lastname");//姓名
			    String gh=rs.getString("workcode");//工号
			    String rzrq=rs.getString("startdate");//入职日期
			    String yjzzrq=rs.getString("probationenddate");//转正日期
			    String yjbms=rs.getString("departmenta");//一级部门
			    String ejbms=rs.getString("departmentb");//二级部门
			    String sjbms=rs.getString("departmentc");//三级部门
			    String yjbmfzr=rs.getString("managera");//一级部门负责人
			    String ejbmfzr=rs.getString("managerb");//二级部门负责人
			    String bgdd=rs.getString("locationid");//办公地点
			    String gw=rs.getString("jobtitle");//岗位
			    String gwjb=rs.getString("joblevel");//岗位级别
			    String xl=rs.getString("educationlevel");//学历
			    	
			    datas.put("sqr",sqr);
			    datas.put("sqrbm",sqrbm);
			    datas.put("lastname",lastname);
			    datas.put("gh",gh);
			    datas.put("rzrq",rzrq);
			    datas.put("yjzzrq",yjzzrq);
			    datas.put("yjbms",yjbms);
			    datas.put("ejbms",ejbms);
			    datas.put("sjbms",sjbms);
			    datas.put("yjbmfzr",yjbmfzr);
			    datas.put("ejbmfzr",ejbmfzr);
			    datas.put("bgdd",bgdd);
			    datas.put("gw",gw);
			    datas.put("gwjib",gwjb);
			    datas.put("xl",xl);
			    
				
			    try {
					ECA.newRequestCreater("HR02-人员转正申请流程-"+lastname+"("+nowtime+")", datas , "1" ,"9" );
				
			    } catch (Exception e) {
					
			    	log.error("HR02-人员转正申请流程=====异常结果= "+e);
				}
				 	
					
				
			
				
				rs.next();
			    
			    
			}
			
			
		
		}
	    */
	    
	}
	
	
	
	


}
				
	   
	
	

	


	





