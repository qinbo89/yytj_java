/**
 * Created by shan on 7/22/2015.
 */
$(document).ready(function(){
    var basePath = $("#basePath").val();

    //进入回复
    oReplyWin = $("a[id='reply']").click(function(){
        var url = basePath+'/suggest/enterReply.html?id='+$(this).attr("data-id");
        openSelectTopWin("意见反馈回复",url,0.4,0.5);
    });

    //解决
    $("a[id='solve']").click(function(){
        var idVal = $(this).attr("data-id");
        $.dialog.confirm('您确定要执行解决吗？', function(){
            $.post(basePath+"/suggest/solve",{"id":idVal},function(d){
                if(d.success){
                    $.dialog.tips( '执行成功');
                    window.location = basePath+"/suggest/list.html"
                }else{
                    $.dialog.tips( '执行失败');
                }
            },"json");
        }, function(){
            this.close();
        });
    });

    //回复
    $("#btnReply").click(function(){
        $.post(basePath+"/suggest/reply",{"id":$("#id").val(),"content":$("#content").val()},function(d){
            if(d.success){
                alert( '回复成功');
                parent.window.location = basePath+"/suggest/list.html"
            }else{
                alert( '回复失败');
            }

        },"json");

    });


});

function topLoadTips(msg){
    var content = msg || "数据处理中...";
    loadTipsObj = $.dialog({
        id:"loadTipsId",
        title:"提示",
        lock:true,
        drag: false,
        max: false,
        min: false,
        content: content
    });
}