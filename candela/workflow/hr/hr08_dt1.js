jQuery(document).ready(function() {

    var result = {} ;

    var userid = WfForm.convertFieldNameToId("sqr");
    var user = WfForm.getFieldValue(userid); //申请人
    var beginid = WfForm.convertFieldNameToId("sjjbkssj", "detail_1"); //实际开始时间id
    var endid = WfForm.convertFieldNameToId("sjjbjssj", "detail_1"); //实际结束时间id
    var scid = WfForm.convertFieldNameToId("jbsc", "detail_1"); //时长id
    var jbkssjid = WfForm.convertFieldNameToId("jbkssj", "detail_1"); //开始时间id
    var jbjssjid = WfForm.convertFieldNameToId("jbjssj", "detail_1"); //结束时间id

    var typeid =  WfForm.convertFieldNameToId("jblx");
    var type = WfForm.getFieldValue(typeid); //加班类型

    for(var i = 0; i < $("#nodesnum0").val(); i++) {
        var jbrqid = WfForm.convertFieldNameToId("jbrq", "detail_1");
        var jbrq = WfForm.getFieldValue(jbrqid + "_" + i);
        var jbkssj = WfForm.getFieldValue(jbkssjid + "_" + i);
        var jbjssj = WfForm.getFieldValue(jbjssjid + "_" + i);
        var begin = "";
        var end = "";
        console.log(type);
        if(type == 0 || type == 4)
        {

            jQuery.ajax({

                type: "POST",
                url: "/candela/workflow/hr/hr08_dtl.jsp",
                data: {"date":jbrq,"userid":user},
                dataType: "json",
                async: false,
                success: function (data) {
                    result = data

                }

            });
            console.log(result);

            begin = result.begin;
            end = result.end;
            begin = begin.substr(0, 5);
            end = end.substr(0, 5);



            WfForm.changeFieldValue(beginid+"_"+i, {
                value: begin,
                specialobj:[
                    {id:begin,name:begin},
                ]
            });
            WfForm.changeFieldValue(endid+"_"+i, {
                value: end,
                specialobj:[
                    {id:end,name:end},
                ]
            });
            var hour = getHour(jbrq,begin,end);
        }
        else{
            WfForm.changeFieldValue(beginid+"_"+i, {
                value: jbkssj,
                specialobj:[
                    {id:jbkssj,name:jbkssj},
                ]
            });
            WfForm.changeFieldValue(endid+"_"+i, {
                value: jbjssj,
                specialobj:[
                    {id:jbjssj,name:jbjssj},
                ]
            });
            var hour = getHour(jbrq,jbkssj,jbjssj);
        }

        WfForm.changeFieldValue(scid+"_"+i, {value:hour});

    }


})
function getHour(v_date,start,end) {
    var noon = "12:00";
    var afternoon = "13:30";
    var night = "23:59";
    var d1;
    var d2;
    var rest = 0;
    if(start<noon){
        if(end<noon){
          d1 =new Date(v_date+" "+start);
          d2 =new Date(v_date+" "+end);
        }
        if(end>=noon && end <= afternoon){
            d1 =new Date(v_date+" "+start);
            d2 =new Date(v_date+" "+noon);
        }
        if(end>afternoon){
            d1 =new Date(v_date+" "+start);
            d2 =new Date(v_date+" "+end);
            rest = 1.5;
        }
    }
    if(start>=noon && start<=afternoon){
        if(end>afternoon){
            d1 =new Date(v_date+" "+afternoon);
            d2 =new Date(v_date+" "+end);
        }

    }
    if(start>afternoon){
        d1 =new Date(v_date+" "+start);
        d2 =new Date(v_date+" "+end);
    }
    if(end<'06:00'){
        d2 =new Date(v_date+" "+night);
    }
    var hour = (d2.getTime()-d1.getTime())/1000/60/60-rest;
    hour = hour > 7.5? 7.5:hour;
    return hour;


}


