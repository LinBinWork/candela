package com.candela.workflow.action;

import com.engine.kq.biz.KQFormatData;
import com.weaver.general.BaseBean;
import com.weaver.general.Util;
import org.apache.log4j.Logger;
import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author linbin
 * @Create 2019-08-16-15:17
 */
public class OverTimeDetailAction extends BaseBean implements Action {

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

    Logger log=Logger.getLogger(this.getClass());

    public String execute(RequestInfo requestinfo) {

        System.out.println("进入Action requestid=" + requestinfo.getRequestid());
        String requestid = requestinfo.getRequestid();  //请求ID
        RecordSet rs = new RecordSet();
        String sql = "";
        KQFormatData kqFormatData = new KQFormatData();




        //取主表数据
        Property[] properties = requestinfo.getMainTableInfo().getProperty();
        DetailTableInfo detail = requestinfo.getDetailTableInfo();//所有明细表
        Map<String,String> map = this.getPropertyMap(properties);
        int sqr = Integer.parseInt(map.get("sqr"));
        DetailTable d_table = detail.getDetailTable(0);
        Row[] rows = d_table.getRow();
        Map<String, String> cellMap = null;
        for (Row row : rows) {
            String[] s = new String[4];
            Cell[] cells = row.getCell();
            cellMap = this.getCellMap(cells);//明细每一列的值

            String jbrq = Util.null2String(cellMap.get("jbrq"));//加班日期
            double jbsc =  Double.parseDouble(Util.null2String(cellMap.get("jbsc")));//加班时长
            String year = jbrq.substring(0,4);
            String month = jbrq.substring(5,7);
            sql = "insert into kq_balanceofleave(leaveRulesId,resourceId,belongYear,extraAmount,status,belongMonth,overtimeType,effectiveDate,isDelete,expirationDate)"+
                    "values(?,?,?,?,?,?,?,?,?,?)";
            rs.executeUpdate(sql,5,sqr,year,jbsc,0,month,4,jbrq,0,"2500-12-31");
            log.info(sql);
        }




        //控制流程流转，增加以下两行，流程不会向下流转，表单上显示返回的自定义错误信息
        requestinfo.getRequestManager().setMessagecontent("返回自定义的错误信息");
        requestinfo.getRequestManager().setMessageid("错误信息编号");
        return SUCCESS;     //return返回固定返回`SUCCESS`
    }


}
