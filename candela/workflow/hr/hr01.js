jQuery(document).ready(function() {

    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
    mid.push(WfForm.convertFieldNameToId("yjbm")) ;
    mid.push(WfForm.convertFieldNameToId("ejbm")) ;
    mid.push(WfForm.convertFieldNameToId("sjbm")) ;
    mid.push(WfForm.convertFieldNameToId("xm")) ;
    mid.push(WfForm.convertFieldNameToId("rzgw")) ;
    mid.push(WfForm.convertFieldNameToId("rzrq")) ;
    mid.push(WfForm.convertFieldNameToId("bt")) ;
    console.log(mid);


    //将主表值压入mv;

    for(var i = 0;i<mid.length;i++){
        mv.push(WfForm.getFieldValue(mid[i]));
    }
    console.log(mv);
    //部门联动防呆
    WfForm.bindFieldChangeEvent(mid[0], function(obj,id,value){
        WfForm.changeFieldValue(mid[1], {
            value: "",
            specialobj:[
            ]
        });
        WfForm.changeFieldValue(mid[2], {
            value: "",
            specialobj:[
            ]
        });
    });
    WfForm.bindFieldChangeEvent(mid[1], function(obj,id,value){
        WfForm.changeFieldValue(mid[2], {
            value: "",
            specialobj:[
            ]
        });
    });
    var xm = WfForm.getFieldValue(mid[3]);
    var gw = WfForm.getFieldValue(mid[4]);
    var rzrq = WfForm.getFieldValue(mid[5]);
    var bt = "HR01-入职流程-"+xm+"-"+gw+"-"+rzrq;
    WfForm.changeFieldValue(mid[6], {value:bt});
    console.log(bt);

    //标题

    WfForm.bindFieldChangeEvent(mid[5], function(obj,id,value){
        xm = WfForm.getFieldValue(mid[3]);
        gw = WfForm.getFieldValue(mid[4]);
        bt = "HR01-入职流程-"+xm+"-"+gw+"-"+value;
        WfForm.changeFieldValue(mid[6], {value:bt});
        console.log(bt);
    });


})
