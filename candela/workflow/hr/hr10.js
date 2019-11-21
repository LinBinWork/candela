jQuery(document).ready(function() {

    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
    mid.push(WfForm.convertFieldNameToId("xm")) ;
    mid.push(WfForm.convertFieldNameToId("rzrq")) ;
    mid.push(WfForm.convertFieldNameToId("szgw")) ;
    mid.push(WfForm.convertFieldNameToId("bt")) ;

    console.log(mid);

    //将主表值压入mv;
    for(var i = 0;i<mid.length;i++){
        mv.push(WfForm.getFieldValue(mid[i]));
    }
    console.log(mv);

    //获取标题
    WfForm.bindFieldChangeEvent(mid[2], function(obj,id,value){
        var xm = WfForm.getFieldValue(mid[0]);
        var rzrq = WfForm.getFieldValue(mid[1]);
        var bt = "HR10-录用流程-"+xm+"-"+rzrq+"-"+value;
        WfForm.changeFieldValue(mid[3], {value:bt});
    });



})