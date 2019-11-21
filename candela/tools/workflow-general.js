/**

常用js
mid：主表
did:明细表
url:/candela/tools/getFieldId.jsp?wfid=?
*/

jQuery(document).ready(function(){
	//主表
	  var mid = { 
		
    } ;
  
	 //明细表1
	  var did1= { 
			
	   } ;

	jQuery.ajax({
		
		type:"POST",
		url: "/candela/tools/getFieldId.jsp",
		data: 'wfid='+jQuery("input[name=workflowid]").val(),
		dataType:"json",
		async: false,
		success: function(data){	
		mid = data.mid;
		did1 = data.did1;
		
		}
		
	});
	
	//事件绑定
	jQuery("#"+mid.xxx).bindPropertyChange(function(){
		
	})
	
	
}
)

/**

修改必填项
id:fieldid
type:		输入框类型 	0 输入框 1 浏览按钮
toType:		是否必填 	0 非必填 1 必填
viewtype:	视图类型 	0 非必填 1 必填
*/


function changeType(id, type, toType){
	//如果目标为必填
	if(toType == 1){ 
	//输入框类型为输入框
		if(type == 0) $("#" + id + "span").html('<img src="/images/BacoError_wev8.gif" align="absmiddle">');
		//若为浏览按钮
		else $("#" + id + "spanimg").html('<img align="absmiddle" src="/images/BacoError_wev8.gif">');

		$("#" + id).attr('viewtype','1');

		//修改验证input的值
		var baseText = $("input[name='needcheck']").val();
		//将该id加入到必填列表字符串中
		var result = (baseText == "")? id: (baseText + "," + id);
		$("input[name='needcheck']").val(result);
	} else {
		//若目标为非必填
		//输入框类型为输入框
		if(type == 0)  $("#" + id + "span").html('');
		else $("#" + id + "spanimg").html('');

		$("#" + id).attr('viewtype','0');

		//修改验证input的值
		var baseText = $("input[name='needcheck']").val();
		//将原有的需校验必填的字符串拆分出来成数组
		var baseList = baseText.split(',');
		for(var i = baseList.length - 1; i >= 0 ; i--){
			var data = baseList[i].trim();
			if((data == id) || (data == ""))
				//去除该id
				baseList.splice(i, 1);
		}
		//数组重新拼接
		$("input[name='needcheck']").val(baseList.join(','));
	}
}
	


