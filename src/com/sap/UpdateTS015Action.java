package com.sap;
/**
 * MM019 TS015研发物料预留创建流程
 * 20180326 修改为新换算方法；
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
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateTS015Action extends BaseBean implements Action{
	Logger log=Logger.getLogger(this.getClass());
	public String execute(RequestInfo request) {
		if(request == null || request.getMainTableInfo() == null || request.getMainTableInfo().getPropertyCount() == 0 ) return null;
		String flag = "0";//状态为0无法提交下一个节点
		MainTableInfo main = request.getMainTableInfo();//主表
		Property[] property = main.getProperty();
		Map<String, String> propertyMap = this.getPropertyMap( property );
		String Mtv="";//移动类型
		Mtv = Util.null2String(propertyMap.get("A13"));

		String requestid=request.getRequestid();
		String l_sql="";
		String res_no = "";
		StringBuffer error_msg = new StringBuffer();
		String tosapDate = "";
		String mainid="";
		String returnMsg = "";
		String forward="";
		SimpleDateFormat sapdate= new SimpleDateFormat("yyyy-MM-dd");
		tosapDate = sapdate.format(new Date()); 
		log.info("TS015研发物料预留创建流程 ");
		RecordSet rs = new RecordSet();
		try{
		 l_sql = " select mainid,d.id did ,A01,A09,A12,A08,A05,A06,A11,B01,B02,B04,B03,B07,A07,A13,B09,B08,B10,hr.lastname empname "+
				 " from formtable_main_157 m "+
				 " join formtable_main_157_dt1 d on m.id = d.mainid "+
				 " left JOIN HrmResource hr on M.A03 = hr.id "+
				 " where m. requestid="+requestid;
		 rs.execute(l_sql);

			List<String[]> records = new ArrayList<String[]>();
			while (rs.next()) {
				String[] s = new String[19];
				s[0] = Util.null2String(rs.getString("A12"));			//流程编号
				s[1] = Util.null2String(rs.getString("A05"));			//成本中心
				s[2] = Util.null2String(rs.getInt("did")+"");			//id
				s[3] = Util.null2String(rs.getString("A06"));			//利润中心
				s[4] = Util.null2String(rs.getString("A13"));			//移动类型
				s[5] = Util.null2String(rs.getString("A07"));			//订单号
				s[6] = Util.null2String(rs.getString("empname"));		//申请人
				s[7] = Util.null2String(rs.getString("A11"));			//范围
				s[8] = Util.null2String(rs.getString("B01"));			//物料编号
				s[9] = Util.null2String(rs.getString("B03"));			//数量
				s[10] = Util.null2String(rs.getString("B07"));			//
				s[11] = Util.null2String(rs.getString("B08"));			//金额
				s[12] = Util.null2String(rs.getString("B09"));			////预算编号	
				s[13] = Util.null2String(rs.getString("B10"));			////科目编号	
				records.add(s);
				mainid=Util.null2String(rs.getString("mainid"));
			}	
				//写入SAP
				if(records.size() > 0){
					JCoFunction function = null;
					JCoDestination destination = null;
					String chanval="";
					String oaval="";
					destination = SAPConn.SapCommonConn();
					function = destination.getRepository().getFunction("ZFM_OA_RESERVATION_CREATE"); //获取低值采购接口
					JCoStructure HEAD = function.getImportParameterList().getStructure("IM_HEAD"); // sap表头
					JCoTable ZSS_ITEM = function.getTableParameterList().getTable("IT_ITEMS"); // sap表单名
					if ("0".equals(records.get(0)[7])){ //此值与SAP的ZTTECH表对应
						chanval="N";
					}else if ("1".equals(records.get(0)[7])){
						chanval="O";
					}else if ("2".equals(records.get(0)[7])){
						chanval="P";
					}else if ("3".equals(records.get(0)[7])){
						chanval="Q";
					}else if ("4".equals(records.get(0)[7])){
						chanval="R";
					}else if ("5".equals(records.get(0)[7])){
						chanval="Y";
					}
					if("0".equals(records.get(0)[4])){
						Mtv="Y07";
						forward="A";  //扣减预算
					}else if("1".equals(records.get(0)[4])){
						Mtv="Y08";
						forward="B";  //还原预算
					}else if("2".equals(records.get(0)[4])){
						Mtv="Y17";
						forward="B";  //还原预算
					}else if("3".equals(records.get(0)[4])){
						Mtv="Y18";
						forward="A";  //扣减预算
					}
					log.info("移动类型="+Mtv);
					//设置抬头文件
					HEAD.setValue("COST_CTR", records.get(0)[1]);
					HEAD.setValue("PROFIT_CTR",records.get(0)[3]);
					HEAD.setValue("MOVE_TYPE",Mtv);
					HEAD.setValue("ORDER_NO", records.get(0)[5]);
					HEAD.setValue("WEMPF", chanval);				
					for(int i = 0; i < records.size(); i ++){										
						/** 设置明细信息 **/
						ZSS_ITEM.appendRow();
						ZSS_ITEM.setValue("PLANT","1000"); 
						ZSS_ITEM.setValue("MATERIAL",records.get(i)[8]); 	
						ZSS_ITEM.setValue("QUANTITY",records.get(i)[9]);				
						ZSS_ITEM.setValue("STORE_LOC",records.get(i)[10]);
						oaval=records.get(i)[6]+"|"+records.get(i)[0];
						ZSS_ITEM.setValue("SHORT_TEXT",oaval);			
						ZSS_ITEM.setValue("G_L_ACCT",records.get(i)[13]);
					}
					
						function.execute(destination); // 执行sap函数
	
						String SUBRC = function.getExportParameterList().getString("SUBRC");// 执行sap函数状态 -- 返回-1失败
						if (!"S".equals(SUBRC)){
							JCoTable ET_MSG = function.getTableParameterList().getTable("ET_MSG"); // sap返回订单号、项目号表单
							error_msg.append("错误信息-->");
							for (int j = 0; j < ET_MSG.getNumRows(); j++) {
								ET_MSG.setRow(j);	
								error_msg.append(j+1);
								error_msg.append("."+ET_MSG.getString("MESSAGE"));							
							}
							returnMsg = error_msg.toString();
							l_sql=" update formtable_main_157 SET BGSTATUS='Y',ERROR='"+returnMsg+"'"
									 +" WHERE requestid="+requestid;
							rs.execute(l_sql);
							flag="0";
						}else{
							res_no = Util.null2String(function.getExportParameterList().getString("EI_RES_NO"));// 返回预留号
							String update_sql = "";
							String buget_sql = "";
							RecordSet updateNum = new RecordSet();
							RecordSet updateBug = new RecordSet();						
								
							//更新预算信息(注：本流程没到在途预算，所以直接扣除已用预算，
							for(int i = 0; i < records.size(); i ++){	
								if ("B".equals(forward)){		//Y08移动类型	
									//System.out.println("Y08移动类型");
									buget_sql = "update  wv_budgetdaily set "
									   +" notusedbudget=ISNULL(budget_limit,0) + ISNULL(budget_apply,0) - ISNULL(usedbudget,0) + ISNULL("+ records.get(i)[11]+",0)" 
									   +" ,usedbudget=(ISNULL(usedbudget,0)) - ISNULL("+ records.get(i)[11]+",0)"
									   + " where id ="+records.get(i)[12];
									
								}else{
									//System.out.println("Y07移动类型");
									//可用金额=80%+累积申请金额-已用金额-本次耗用金额
									//已用金额=已用金额+本次耗用金额
									buget_sql = "update  wv_budgetdaily set "
											   +" notusedbudget=ISNULL(budget_limit,0) + ISNULL(budget_apply,0) - ISNULL(usedbudget,0) - ISNULL("+ records.get(i)[11]+",0)" 
											   +" ,usedbudget=(ISNULL(usedbudget,0)) + ISNULL("+ records.get(i)[11]+",0)"
											   + " where id ="+records.get(i)[12];
									
								}								 
								updateBug.execute(buget_sql);
								log.info("预算更新成功SQL"+buget_sql);
							}			

							//更新信息
							update_sql = "update formtable_main_157_dt1 set B05='"+res_no+"' ,B06='1', "
							+" TOSAPDATE='"+tosapDate+"' "
							+" where mainid in ("+ mainid +")";							
							updateNum.execute(update_sql);
							
							l_sql=" update formtable_main_157 SET A14='"+res_no+"', BGSTATUS='Y',ERROR='写入SAP系统成功，扣除预算成功' WHERE requestid="+requestid;
							rs.execute(l_sql);
							flag = "1";					
							log.info("预算更新成功");
						}										
				}else{
					log.info("读取数据失败");
					flag = "0";
				}
			}catch(Exception e){
				log.info("预算更新异常");
				log.info(e);
			}
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
}
 