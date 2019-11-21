jQuery(document).ready(function() {
    //主表
    var mid = {};

    //明细表1
    var did1 = {};

    var sign = {"choice":"","day1":"","day2":"","userid":"","up":"","off":"","signdate":""};

    jQuery.ajax({

        type: "POST",
        url: "/candela/tools/getFieldId.jsp",
        data: 'wfid=' + jQuery("input[name=workflowid]").val(),
        dataType: "json",
        async: false,
        success: function (data) {
            mid = data.mid;
            did1 = data.did1;

        }

    });


    var day1 =  WfForm.getFieldValue(mid.jbksrq);
    var day2 =  WfForm.getFieldValue(mid.jbjsrq);
    var time1 =   WfForm.getFieldValue(mid.jbkssj);
    var time2=  WfForm.getFieldValue(mid.jbjssj);
    var choice =  WfForm.getFieldValue(mid.jblx);
    var userid =  WfForm.getFieldValue(mid.sqr);
    var location =  WfForm.getFieldValue(mid.bgdd);
    
    console.log(day1,day2,time1,time2,choice,userid,location);
    
    var t1 = ""; //午休结束时间
    var t2 = ""; //下班时间
    var h1 = 0; //午休时长
    var h2 = 0; //上限
    if(location == 5){
        t1 = "13:00";
        t2 = "17:30"
        h1 = 1;
        h2 = 8;
    }
    else{
        t1 = "13:30";
        t2 = "18:30"
        h1 = 1.5;
        h2 = 7.5;
    }

    if(choice==3){
        jQuery.ajax({
            type: "POST",
            url: "/candela/workflow/hr/hr08.jsp",
            data: {"day1":day1,"day2":day2,"choice":choice,"userid":userid},
            dataType: "json",
            async: false,
            success: function (data) {
                sign = data;
            }

        });
        sign.up = time1;
    }
    if(choice==0){
        jQuery.ajax({
            type: "POST",
            url: "/candela/workflow/hr/hr08.jsp",
            data: {"day1":day1,"day2":day2,"choice":choice,"userid":userid},
            dataType: "json",
            async: false,
            success: function (data) {
                sign = data;
            }

        });
    }
    if(choice==1 || choice == 2){
        sign.up = time1;
        sign.off = time2;
    }
    jQuery("#"+mid.sjjbkssj).val(sign.up);
    jQuery("#"+mid.sjjbjssj).val(sign.off);

    sign.day1 = day1;
    sign.day2 = day2;
    sign.userid = userid;
    sign.choice = choice;



    var dt1 = new Date(day1+" "+sign.up);
    var dt2 ='';
    if(sign.signdate=='' || sign.signdate == undefined){
         dt2 = new Date(day2+" "+sign.off);
    }
    else{
         dt2 = new Date(sign.signdate+" "+sign.off);
    }
    var h = 0 ;
    console.log("dt1."+dt1);
    console.log("dt2."+dt2);

    if(choice==3){
        h = ( dt2.getTime()-dt1.getTime())/1000/60/60;
        h = h>7.5 ? 7.5:h;
        console.log(h);
    }
    else{
        var sd1 = new Date(day1+" 12:00");
        var sd2 = new Date(day1+" "+t1);
        if(dt1<sd1 && dt2>sd2){
            h= (dt2.getTime() - dt1.getTime())/1000/60/60 - h1;
        }
        if(dt1>sd1 && dt1<sd2 && dt2>sd2){
            h =  ( dt2.getTime()-sd2.getTime())/1000/60/60;
        }
        if((dt1<sd1 && dt2<sd1) || (dt1>sd2 && dt2>sd2)){
            h =  ( dt2.getTime()-dt1.getTime())/1000/60/60;
        }
        if(dt2>sd1 && dt2<sd2 && dt1<sd1){
            h =  ( sd1.getTime()-dt1.getTime())/1000/60/60;
        }
        h = h > h2 ? h2:h;
        console.log(h);
    }
    if(h>0){
        console.log("--->"+h);
        jQuery("#"+mid.sjsc).val(h);
    }
    if(sjjbkssj=='' || sjjbjssj==''){
        jQuery("#"+mid.sjsc).val(0);
    }
})


