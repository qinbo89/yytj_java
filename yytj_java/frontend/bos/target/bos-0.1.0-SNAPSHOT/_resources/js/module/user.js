/**
 * Created by shan on 7/22/2015.
 */
$(document).ready(function(){
    var basePath = $("#basePath").val();

    $("#btnEnterNew").click(function(){
        window.location = basePath+"/user/enterNew.html";
    });

    $("#btnSave").click(function(){
        //校验
        if(!$("#mobile").val()){
            alert("账号不能为空");
            return;
        }
        if(!$("#type").val()){
            alert("用户类型不能为空");
            return;
        }
        if(!$("#password").val()){
            alert("密码不能为空");
            return;
        }
        if($("#password").val() != $("#password2").val()){
            alert("前后密码输入不一致");
            return;
        }
        var params = getFormParams();
        $.post(basePath+"/user/save",params,function(d){
            if(d.success){
                alert( '执行成功');
                window.location = basePath+"/user/list.html"
            }else{
                alert("执行失败 原因："+ d.message);
            }
        });
    });

    $("#btnUpdate").click(function(){
        //校验
        if(!$("#mobile").val()){
            alert("账号不能为空");
            return;
        }
        if(!$("#type").val()){
            alert("用户类型不能为空");
            return;
        }
        var params = getFormParams();
        $.post(basePath+"/user/update",params,function(d){
            if(d.success){
                alert( '执行成功');
                window.location = basePath+"/user/list.html"
            }else{
                alert("执行失败 原因："+ d.message);
            }
        });
    });






});


//获得提交表单数据
function getFormParams(id){
    var sel = id || "form";
    var paramsArray = $(sel).serializeArray();
    var params = {};
    while(paramsArray.length > 0){
        var data = paramsArray.pop();
        var oldValue = params[data.name]
        if(typeof(oldValue) != "undefined"){  //存在数据
            if(typeof(oldValue) == "object"){
                oldValue.push(data.value); //add
            }else{
                var arr = new Array();
                arr.push(oldValue);
                arr.push(data.value);
                oldValue = arr;
            }
            params[data.name] = oldValue;
        }else{
            params[data.name] = data.value;
        }

    }
    return params;
}