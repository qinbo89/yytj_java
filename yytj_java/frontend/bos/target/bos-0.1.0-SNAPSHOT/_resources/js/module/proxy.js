/**
 * Created by shan on 7/22/2015.
 */
$(document).ready(function(){
    var basePath = $("#basePath").val();


    $("#btnUpdate").click(function(){
        //校验
        if(!$("#userName").val()){
            alert("代理商名称不能为空");
            return;
        }
        var params = getFormParams();
        $.post(basePath+"/proxy/update",params,function(d){
            if(d.success){
                alert( '执行成功');
                window.location = basePath+"/proxy/list.html"
            }else{
                alert("执行失败 原因："+ d.message);
            }
        });
    });

    $("a[id='delProxy']").click(function(){
        if(!confirm("您确认要删除该代理商吗？")){
            return;
        }
        var userId = $(this).attr("data-id");
        $.post(basePath+"/proxy/remove",{userId:userId},function(d){
            if(d.success){
                $.dialog.tips( '执行成功');
                window.location = basePath+"/proxy/list.html"
            }else{
                $.dialog.tips( '执行失败');
            }
        },"json");
    });








});

