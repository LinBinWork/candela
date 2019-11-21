/**
 * 财务流程工具类
 * @author liyi 2019-4-8
 * 
 * 

<script type='text/javascript'>
      document.write("<s"+"cript type='text/javascript' src='/tools/js/common.js?"+(new Date()).valueOf()+"'></scr"+"ipt>"); 
      document.write("<s"+"cript type='text/javascript' src='/tools/js/wfTools.js?"+(new Date()).valueOf()+"'></scr"+"ipt>"); 
</script>

 */

//表单信息
var m;
var d1;
var d2;
var d3;
var d4;
var d5;
var d6;
var d7;
var d8;
var ft; //1：单行文本框 2：多行文本框 3：浏览按钮 4：Check框 5：选择框

//记录是否是移动端 true=移动端
var mobile = (function () {
    if (typeof js_nodeid == "undefined") {
        return false;
    } else {
        //加载移动控制台
        $('#header').live("click", function () {
            loadMConsole();
        });
        return true;
    }
})()


/**
 * 页面加载完成事件
 */
$(function () {
    /**
     * 初始化字段信息
     */
    initFieldInfo();

    $('textarea').css('width', '95%');

    if (console && console.log) {
        /**
         * 打印出当前节点信息
         * wf__info.f_bel_userid        申请人ID
         * wf__info.nodeid              节点ID
         * wf__info.requestid           请求ID, =0是代表是申请节点
         * wf__info.workflowid          流程ID
         */
        console.log(wf__info);
    }
});




/**
 * 初始化流程字段信息
 */
function initFieldInfo() {
    Ajax("/tools/jsp/GetFieldId.jsp", "workflowId=" + wf__info.workflowid, function (data) {
        var info = new Array();
        ft = data.ft;
        info.push(new _f('ft', ft, '1单行,2多行,3浏览按钮,4Ck,5下拉框,6附件,7特殊字段,9位置'));
        if (!isNull(data.mid)) {
            m = data.mid;
            info.push(new _f('m', m));
        }
        if (!isNull(data.did1)) {
            d1 = data.did1;
            info.push(new _f('d1', d1));
        }
        if (!isNull(data.did2)) {
            d2 = data.did2;
            info.push(new _f('d2', d2));
        }
        if (!isNull(data.did3)) {
            d3 = data.did3;
            info.push(new _f('d3', d3));
        }
        if (!isNull(data.did4)) {
            d4 = data.did4;
            info.push(new _f('d4', d4));
        }
        if (!isNull(data.did5)) {
            d5 = data.did5;
            info.push(new _f('d5', d5));
        }
        if (!isNull(data.did6)) {
            d6 = data.did6;
            info.push(new _f('d6', d6));
        }
        if (!isNull(data.did7)) {
            d7 = data.did7;
            info.push(new _f('d7', d7));
        }
        if (!isNull(data.did8)) {
            d8 = data.did8;
            info.push(new _f('d8', d8));
        }
        if (console && console.log) {
            console.log(info)
        }

        function _f(name, arr, desc) {
            this.name = name;
            this.arr = arr;
            this.desc = desc;
        }

    }, function (err) {
        alert('页面加载异常... 需重新加载页面.')
        window.location.reload()
    }, null, 1500, false);
}


/**
 * 通用ajax入口
 * @param url  连接地址
 * @param _data  传输参数
 * @param functionEx  回调函数
 * @param functionErr  错误回调函数
 * @param dataType  类型 默认 json  text
 * @param timeout  超时时间  默认 10000
 * @param async  是否异步   默认true 是 false同步
 * @returns {String}
 */
function Ajax(url, _data, functionEx, functionErr, dataType, timeout, async) {
    if (isNull(url)) {
        alert("Ajax的url不能为空！");
        return false;
    }
    if (isNull(_data)) {
        _data = null;
    }
    if (isNull(dataType)) {
        dataType = "json";
    }
    if (isNull(timeout)) {
        timeout = null;
    }
    if (async == null) {
        async = true;
    } else if (async == false) {
        timeout = 100000;
    }
    jQuery.ajax({
        type: "POST",
        timeout: timeout,
        async: async,
        url: url,
        data: _data,
        dataType: dataType,
        success: function (data) {
            functionEx(data);
        },
        error: function (msg) {
            var t = msg;
            if (jQuery.isFunction(functionErr)) {
                functionErr(msg);
            }
            //alert("系统异常！");
        }
    });
}

/**
 * 修改字段状态
 * @param {*} _id 字段ID
 * @param {*} type 1=编辑,2只读,3必填
 * @param {*} _bIsClear 是否需要清空字段
 */
