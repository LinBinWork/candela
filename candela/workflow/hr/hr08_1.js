jQuery(document).ready(function() {

    var result = {} ;

    var userid = WfForm.convertFieldNameToId("sqr");
    var user = WfForm.getFieldValue(userid);
    var beginid = WfForm.convertFieldNameToId("sjjbkssj", "detail_1");
    var endid = WfForm.convertFieldNameToId("sjjbjssj", "detail_1");
    var scid = WfForm.convertFieldNameToId("jbsc", "detail_1");
    console.log(beginid,endid);
    console.log(user);

    for(var i = 0; i < $("#nodesnum0").val(); i++) {
        var jbrqid = WfForm.convertFieldNameToId("jbrq", "detail_1");
        var jbrq = WfForm.getFieldValue(jbrqid + "_" + i);
        console.log(jbrq);
    }
    WfForm.bindDetailFieldChangeEvent(jbrqid, function(id,rowIndex,value){
        console.log(id,rowIndex,value);
        jQuery.ajax({

            type: "POST",
            url: "/candela/workflow/hr/hr08_dtl.jsp",
            data: {"date":value,"userid":user},
            dataType: "json",
            async: false,
            success: function (data) {
                result = data

            }

        });
        console.log(result);

        var begin = result.begin;
        var end = result.end;
        begin = begin.substr(0, 5);
        end = end.substr(0, 5);

        console.log(begin,end);


        WfForm.changeFieldValue(beginid+"_"+rowIndex, {
            value: begin,
            specialobj:[
                {id:begin,name:begin},
            ]
        });
        WfForm.changeFieldValue(endid+"_"+rowIndex, {
            value: end,
            specialobj:[
                {id:end,name:end},
            ]
        });


    });

})


