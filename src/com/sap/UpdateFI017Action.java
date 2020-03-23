package com.sap;
/**
 * FI017 项目预算增减申请流程 
 */
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.westvalley.fanstech.sap.SAPConn;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;

public class UpdateFI017Action extends BaseBean implements Action{
	Logger log=Logger.getLogger(this.getClass());
	public String execute(RequestInfo request) {
		if(request == null || request.getMainTableInfo() == null || request.getMainTableInfo().getPropertyCount() == 0 ) return null;
		String flag = "0";//状态为0无法提交下一个节点	
		DetailTableInfo detail = request.getDetailTableInfo();//所有明细表

		String sqlString="";

		String B01="";//预算编号
		String B04="";//方向
		String B05="";//金额
		String iseno="Y";
		String notusedbudget="0";
		String mess="";
		String fyl="";
		float notuse = '0';
		float adujt = '0';
		List<String[]> records = new ArrayList<String[]>();
		log.info("FI017 项目预算还原申请流程 ");
		try{
			RecordSet dcrs = new RecordSet();
			RecordSet checkrs = new RecordSet();
			if(detail != null && detail.getDetailTableCount() > 0){
				DetailTable d_table = detail.getDetailTable(0);
				Row[] rows = d_table.getRow();
				Map<String, String> cellMap = null;				
									
				for (Row row : rows) {			
					String[] s = new String[4];
					Cell[] cells = row.getCell(); 
					cellMap = this.getCellMap(cells);//明细每一列的值
					B01 = Util.null2String(cellMap.get("B01"));//预算编号
					fyl = Util.null2String(cellMap.get("B02")); 
					B04 = Util.null2String(cellMap.get("B04"));//方向 0 增加 1 减少									
					B05 = Util.null2String(cellMap.get("B05")); //金额
					
					B05=(B05!="")?B05:"0";
					if(!"".equals(B01)){
						sqlString = "select id,notusedbudget from wv_budgetdaily where id="+B01;
						checkrs.execute(sqlString);						
						if (checkrs.next()) {							
							notusedbudget = Util.null2String(checkrs.getString("notusedbudget"));
							notusedbudget=(notusedbudget!="")?notusedbudget:"0";
							notuse = Float.parseFloat(notusedbudget);
							adujt = Float.parseFloat(B05);
							if ("1".equals(B04)){ //减少
								if (notuse>=adujt){
									s[0] = B01;
									s[1] = B04;
									s[2] = B05;
									records.add(s);		
								}else{
									iseno="N";
									mess="扣减的费用类【"+fyl+"】金额不足够，请重新核对！";
									break;								
								}
							}else if ("0".equals(B04)){ //还原
								s[0] = B01;
								s[1] = B04;
								s[2] = B05;
								records.add(s);	
							}
						}
					}
						
				}
			}	
				if("Y".equals(iseno)){ //是否够扣减
					for(int i = 0; i < records.size(); i ++){					
						//增加预算以负数方式还原
						if ("0".equals(records.get(i)[1])){

							sqlString = "update  wv_budgetdaily set "
									   +" notusedbudget=ISNULL(budget_limit,0) + ISNULL(budget_apply,0) - ISNULL(usedbudget,0) + ISNULL("+ records.get(i)[2]+",0)" 
									   +" ,usedbudget=(ISNULL(usedbudget,0)) - ISNULL("+ records.get(i)[2]+",0)"
									   + " where id ="+records.get(i)[0];				
							dcrs.execute(sqlString);
							
							log.info("FI017 项目预算增减申请流程  【还原】 sql："+sqlString);
						}else{	
						//减少预算
							sqlString = "update  wv_budgetdaily set "
									   +" notusedbudget=ISNULL(budget_limit,0) + ISNULL(budget_apply,0) - ISNULL(usedbudget,0) - ISNULL("+ records.get(i)[2]+",0)" 
									   +" ,usedbudget=(ISNULL(usedbudget,0)) + ISNULL("+ records.get(i)[2]+",0)"
									   + " where id ="+records.get(i)[0];		
							dcrs.execute(sqlString);
							log.info("FI017 项目预算还原申请流程 【扣减】 sql："+sqlString);
						}
					}
					flag = "1";
				}else{
					flag = "0";
				}		
			}catch(Exception e){
				log.info("FI017 项目预算还原申请流程 程异常"+e.toString());
			}
			request.getRequestManager().setMessagecontent(mess);
		return flag;	
	}
	/**
	 * property转到map
	 * @param property
	 * @return
	 */
	private Map<String, String> getPropertyMap(Property[] property){
		Map<String, String> m = new HashMap<String, String>();
		for(Property p : property){
			m.put( p.getName(), Util.null2String(p.getValue()) );
		}
		return m;
	}
	/**
	 * Cell转到map
	 * @param cells
	 * @return
	 */
	private Map<String, String> getCellMap(Cell[] cells){
		Map<String, String> m = new HashMap<String, String>();
		for(Cell c : cells){
			m.put(c.getName( ), Util.null2String(c.getValue()) );
		}
		return m;
	}	
}
 