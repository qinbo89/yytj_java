/**
 * Created by shan on 7/22/2015.
 */
$(document).ready(function(){
    var basePath = $("#basePath").val();

    //进入添加备注
    oReplyWin = $("a[id='memo']").click(function(){
        var url = basePath+'/cashout/enterEditMemo.html?id='+$(this).attr("data-id");
        openSelectTopWin("添加备注",url,0.4,0.5);
    });

    //进入汇款失败
    oReplyWin = $("a[id='auditNotPass']").click(function(){
        var url = basePath+'/cashout/enterNotPass.html?id='+$(this).attr("data-id");
        openSelectTopWin("汇款失败原因",url,0.4,0.35);
    });


    //审核不通过
    $("#btnAuditNotPass").click(function(){
        var comment = $("#comment").val();
        if(!comment){
            alert("汇款失败原因不能为空");
            return;
        }
        $.post(basePath+"/cashout/auditNotPass",{"id":$("#id").val(),"comment":comment},function(d){
            if(d.success){
                alert( '执行成功');
                parent.window.location = basePath+"/cashout/list.html"
            }else{
                alert( '执行失败');
            }

        },"json");

    });

    //审核通过
    $("a[id='auditPass']").click(function(){
        var idVal = $(this).attr("data-id");
        $.dialog.confirm('您确定要执行汇款成功吗？', function(){
            $.post(basePath+"/cashout/auditPass",{"id":idVal},function(d){
                if(d.success){
                    $.dialog.tips( '执行成功');
                    window.location = basePath+"/cashout/list.html"
                }else{
                    $.dialog.tips( '执行失败');
                }
            },"json");
        }, function(){
            this.close();
        });
    });

    //添加备注
    $("#btnEditMemo").click(function(){
        var memo = $("#memo").val();
        if(!memo){
            alert("备注不能为空");
            return;
        }
        $.post(basePath+"/cashout/editMemo",{"id":$("#id").val(),"memo":memo},function(d){
            if(d.success){
                alert( '执行成功');
                parent.window.location = basePath+"/cashout/list.html"
            }else{
                alert( '执行失败');
            }

        },"json");

    });

    //导出excel
    $("#btnExportExcel").click(function(){
        $("form:first").attr("action",basePath+"/cashout/exportExcel.html");
        $("form:first").submit();
        $("form:first").attr("action",basePath+"/cashout/list.html");

    });


});
