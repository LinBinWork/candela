/**
 * 请假申请流程--创建节点JS
 * @author Lij
 * @date 2017-05-24
 * @version 1.0.0
 * @encoding utf-8
 * @url <script type="text/javascript" src="/westvalley/workflow-buryyd.js"></script>
 * <p>
 * 主表表单元素调用：wf_main_fields.字段名称
 * 明细一表单元素调用：wf_dt1_fields.字段名称
 * 方法集主要包括：
 * 1.业务，对某个控件赋值
 */

//要解决兼容性的id集合  格式：#field7865,#field7865。。。。
//var excutMID="#"+wf_main_fields.kaistxrq+",#"+wf_main_fields.kaistxsj+
	//",#"+wf_main_fields.jiestxtq+",#"+wf_main_fields.jiestxsj;
//要解决兼容性的id集合  格式：[id,id,id]
//var excutDID=[wf_dt1_fields.kaissj,wf_dt1_fields.jiessj];
  var t0 = { 
		bum : '' /*部门 */
	   ,code : '' /*科目*/ 
	   ,shenqr : 'field9184' /*申请人*/ 
	   ,shqrq : '' /*申请日期*/ 
	   ,bcjkje : ''/*本次借款金额*/ 
	   ,yjhkrq : ''/*预计还款日期*/
	   ,jbshy :''/*加班事由*/ //field6335
	   ,jbshch :''/*小时时长*/ //field6336
	   ,glwd :''/*文档*/ //field6339
	   ,beiz :''/*备注*/
	   ,fenb :''/*分部*/
	   ,code :''/*科目*/
    } 
  
 
  var t1 = { 
		remark : '' /*备注*/ 
		,name : '' /*姓名*/ 
	   ,kaisrq: '' /*开始日期*/ 
	   ,mingc: '' /*开始日期*/ 
	   ,jiesrq: '' /*结束日期*/
	   ,beiz: '' /*备注*/
   } 
  


jQuery(document).ready(function(){
	

	jQuery.ajax({
		type:"POST",
		url: "/westvalley/cw/getfieldid.jsp",
		data: 'wid='+jQuery("input[name=workflowid]").val(),
		dataType:"json",
		async: false,
		success: function(data){	
		t0 = data.mid;
		t1 = data.did1;
		//t2 = data.did2;
		}
		
	});
	
	
	jQuery("#hhh").hide();
	
	var of = window.doSubmit;
	window.doSubmit = function(p){
		var index = jQuery("#indexnum0").val() * 1.0;
		for (var i = 0; i < index; i++) {
			alert(jQuery("#"+t1.kaisrq+"_"+i).val());
		}
		return false;
			of(p);
	}
	
	//监听明细行增加
       jQuery("#indexnum0").bindPropertyChange(function(){
           //获取到明细下标
		var index = jQuery("#indexnum0").val()-1;
           //给新增的明细行字段添加监听事件
               //jQuery("#"+t1.kaisrq+"_"+index).bind("change",function(){
               	jQuery("#"+t1.jiesrq+"_"+index).bindPropertyChange(function(){
            	   //alert("1111");
            	   top.Dialog.alert("1111");
               		/**
            	   jQuery.ajax({
            			type:"POST",
            			url: "/westvalley/workflowServlet.jsp?cmd=getHrToBumryyd",
            			data: 'jiablx='+this.value,
            			dataType:"text",
            			success: function(data){
            			  $("#"+did1.QG010+"_"+index).val(data);
            			}
            		});
            		*/
            		 
              });
	});

		
	var addRowsFun = window.addRow0;//绑定到相应的明细表的添加按钮 onclick="addRow0(0)"
	window.addRow0 = function(){
		//addRowsFun();		
		addRowsFun(0);
		var j= jQuery("#indexnum0").val() * 1.0 - 1;
		jQuery("#"+t1.beiz+"_"+j).val("MM");
		
		var strBtn = "<input type=\"button\" id = \"" + "MT" + bz + "\" class=\"e8_btn_top_first\" _disabled=\"true\" value=\"查看\" title=\"查看\" style=\"max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;\">";
		$("tr[_rowindex = '" + bz + "']").find("td[id = 'tuxingliulan_"+bz+"']").html(strBtn);
		$("#" + "MT" + bz).click(function(){
			//alert('test'+bz);
			//window.location.href="http://219.159.250.198:8045/glonemap/?layer=BP&PK=";
			var pk = jQuery('#field12127_' + bz).val();
			window.open("http://219.159.250.198:8045/glonemap/?layer=BP&PK="+pk); 
		});
	}
	
	jQuery("#haha").hide();
	
	jQuery("#"+t0.beiz).bind("change",function(){
		alert("t0.beiz:"+t0.beiz);
		//var remark = jQuery("#"+t0.jbshy).val();
		//alert(remark);
		//jQuery("#"+t0.code+"_browserbtn").hide();
		//jQuery("#"+t0.code+"_browserbtn").show();
	});
	
	
	
	
	jQuery("#"+t0.shenqr).bindPropertyChange(function(){
		var fenb = jQuery("#"+t0.shenqr).val();
		alert(fenb);
		//jQuery("#"+t0.jbshch).val(jbshy);
		//jQuery("#xs").hide();
		//jQuery("#haha").show();
		
		//获取人力资源信息
		jQuery.ajax({
			type:"post",
			url:"/westvalley/workflowServlet.jsp?cmd=getZT",
			data:"code=1",
			dataType:"text",
			async:false,
			success:function(data){
				var returnJson=eval("("+data+")");
				alert('returnJson.msg:'+returnJson.msg);
				jQuery("#"+t0.bez).val(returnJson.msg);
			},
			error : function(data) {
				//alert("获取信息失败");
				top.Dialog.alert("获取信息失败，数据异常！");
			}
		});
		
		
		
	});
	
	
	
});

//转换数据
function parseFloat_(v){
	return isNaN(v)? 0 : parseFloat(v);
}

