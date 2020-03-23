package com.candela.workflow.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import weaver.conn.RecordSet;
import weaver.general.Util;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

/**
 * SAP连接辅助类
 * @author zhouzj 
 */
public class SAPConn {
	private static Logger log = Logger.getLogger(SAPConn.class); // 初始化日志对象
	private static final String sapType = "test"; //"正式"; //sap服务器类型
	private static  String ABAP_AS_POOLED_COMMON = "SAP测试系统"; // 通用连接
	
	/**
	 * 获取连接到SAP的信息
	 * @return res集合
	 */
	private static List<String> getSAPConnParam(int id){
		RecordSet rs = new RecordSet();
		List<String> res = new ArrayList<String>();
		rs.execute("select poolname,hostname,systemNum,client,username,password,language from sap_datasource where hpid=" + id);
		if(rs.next()){
			res.add(Util.null2String(rs.getString("hostname"))); // 系统地址
			res.add(Util.null2String(rs.getString("systemNum"))); // 系统编号
			res.add(Util.null2String(rs.getString("client")));  // 客户端端口
			res.add(Util.null2String(rs.getString("username"))); // 用户名
			res.add(Util.null2String(rs.getString("password")));  // 用户密码
			res.add(Util.null2String(rs.getString("language"))); // 语言
			res.add(Util.null2String(rs.getString("poolname"))); // 
			ABAP_AS_POOLED_COMMON = Util.null2String(rs.getString("poolname"));
			log.info("----- size=" + res.size() + ",读取SAP连接信息成功... -----");
		}
		log.info("----- 读取SAP连接信息结束... -----");
		return res;
	}
	
	
	static{
		if("test".equals(sapType)){
			// SAP通用连接设置
			log.info("----- 设置SAP连接... -----");
			Properties connectProperties = null;
			List<String> connInfo = getSAPConnParam(1);
			if(connInfo.size() == 7){
				connectProperties = new Properties();
				connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, connInfo.get(0));
				connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  connInfo.get(1));        //系统编号
				connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, connInfo.get(2));       //客户端端口
				connectProperties.setProperty(DestinationDataProvider.JCO_USER,   connInfo.get(3));  //SAP用户名
				connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, connInfo.get(4));     //密码
				connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   connInfo.get(5));        //登录语言
				connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");  //最大连接数  
				connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");     //最大连接线程
				createDataFile(connInfo.get(6), "jcoDestination", connectProperties);
			}else{
				log.info("----- SAP连接1不存在... -----");
			}
			/** 其他连接 **/
		}
	}
	
	/**
	 * 创建SAP接口属性文件。
	 * @param name	ABAP管道名称
	 * @param suffix	属性文件后缀
	 * @param properties	属性文件内容
	 */
	private static void createDataFile(String name, String suffix, Properties properties){
		log.info("----- 创建SAP连接属性文件... -----");
		File cfg = new File(name+"."+suffix);
		if(cfg.exists()){
			cfg.deleteOnExit();
		}
		try{
			FileOutputStream fos = new FileOutputStream(cfg, false);
			properties.store(fos, "for tests only !");
			fos.close();
		}catch (Exception e){
			log.error("Create Data file fail, error msg: " + e.toString());
			throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
		}
	}
	
	/**
	 * 获取SAP通用连接
	 * @return	SAP连接对象
	 */
	public static JCoDestination SapCommonConn(){
		log.info("----- 获取SAP通用连接... -----");
		JCoDestination destination =null;
		try {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED_COMMON);
		} catch (JCoException e) {
			log.error("Connect SAP fail, error msg: " + e.toString());
		}
		return destination;
	}
}