function _fChange(_id, type, _bIsClear) {
    _id = clearStr(_id);
    var fieldType = _getFieldType(_id);

    // _bIsClear不等于未定义 并且等于true 清空当前字段
    if (_bIsClear != undefined && _bIsClear) {
        //先将所有字段设置为编辑
        setFieldReadOnly(_id, false, 1)
        if (fieldType == 1) { //'1：单行文本框'
            $('#field' + _id).val('')
        } else if (fieldType == 2) { //'1：多行文本框'
            $('#field' + _id).val('')
        } else if (fieldType == 3) {
            //浏览按钮
            _browseButClear(_id);
        } else if (fieldType == 4) { //  console.log('4：Check框')
            document.getElementById('field' + _id).value = 0;
            document.getElementById('field' + _id).checked = false;
        } else if (fieldType == 5) { //  console.log('5：选择框')
            $('#field' + _id).val('')
        }
    }

    //PC端操作  1=编辑,2只读,3必填
    if (type == 1) {
        setFieldReadOnly(_id, false, 1)
        //针对Checkbox的操作
        if (fieldICheckbox(fieldType)) {
            document.getElementById('field' + _id).disabled = false;
        }
    } else if (type == 2) {
        //先恢复成默认状态再只读,防止红色感叹号
        setFieldReadOnly(_id, false, 1)
        setFieldReadOnly(_id, true);
        _C.rs("field" + _id, false)
        //针对Checkbox的操作
        if (fieldICheckbox(fieldType)) {
            document.getElementById('field' + _id).disabled = true;
        }
    } else if (type == 3) {
        setFieldReadOnly(_id, false, 1)
        _C.rs("field" + _id, true)
        //  setFieldReadOnly(_id, false, 2)
        //针对Checkbox的操作
        if (fieldICheckbox(fieldType)) {
            document.getElementById('field' + _id).disabled = false;
        }
    }

    /**
     * 判断是否是勾选框
     * @param {*} fieldType 
     */
    function fieldICheckbox(fieldType) {
        if (fieldType == 4) {
            return true;
        }
        return false;
    }

}

/**
 * 修改字段状态
 * @param {*} _id 字段ID
 * @param {*} type 1=编辑,2只读,3必填
 * @param {*} _bIsClear 是否需要清空字段
 */
function _fChange(_id, type, _bIsClear) {
    _id = clearStr(_id);
    var fieldType = _getFieldType(_id);

    // _bIsClear不等于未定义 并且等于true 清空当前字段
    if (_bIsClear != undefined && _bIsClear) {
        //先将所有字段设置为编辑
        setFieldReadOnly(_id, false, 1)
        if (fieldType == 1) { //'1：单行文本框'
            $('#field' + _id).val('')
        } else if (fieldType == 2) { //'1：多行文本框'
            $('#field' + _id).val('')
        } else if (fieldType == 3) {
            //浏览按钮
            _browseButClear(_id);
        } else if (fieldType == 4) { //  console.log('4：Check框')
            document.getElementById('field' + _id).value = 0;
            document.getElementById('field' + _id).checked = false;
        } else if (fieldType == 5) { //  console.log('5：选择框')
            $('#field' + _id).val('')
        }
    }

    //PC端操作  1=编辑,2只读,3必填
    if (type == 1) {
        setFieldReadOnly(_id, false, 1)
        //针对Checkbox的操作
        if (fieldICheckbox(fieldType)) {
            document.getElementById('field' + _id).disabled = false;
        }
    } else if (type == 2) {
        //先恢复成默认状态再只读,防止红色感叹号
        setFieldReadOnly(_id, false, 1)
        setFieldReadOnly(_id, true);
        _C.rs("field" + _id, false)
        //针对Checkbox的操作
        if (fieldICheckbox(fieldType)) {
            document.getElementById('field' + _id).disabled = true;
        }
    } else if (type == 3) {
        setFieldReadOnly(_id, false, 1)
        _C.rs("field" + _id, true)
        //  setFieldReadOnly(_id, false, 2)
        //针对Checkbox的操作
        if (fieldICheckbox(fieldType)) {
            document.getElementById('field' + _id).disabled = false;
        }
    }

    /**
     * 判断是否是勾选框
     * @param {*} fieldType 
     */
    function fieldICheckbox(fieldType) {
        if (fieldType == 4) {
            return true;
        }
        return false;
    }
}

/**
 * 向浏览按钮中添加一条数据 
 * -适用于单多人,明细,主表
 * @param {*} _addFeild  要添加的按钮ID
 * @param {*} _showText  显示的文本
 * @param {*} _id   主键
 * @param {*} _bIsNode  按钮是否是单人 true=单人 ,false=多人
 * @author 李翼  2019.1.18
 */
