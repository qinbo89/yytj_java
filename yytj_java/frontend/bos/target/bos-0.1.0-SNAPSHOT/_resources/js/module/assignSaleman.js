/**
 * Created by shan on 8/6/2015.
 */

var saleUserId;
$(document).ready(function(){
    var basePath = $("#basePath").val();

    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };

    //初始化加载数据
    $.post(basePath+"/shop/getProxyTreeList",{refreshTree:0},function(d){
        var zNodes = d.data;
        treeObj = $.fn.zTree.init($("#treeDiv"), setting, zNodes);
    },"json");

    function onClick(event, treeId, treeNode, clickFlag) {
        //console.log(treeNode.level);
        if(treeNode.level == "4"){  //level 被框架覆盖了
            saleUserId = treeNode.id.substring(1);
        }else{
            saleUserId = null;
        }
    }




    //搜索
    $("#btnSearch").click(function(){
        var keyword = $("#keyword").val();
        var tel = $("#tel").val();
        var isOpen  = false;
        if(keyword || tel){
            isOpen = true;
        }
        $.post(basePath+"/shop/getProxyTreeList",{refreshTree:0,keyword:keyword,tel:tel},function(d){
            var zNodes = d.data;
            for(var i = 0; i < zNodes.length; i++){
                var node = zNodes[i];
                node.open = isOpen;
            }
            treeObj = $.fn.zTree.init($("#treeDiv"), setting, zNodes);
        },"json");


    });

    //确认指派
    $("#btnConfirmAssgin").click(function(){
        if(!saleUserId){
            alert("请先选择业务员！");
            return;
        }
        if(!confirm("您确认要指派给该业务员吗？")){
            return;
        }
        var id = $("#id").val();
        $.post(basePath+"/shop/assign",{"id":id,saleUserId:saleUserId},function(d){
            if(d.success){
                alert( '指派成功');
                parent.location = basePath+"/shop/list.html"
            }else{
                alert( '指派失败');
            }
        },"json");
    });

});