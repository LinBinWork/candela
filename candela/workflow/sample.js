jQuery.document.ready(function () {
/*<script type="text/javascript" src="/candela/workflow/hr/hr01.js">

 </script>*/


    var isMobile = WfForm.isMobile();

   /* WfForm.OPER_SAVE 保存
    WfForm.OPER_SUBMIT 提交/批准/提交需反馈/不需反馈等
    WfForm.OPER_SUBMITCONFIRM
    提交至确认页面，如果是确认界面，点
    确认触发的是SUBMIT
    WfForm.OPER_REJECT 退回
    WfForm.OPER_REMARK 批注提交
    WfForm.OPER_INTERVENE 干预
    WfForm.OPER_FORWARD 转发
    WfForm.OPER_TAKEBACK 强制收回
    WfForm.OPER_DELETE 删除
    WfForm.OPER_ADDROW 添加明细行，需拼明细表序号
    WfForm.OPER_DELROW 删除明细行，需拼明细表序号
    WfForm.OPER_PRINTPREVIEW 打印预览
    */

    WfForm.registerCheckEvent(WfForm.OPER_SAVE, function(callback){
        jQuery("#field27495").val("保存自动赋值");
        callback(); //继续提交需调用callback，不调用代表阻断
    });
    WfForm.registerCheckEvent(WfForm.OPER_SAVE+","+WfForm.OPER_SUBMIT,function(callback){
        //... 执行自定义逻辑
        callback();
    });
    WfForm.registerCheckEvent(WfForm.OPER_ADDROW+"1", function(callback){
        alert("添加明细1前执行逻辑，明细1则是OPER_ADDROW+1，依次类推")
        callback(); //允许继续添加行调用callback，不调用代表阻断添加
    });
    WfForm.registerCheckEvent(WfForm.OPER_DELROW+"2", function(callback){
        alert("删除明细2前执行逻辑")
        callback(); //允许继续删除行调用callback，不调用代表阻断删除
    });
    WfForm.registerCheckEvent(WfForm.OPER_PRINTPREVIEW, function(callback){
        alert("控制默认弹出的打印预览窗口")
        alert("当打印含签字意见列表，此接口需要放到跳转路由前执行，组件库提供此机制");
        window.WfForm.printTimeout = 3000; //产品是默认延时1s自动弹出，可通过此方式控
        制延时时间
        callback(); //允许继续弹出调用callback，不调用代表不自动弹预览
    });

  /*
        WfForm.ACTION_ADDROW 添加明细行
        WfForm.ACTION_DELROW 删除明细行
        WfForm.ACTION_EDITDETAILROW 移动端编辑明细行，需拼明细表序号
   */

    var fieldid = WfForm.convertFieldNameToId("zs");
    var fieldid = WfForm.convertFieldNameToId("zs_mx", "detail_1");

    var fieldid = WfForm.convertFieldNameToId("zs_mx", "detail_1", false);

    var fieldvalue = WfForm.getFieldValue("field110");

    //修改文本框、多行文本、选择框等字段类型
    WfForm.changeFieldValue("field123", {value:"1.234"});

    //修改浏览框字段的值，必须有specialobj数组结构对象
    WfForm.changeFieldValue("field11_2", {
        value: "2,3",
        specialobj:[
            {id:"2",name:"张三"},
            {id:"3",name:"李四"}
        ]
    });
    //修改check框字段(0不勾选、1勾选)
    WfForm.changeFieldValue("field123", {value:"1"});

    //针对单行文本框字段类型，只读情况，支持显示值跟入库值不一致
    WfForm.changeFieldValue("field123", {
        value: "入库真实值",
        specialobj: {
            showhtml: "界面显示值"
        }
    });
    WfForm.changeFieldAttr("field110", 1); //字段修改为只读
    WfForm.changeFieldAttr("field110", 4); //字段标签以及内容都隐藏，效果与显示属性联动隐藏一致，只支持主表字段

    WfForm.changeSingleField("field110", {value:"修改的值"}, {viewAttr:"1"});  //修改值同时置为只读

    WfForm.changeMoreField({
        field110:{value:"修改后的值"},
        field111:{value:"2,3",specialobj:[
            {id:"2",name:"张三"},{id:"3",name:"李四"}
        ]},

        },{
        field110:{viewAttr:2},
        field111:{viewAttr:3},
    });

    //表单打开强制执行某字段的联动
    jQuery(document).ready(function(){
        WfForm.triggerFieldAllLinkage("field110"); //执行字段涉及的所有联动
    });

    WfForm.getFieldInfo("111");

    WfForm.getFieldCurViewAttr("field110_2");  //获取明细字段属性，1：只读、2：可编辑、3：必填；已办全部为只读；

    WfForm.bindFieldChangeEvent("field27555,field27556", function(obj,id,value){
        console.log("WfForm.bindFieldChangeEvent--",obj,id,value);
    });

    WfForm.bindDetailFieldChangeEvent("field27583,field27584",function(id,rowIndex,value){
        console.log("WfForm.bindDetailFieldChangeEvent--",id,rowIndex,value);
    });

    //明细2添加一行并给新添加的行字段field111赋值
    WfForm.addDetailRow("detail_2",{field111:{value:"初始值"}});
    //动态字段赋值，明细1添加一行并给字段名称为begindate的字段赋值
    var begindatefield = WfForm.convertFieldNameToId("begindate", "detail_1");
    var addObj = {};
    addObj[begindatefield] = {value:"2019-03-01"};
    WfForm.addDetailRow("detail_1", addObj);

    WfForm.delDetailRow("detail_1", "all");   //删除明细1所有行
    WfForm.delDetailRow("detail_1", "3,6");   //删除明细1行标为3,6的行

    WfForm.showMessage("结束时间需大于开始时间");

    WfForm.showConfirm("确认删除吗？", function(){
        alert("删除成功");
    });

    function subimtForm(params){
        WfForm.controlBtnDisabled(true);  //操作按钮置灰
        WfForm.controlBtnDisabled(false);
    }

    WfForm.getBrowserShowName("field110"); //以逗号分隔获取浏览按钮字段显示值
    WfForm.removeSelectOption("field112", "3,4");  //移除选择框中id值为3/4的选项
    WfForm.controlSelectOption("field112", "1,2,4"); //控制选择框只显示1/2/4的选项
    WfForm.controlSelectOption("field112", ""); //清除选择框所有选项

    WfForm.getSelectShowName("field110_3"); //获取选择框字段显示值
    WfForm.setTextFieldEmptyShowContent("field27555", "单文本默认提示信息1");
    WfForm.setTextFieldEmptyShowContent("field27566", "多文本默认提示2");
    WfForm.setTextFieldEmptyShowContent("field222_0", "明细字段提示信息"); //需要结合接口5.9添加行事件一并使用

    WfForm.controlDateRange("field111", -5, 10); //限定日期可选范围，往前5天，往后10天
    WfForm.controlDateRange("field111", 0, '2019-12-31');  //限定今天至本年
    WfForm.controlDateRange("field222_0", '2019-05-01', '2019-05-31');  //明细字段,限定当月

})