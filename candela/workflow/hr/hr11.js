jQuery(document).ready(function() {

    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
    mid.push(WfForm.convertFieldNameToId("dryjbm")) ;
    mid.push(WfForm.convertFieldNameToId("drejbm")) ;
    mid.push(WfForm.convertFieldNameToId("drsjbm")) ;
    mid.push(WfForm.convertFieldNameToId("drbgdd")) ;
    mid.push(WfForm.convertFieldNameToId("sqr")) ;
    mid.push(WfForm.convertFieldNameToId("ddrq")) ;
    mid.push(WfForm.convertFieldNameToId("bt")) ;
    mid.push(WfForm.convertFieldNameToId("bgdd")) ;
    mid.push(WfForm.convertFieldNameToId("sfqynwdd")) ;

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


    //办公地点改动触发
    WfForm.bindFieldChangeEvent(mid[3], function(obj,id,value){
        var location = WfForm.getFieldValue(mid[7]);
        if(location==value){
            WfForm.changeFieldValue(mid[8], {value:"1"});
        }else{
            WfForm.changeFieldValue(mid[8], {value:"0"});
        }
    });


    //标题
    WfForm.bindFieldChangeEvent(mid[5], function(obj,id,value){
        var lastname = WfForm.getBrowserShowName(mid[4]);
        var bt = "HR11-调动流程-"+lastname+"-"+value;
        WfForm.changeFieldValue(mid[6], {value:bt});
        console.log(bt);
    });



})