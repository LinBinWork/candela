jQuery(document).ready(function() {
    //主表
    var mid = {};

    //明细表1
    var did1 = {};
    var isMobile = WfForm.isMobile();
    top.Dialog.alert("ok");
    if(isMobile){
        window.checkCustomize =()=>{

        var qjscid = WfForm.convertFieldNameToId("qjsc");
        var qjlxid = WfForm.convertFieldNameToId("qjlx");
        var qjsc = WfForm.getFieldValue(qjscid);
        var qjlx = WfForm.getFieldValue(qjlxid);
        var flag = true;
        if(qjlx == 5 && qjsc < 2.5){
            flag = false;
            alert("调休假请假时长不得低于2.5小时 ！");
        }
        if(qjlx == 6 && qjsc < 1){
            flag = false;
            alert("事假请假时长不得低于1小时 ！");
        }
        return flag;
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
            var qjlx = jQuery("#"+mid.qjlx).val();
            var qjsc = jQuery("#"+mid.qjsc).val();
            var flag = true;
            if(qjlx == 5 && qjsc < 2.5){
                flag = false;
                top.Dialog.alert("调休假请假时长不得低于2.5小时 ！");
            }
            if(qjlx == 6 && qjsc < 1){
                flag = false;
                top.Dialog.alert("事假请假时长不得低于1小时 ！");
            }
            console.log(flag);
            console.log(qjlx);
            console.log(qjsc);
            return flag;



        }
    }



})