function _browseButAdd(_addFeild, _id, _showText, _bIsNode) {
    if (_addFeild == null || _addFeild == '' || _id == null || _id == '' || _showText == null || _showText == '' || _bIsNode == null || _bIsNode == '') {
        if (console && console.log) console.log("赋值浏览按钮参数不完整...");
        return false;
    }
    var html = '<span class="e8_showNameClass">' +
        '<a title="' + _id + '">' + _showText + '</a>' +
        '&nbsp;' +
        '<span class="e8_delClass" id="' + _id + '"onclick="del(event,this,1,false,{});"' +
        'style="opacity: 1; visibility: hidden;">&nbsp;x&nbsp;</span>' +
        '</span>';

    var valArr = new Array();
    var parentSpanId = '#field' + clearStr(_addFeild);

    if (_bIsNode) {
        //单人浏览按钮
        _browseButClear(_addFeild);
        valArr.push(_id);
    } else {
        //多人浏览按钮
        //查找所有元素.判断此值是否已存在,如果存在判断是否重复
        var valInfo = $(parentSpanId + 'span' + ' span[class=e8_delClass]')
        if (valInfo.length > 0) {
            for (var a = 0; a < valInfo.length; a++) {
                var id_val = $(valInfo[a]).attr('id');
                if (id_val == _id) {
                    //存在不操作.
                    return;
                } else {
                    valArr.push(id_val);
                }
            }
            valArr.push(_id);
        } else {
            valArr.push(_id);
        }
    }
    $(parentSpanId + 'span').append(html);
    $(parentSpanId).val(valArr)
    return true;
}

/**
 * 重写提交校验方法 PC-移动端通用
 * 
 * @param {*} _fun  具体校验方法  无返回值=通过;返回false=阻止
 */
function _submitVerify(_fun) {
    if (mobile) {
        var _systemHandleFunction = dosubmit;
        dosubmit = function (btnobj) {
            var result = _fun();
            if (result == false) {
                if (console && console.error) { console.error('[重写] 提交校验不通过 阻止提交!') }
            } else { _systemHandleFunction(btnobj); }
        }
    } else {
        var submitVerify = window.doSubmit;
        window.doSubmit = function (p) {
            var result = _fun();
            if (result == false) {
                if (console && console.error) { console.error('[重写] 提交校验不通过 阻止提交!') }
            } else { submitVerify(p); }
        }
    }
}


/** 
 *    ***  工具方法    ---   工具方法  ---  工具方法    ---   工具方法    ---   工具方法    ---   工具方法    ***
 */


/**
 * 判断是否为空
 */
function isNull(v) {
    if (typeof (v) == "undefined" || v == "" || v == null) {
        return true;
    }
    return false;
}

/**
 * 去掉 'field' 和 空串
 * @param {*} str           源字符串
 * @param {*} _clearStr     要删除的字符串
 * @author 李翼  2019.1.18
 */
function clearStr(str, _clearStr) {
    if (_clearStr == undefined) {
        str = str.replace('field', ""); //取消字符串中出现的所有逗号 
    } else {
        str = str.replace(_clearStr, ""); //取消字符串中出现的所有逗号 
    }
    return str;
}

/**
 *  去掉字符串首尾空格
 * @param {*} v 值
 */
function toTm(v) {
    if (v == null || v == undefined) {
        return "";
    } else {
        return v.trim();
    }
}

/**
 *  空转空串
 */
function nullToStr(v) {
    if (typeof (v) == "undefined" || v == "" || v == null) {
        return "";
    }
    return v;
};

/**
 *说明：判断value变量值是否是数字
 *返回值：是数字返回true，否则false
 */
function isNumeric(v) {
    if (v != null && v.length > 0 && isNaN(v) == false) {
        return true;
    }
    return false;
}

/**
 *说明：判断value变量值是否是中文
 *返回值：是中文返回false，否则true
 */
function isChn(v) {
    var reg = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0])*$/;
    if (reg.test(v)) {
        return true;
    }
    return false;
}

/**
 * 获取当前系统日期
 */

function getNewDate() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth() + 1;
    var day = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    if (minute < 10) {
        minute = "0" + minute;
    }
    var second = now.getSeconds();
    var date = year + "-" + month + "-" + day;
    return date;
}


/**
 *  存数组,去掉重复
 * @param v         要存入的值
 * @param arr       要存入的数组
 */
