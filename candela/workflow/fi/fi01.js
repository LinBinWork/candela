jQuery(document).ready(function() {
    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
    mid.push(WfForm.convertFieldNameToId("fylb")) ;
    mid.push(WfForm.convertFieldNameToId("fykmtj")) ;
    mid.push(WfForm.convertFieldNameToId("fykm")) ;

    console.log(mid);

    //将主表值压入mv;
    for(var i = 0;i<mid.length;i++){
        mv.push(WfForm.getFieldValue(mid[i]));
    }
    console.log(mv);

    var tovalue = "";
    //浏览按钮过滤条件
    WfForm.bindFieldChangeEvent(mid[0], function(obj,id,value){
        var type = value;
        if(type=="0"){
            WfForm.changeFieldValue(mid[1], {value:"财务类"});
        }
        if(type=="1"){
            WfForm.changeFieldValue(mid[1], {value:"行政办公类"});
        }
        if(type=="2"){
            WfForm.changeFieldValue(mid[1], {value:"工资奖金福利类"});
        }
        if(type=="3"){
            WfForm.changeFieldValue(mid[1], {value:"材料/设备款支付"});
        }
        if(type=="4"){
            WfForm.changeFieldValue(mid[1], {value:"借款类"});
        }
        if(type=="5"){
            WfForm.changeFieldValue(mid[1], {value:"业务费用"});
        }

    });

    //
    WfForm.bindFieldChangeEvent(mid[2], function(obj,id,value){
        WfForm.delDetailRow("detail_1", "all"); //删除所有行

    });
    console.log(WfForm.getBrowserShowName(mid[2]));

    WfForm.addDetailRow("detail_1",{field8097:{value:WfForm.getBrowserShowName(mid[2])}});


    //明细费用科目只取主表










})
