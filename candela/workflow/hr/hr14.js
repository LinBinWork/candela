jQuery(document).ready(function() {

    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
    mid.push(WfForm.convertFieldNameToId("sqr")) ;
    mid.push(WfForm.convertFieldNameToId("gw")) ;
    mid.push(WfForm.convertFieldNameToId("lzrq")) ;
    mid.push(WfForm.convertFieldNameToId("bt")) ;
    console.log(mid);


    //将主表值压入mv;

    for(var i = 0;i<mid.length;i++){
        mv.push(WfForm.getFieldValue(mid[i]));
    }

    var lastname = WfForm.getBrowserShowName(mid[0]);
    var gw = WfForm.getBrowserShowName(mid[1]);
    var lzrq = WfForm.getBrowserShowName(mid[2]);
    var bt = "HR14-离职交接流程-"+lastname+"-"+gw+"-"+lzrq;
    WfForm.changeFieldValue(mid[3], {value:bt});
    console.log(bt);

    //标题
    WfForm.bindFieldChangeEvent(mid[2], function(obj,id,value){
        var lastname = WfForm.getBrowserShowName(mid[0]);
        var gw = WfForm.getBrowserShowName(mid[1]);
        var bt = "HR14-离职交接流程-"+lastname+"-"+gw+"-"+value;
        WfForm.changeFieldValue(mid[3], {value:bt});
        console.log(bt);
    });



})