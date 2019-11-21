package com.candela.workflow.action;

import com.engine.kq.biz.KQFormatData;
import com.engine.kq.biz.KQWorkTime;
import com.engine.kq.entity.TimeScopeEntity;
import com.engine.kq.entity.WorkTimeEntity;
import com.weaver.general.BaseBean;
import com.weaver.general.Util;
import org.apache.log4j.Logger;
import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author linbin
 * @Create 2019-08-16-15:17
 */
public class EcrChangeAction extends BaseBean implements Action {
	private  String value = "";
    private Map<String, String> getPropertyMap(Property[] property){
        Map<String, String> m = new HashMap<String, String>();
        for(Property p : property){
            m.put( p.getName(), Util.null2String(p.getValue()) );
        }
        return m;
    }

    Logger log=Logger.getLogger(this.getClass());

    public String execute(RequestInfo requestinfo) {

        System.out.println("进入Action requestid=" + requestinfo.getRequestid());
        String requestid = requestinfo.getRequestid();  //请求ID
        RecordSet rs = new RecordSet();
        
        String [] departments = {"2","53","19","124","3","66","51"};
        List list  = new ArrayList();
        String sql = "";
       
        
       


        //取主表数据
        Property[] properties = requestinfo.getMainTableInfo().getProperty();
        Map<String,String> map = this.getPropertyMap(properties);
        String xfbm = map.get("xfbm");
        value = xfbm;
        String manager = "";
        
        String[] split = xfbm.split(",");
        
        for(String s: split){
        	int index = Integer.parseInt(s);
        	list.add(departments[index]);
        }
        value = list.toString();
        list.clear();
        value = value.substring(1,value.length()-1);
        sql = "select bmfzr from Matrixtable_2 where id in ("+value+")";
        rs.execute(sql);
        while(rs.next()){
        	value = rs.getString(1);
        	manager= manager+value+",";
        	
        }
        value = manager.substring(0,manager.length()-1);
       /*value =  list.toString();
       value = value.substring(1, value.length()-1);*/
       value = "update formtable_main_105 set xgbm = '"+value+"' where requestid = "+requestid;
       sql = value;
       rs.execute(sql);



        //控制流程流转，增加以下两行，流程不会向下流转，表单上显示返回的自定义错误信息
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
    }


}
