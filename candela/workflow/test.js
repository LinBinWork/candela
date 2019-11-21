jQuery(document).ready(function() {
    var isMobile = WfForm.isMobile();
    console.log(isMobile);

    console.log(WfForm.getBaseInfo());

   /* WfForm.showMessage("运算错误", 4, 3);

    WfForm.showConfirm("确认删除吗？", function(){
        alert("删除成功");
    });*/


    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
   mid.push(WfForm.convertFieldNameToId("sqr")) ;
   mid.push(WfForm.convertFieldNameToId("sqrbm")) ;
   mid.push(WfForm.convertFieldNameToId("tzr")) ;
   mid.push(WfForm.convertFieldNameToId("ksrq")) ;
   mid.push(WfForm.convertFieldNameToId("jsrq")) ;
   mid.push(WfForm.convertFieldNameToId("kssj")) ;
   mid.push(WfForm.convertFieldNameToId("jssj")) ;
   mid.push(WfForm.convertFieldNameToId("jblx")) ;
   mid.push(WfForm.convertFieldNameToId("sc")) ;
   mid.push(WfForm.convertFieldNameToId("bz")) ;
   mid.push(WfForm.convertFieldNameToId("qt")) ;
   console.log(mid);

   //将主表值压入mv;

   for(var i = 0;i<mid.length;i++){
       mv.push(WfForm.getFieldValue(mid[i]));
   }
    console.log(mv);

    //将明细表ID(不含行号)压入mid
    did.push(WfForm.convertFieldNameToId("ry","detail_1")) ;
    did.push(WfForm.convertFieldNameToId("rq","detail_1")) ;
    did.push(WfForm.convertFieldNameToId("sj","detail_1")) ;
    did.push(WfForm.convertFieldNameToId("km","detail_1")) ;
    did.push(WfForm.convertFieldNameToId("lx","detail_1")) ;
    did.push(WfForm.convertFieldNameToId("jg","detail_1")) ;

    console.log(did);

    //获取明细行数
    var rows = WfForm.getDetailRowCount("detail_1");


    //将主表值压入dv;
    for(var  i=0;i<rows;i++){
        for(var j=0;j<did.length;j++){
            dv.push(WfForm.getFieldValue(did[j]+"_"+i));
        }
    }
    console.log(dv);

/*
    WfForm.changeFieldValue(mid[7], {value:"1"});
    WfForm.changeFieldValue(mid[8], {value:"999"});
    WfForm.changeFieldValue(mid[9], {value:"德玛西亚"});
    WfForm.changeFieldValue(mid[10], {value:"诺克萨斯"});

    WfForm.changeFieldValue(mid[2], {
        value: "101,106",
        specialobj:[
            {id:"101",name:"张三"},
            {id:"106",name:"李四"}
        ]
    });

    WfForm.changeFieldAttr(mid[0], 1); //字段修改为只读
    WfForm.changeFieldAttr(mid[1], 2); //字段修改为可编辑
    WfForm.changeFieldAttr(mid[2], 3); //字段修改为必填
    WfForm.changeFieldAttr(mid[3], 4); //字段标签以及内容都隐藏，效果与显示属性联动隐藏一致，只支持主表字段

    WfForm.triggerFieldAllLinkage(mid[0]); //执行字段涉及的所有联动

    console.log(WfForm.getFieldCurViewAttr(mid[0])); //获取字段属性
*/
    //主表字段变化触发事件
    WfForm.bindFieldChangeEvent(mid[1], function(obj,id,value){
        WfForm.changeFieldAttr(mid[0], 4);
    });

    //明细表字段触发事件   id:字段id rowindex:行号 value:字段值
    WfForm.bindDetailFieldChangeEvent(did[0],function(id,rowIndex,value){

        console.log("WfForm.bindDetailFieldChangeEvent--",id,rowIndex,value);
    });

   /* WfForm.addDetailRow("detail_1",{field8833:{value:106}});

    WfForm.delDetailRow("detail_1", "all"); //删除所有行
*/
    //WfForm.delDetailRow("detail_1", "1,3");   //删除明细1行标为3,6的行

    WfForm.checkDetailRow("detail_1", "all");   //勾选明细1所有行

    console.log(WfForm.getDetailAllRowIndexStr("detail_1"));

    console.log(WfForm.getBrowserShowName(mid[0]),WfForm.getBrowserShowName(mid[1]),WfForm.getBrowserShowName(mid[2]),WfForm.getBrowserShowName(mid[3]),WfForm.getBrowserShowName(mid[5]));

    console.log(WfForm.getSelectShowName(mid[7]));

    WfForm.setTextFieldEmptyShowContent(mid[8], "填写时长");

   WfForm.controlDateRange(mid[3], -5, 10);

    WfForm.registerCheckEvent(WfForm.OPER_SAVE, function(callback){
        jQuery("#field27495").val("保存自动赋值");
        callback(); //继续提交需调用callback，不调用代表阻断
    });
})
