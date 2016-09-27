/**
 * Created by shan on 8/6/2015.
 */

$(document).ready(function(){
    var basePath = $("#basePath").val();

    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onDblClick: onClick
        }
    };

    //初始化加载数据
    $.post(basePath+"/shop/getProxyTreeList",{refreshTree:0,queryType:"Z"},function(d){
        var zNodes = d.data;
        treeObj = $.fn.zTree.init($("#treeDiv"), setting, zNodes);
    },"json");

    function onClick(event, treeId, treeNode, clickFlag) {
        console.log(treeNode.level);
        if(treeNode.level == "2"){  //level 被框架覆盖了
            $("#zoneId",parent.document).val(treeNode.id.substring(1));
            $("#zoneText",parent.document).val(treeNode.name);
            parent.openWinObj.close();
        }
    }




    //搜索
    $("#btnSearch").click(function(){
        var keyword = $("#keyword").val();
        var isOpen  = false;
        if(keyword || tel){
            isOpen = true;
        }
        $.post(basePath+"/shop/getProxyTreeList",{refreshTree:0,queryType:"Z",keyword:keyword},function(d){
            var zNodes = d.data;
            for(var i = 0; i < zNodes.length; i++){
                var node = zNodes[i];
                node.open = isOpen;
            }
            treeObj = $.fn.zTree.init($("#treeDiv"), setting, zNodes);
        },"json");


    });


});