function setArr(v, arr) {
    //判断入参是否为数组,如果是数组就遍历取值
    var _b = v instanceof Array
    if (_b) {
        for (var a = 0; a < v.length; a++) {
            if ($.inArray(v[a], arr) > -1) { } else {
                arr.push(v[a])
            }
        }
    } else {
        if ($.inArray(v, arr) > -1) { } else {
            arr.push(v)
        }
    }
}

/**
 * 判断数组
 * 判断一个数是否存在Arr中,返回boolean
 * @param arr
 * @param v
 */
function _inArray(_v, _arr) {
    if ($.inArray(_v, _arr) >= 0) {
        return true;
    } else {
        return false;
    }
}


/**
 * 精度运算 用于小数的加减乘除运算
 *  Example: floatObj.add(double1,double2)
 * 
 */
var floatObj = function () {

    /*
     * 判断obj是否为一个整数
     */
    function isInteger(obj) {
        return Math.floor(obj) === obj
    }

    /*
     * 将一个浮点数转成整数，返回整数和倍数。如 3.14 >> 314，倍数是 100
     * @param floatNum {number} 小数
     * @return {object}
     *   {times:100, num: 314}
     */
    function toInteger(floatNum) {
        var ret = {
            times: 1,
            num: 0
        };
        if (isInteger(floatNum)) {
            ret.num = floatNum;
            return ret
        }
        var strfi = floatNum + '';
        var dotPos = strfi.indexOf('.');
        var len = strfi.substr(dotPos + 1).length;
        var times = Math.pow(10, len);
        var intNum = parseInt(floatNum * times + 0.5, 10);
        ret.times = times;
        ret.num = intNum;
        return ret
    }

    /*
     * 核心方法，实现加减乘除运算，确保不丢失精度
     * 思路：把小数放大为整数（乘），进行算术运算，再缩小为小数（除）
     *
     * @param a {number} 运算数1
     * @param b {number} 运算数2
     * @param op {string} 运算类型，有加减乘除（add/subtract/multiply/divide）
     *
     */
    function operation(a, b, op) {
        var o1 = toInteger(a);
        var o2 = toInteger(b);
        var n1 = o1.num;
        var n2 = o2.num;
        var t1 = o1.times;
        var t2 = o2.times;
        var max = t1 > t2 ? t1 : t2;
        var result = null;
        switch (op) {
            case 'add':
                if (t1 === t2) { // 两个小数位数相同
                    result = n1 + n2
                } else if (t1 > t2) { // o1 小数位 大于 o2
                    result = n1 + n2 * (t1 / t2)
                } else { // o1 小数位 小于 o2
                    result = n1 * (t2 / t1) + n2
                }
                return result / max;
            case 'subtract':
                if (t1 === t2) {
                    result = n1 - n2
                } else if (t1 > t2) {
                    result = n1 - n2 * (t1 / t2)
                } else {
                    result = n1 * (t2 / t1) - n2
                }
                return result / max;
            case 'multiply':
                result = (n1 * n2) / (t1 * t2);
                return result;
            case 'divide':
                result = (n1 / n2) * (t2 / t1);
                return result
        }
    }

    // 加减乘除的四个接口
    function add(a, b) {
        return operation(a, b, 'add')
    }

    function subtract(a, b) {
        return operation(a, b, 'subtract')
    }

    function multiply(a, b) {
        return operation(a, b, 'multiply')
    }

    function divide(a, b) {
        return operation(a, b, 'divide')
    }

    // exports
    return {
        add: add,
        subtract: subtract,
        multiply: multiply,
        divide: divide
    }
}();

/**
 * 校验浏览器是否是IE内核
 */
function checkBrowser() {
    var userAgent = navigator.userAgent;
    if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Microsoft") > -1 || userAgent.indexOf("Edge") > -1) {
        //是IE内核
        return false;
    } else {
        return true;
    }
}

/**
 * 加载移动端控制台
 */
var countConsole = 0;

function loadMobileConsole() {
    countConsole++;
    //判断点击次数是否达到,如果达到就改sessionStorage.getItem('erdua')为true
    if (countConsole >= 7) {
        var erdua = sessionStorage.getItem('erdua');
        if (erdua == null || erdua == 'null' || erdua == false) {
            sessionStorage.setItem('eruda', 'true')
        } else {
            sessionStorage.setItem('eruda', 'false')
        }
    }
    //如果sessionStorage.getItem('erdua')为true,就加在控制台
    if (sessionStorage.getItem('eruda') == 'fasle' || sessionStorage.getItem('eruda') == null) {
        return;
    } else {
        $.getScript('https://cdn.jsdelivr.net/npm/eruda', function () {
            eruda.init();
        });
        alert('不要再点啦!')
        countConsole = 0;
        //解绑点击事件
        $('#header').unbind();
    }
}


