jQuery(document).ready(function() {
    //主表
    var mid = {};

    //明细表1
    var did1 = {};
    var isMobile = WfForm.isMobile();
	
    if(isMobile){
       
        window.checkCustomize =()=>{

            var jbksrqid = WfForm.convertFieldNameToId("jbksrq");
            var jbjsrqid = WfForm.convertFieldNameToId("jbjsrq");
            var jbkssjid = WfForm.convertFieldNameToId("jbkssj");
            var bgddid = WfForm.convertFieldNameToId("bgdd");
            var sqrid = WfForm.convertFieldNameToId("sqr");

            var day1 = WfForm.getFieldValue(jbksrqid);
            var day2 = WfForm.getFieldValue(jbjsrqid);
            var time1 = WfForm.getFieldValue(jbkssjid);
            var location = WfForm.getFieldValue(bgddid);
            var t = "";
            var flag = 0;
            if (location == 5) {
                t = "17:30";
            }
            else {
                t = "18:30";
            }
            if (time1 >= t && day1 == day2) {
                flag = 1;
            }
            if (flag == 1) {
                alert("工作日加班必须加班至次日凌晨方可提加班流程调休，否则不允许提加班流程！");
				//top.Dialog.alert("工作日加班必须加班至次日凌晨方可提加班流程调休，否则不允许提加班流程！");
                return false;
            }
            else {
                return true;
            }
        }
    }
    else{
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



        //重写checkCustomize函数
        checkCustomize = function (){
            // 请假天数为空的时候，不允许流程提交
            // 10146为字段的id，我这边演示的请假天数这个字段的id
            // 大家需要根据自己的系统来做对应的修改
            //
            // 函数最后需要有一个返回值，返回false时候，不能提交流程。
            // 返回true时，流程正常流转
            //var field10146value = jQuery("#field10146").val();
            var day1 = jQuery("#"+mid.jbksrq).val();
            var day2 = jQuery("#"+mid.jbjsrq).val();
            var time1 =  jQuery("#"+mid.jbkssj).val();
            var location = jQuery("#"+mid.bgdd).val();
            var t = "";
            var flag = 0;

            if(location == 5){
                t="17:30";
            }
            else{
                t="18:30";
            }
            if(time1>=t && day1==day2){
                flag = 1;
            }
            console.log(day1);
            console.log(day2);
            console.log(time1);
            console.log(flag);
            if(flag==1){
				top.Dialog.alert("工作日加班必须加班至次日凌晨方可提加班流程调休，否则不允许提加班流程！");
               //alert("工作日加班必须加班至次日凌晨方可提加班流程调休，否则不允许提加班流程！");
                return false;
            }
            else{
                return true;
            }
        }
    }



})


