jQuery(document).ready(function() {

    var mid = [];  //主表ID
    var mv = []; //主表ID对应的值
    var did = []; //明细表对应的id
    var dv = []; //明细表对应的值
    var info = []; //字段信息
    var broser = [] ;//浏览框对应的中文值

    //将主表ID压入mid
    mid.push(WfForm.convertFieldNameToId("xqksrq")) ;
    mid.push(WfForm.convertFieldNameToId("xqjsrq")) ;

    console.log(mid);

    //将主表值压入mv;
    for(var i = 0;i<mid.length;i++){
        mv.push(WfForm.getFieldValue(mid[i]));
    }
    console.log(mv);

    window.checkCustomize =()=>{
        var flag = true;
        var begin = WfForm.getFieldValue(mid[0]);
        var end = WfForm.getFieldValue(mid[1]);
        if(begin>=end){
            WfForm.showMessage("结束日期需大于开始日期",5);
            flag = false;
        }
        return flag;
    }

})