/** 
 *    ***  流程字段操作    ---   流程字段操作  ---  流程字段操作    ---   流程字段操作    ---   流程字段操作    ---   流程字段操作    ***
 */

/**
 * 收起明细
 * 所有流程节点中是默认收起明细的
 * 在申请节点的时候需要自行调用showDetail()来显示明细
 */
function hideDetail() {
    var rows = getDetailTableRows(0);
    for (var i = 0; i < rows.length; i++) {
        $(rows[i]).hide();
    }
}

/**
 * 展开明细按钮
 */
function showDetail() {
    var rows = getDetailTableRows(0);
    for (var i = 0; i < rows.length; i++) {
        $(rows[i]).show();
    }
}

/**
 * 获取明细表中所有行 
 * 支持 PC - 移动端
 * @param {*} _id  明细号从0开始,传入0代表获取dt1
 * 
 * Example :
 *      var rows = getDetailTableRows(0);
        for (var i = 0; i < rows.length; i++) {
            var row = getDtIndex(rows[i]);
            $('#' + m.fid + '_' + row).val();
        }
 * 
 * 
 */
function getDetailTableRows(_id) {
    if (mobile) {
        return $('#oTable' + _id + ' tr[_target=datarow]')
    } else {
        return $('#oTable' + _id + ' tr[_rowindex]');
    }
}

/**
 *  删除明细所有行
 * @param {} dtid  明细号,从0开始
 */
function removeDetail(dtid) {
    if (dtid == undefined) {
        dtid = 0;
    }
    var check = jQuery("input[name=check_node_" + dtid + "]");
    if (check.size() != 0) {
        check.attr("checked", true);
        var isdelOld = isdel;
        isdel = function () {
            return true;
        };
        eval("deleteRow" + dtid + "(" + dtid + ")");
        isdel = isdelOld;
    }
}

/**
 * 获得行index
 *  标记字段ID序号的Index   field1999_Index
 * @param {*} _obj 
 */
function getDtIndex(_obj) {
    return $(_obj).attr('_rowindex');
}
/**
 * 获得行index  根据行内的某一个字段获取
 *  标记字段ID序号的Index   field1999_Index
 * @param {*} _obj 
 */
function getDtIndexByRowField(_obj) {
    var rowNo = $(_obj).parents('tr');
    if (rowNo.length > 0) {
        return $(rowNo[0]).attr('_rowindex');
    }
    return -1
}

//获取明细行号,,,明细中的序号 
function getDtRowNo(_el) {
    return $(_el).find("span[name^='detailIndexSpan']").text();
}

/**
 * 添加一行明细 
 * 目前只支持0-2明细表的添加
 * @param {*} _no 明细号,从0开始
 */
function addDetail(_no) {
    if (_no == 0 || _no == undefined) {
        addRow0(0);
    } else if (_no == 1) {
        addRow1(1);
    } else if (_no == 2) {
        addRow1(2);
    }
}

/**
 * 删除当前浏览按钮中的值
 * @param {*} _addFeild ID  字段ID
 * @author 李翼  2019.1.18
 */
function _browseButClear(_addFeild) {
    if (_addFeild == null || _addFeild == '') {
        if (console && console.log) console.log("清空浏览按钮参数不完整...");
        return false;
    } else {
        var parentSpanId = '#field' + clearStr(_addFeild);
        var valInfo = $(parentSpanId + 'span' + ' span[class=e8_delClass]')
        if (valInfo.length > 0) {
            for (var a = 0; a < valInfo.length; a++) {
                $(valInfo[a]).trigger('click');
            }
        }
        return true;
    }
}

/**
 * 获取字段类型
 * @param {h} _id 
 * @author 李翼  2019.1.18
 */
function _getFieldType(_id) {
    _id = 'f' + clearStr(_id);
    var _newId = _id;
    if (_id.indexOf("_") != -1) {
        _newId = _id.split('_')[0];
    }
    return eval('ft.' + _newId);

    // if (__newId == 1) {
    //     console.log('1：单行文本框')
    // } else if (__newId == 2) {
    //     console.log('2：多行文本框')
    // } else if (__newId == 3) {
    //     console.log('3：浏览按钮')
    // } else if (__newId == 4) {
    //     console.log('4：Check框')
    // } else if (__newId == 5) {
    //     console.log('5：选择框')
    // }
}



/**
 * 获取浏览按钮文本值
 */
function brwoseText(_id) {
    _id = clearStr(_id);
    var v = $('#field' + _id + 'span').children('span').children('a').text();
    return toTm(v);
}