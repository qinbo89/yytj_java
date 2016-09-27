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
    $.post(basePath+"/shop/getProxyTreeList",{refreshTree:0,queryType:"B"},function(d){
        var zNodes = d.data;
        treeObj = $.fn.zTree.init($("#treeDiv"), setting, zNodes);
        $("#btnSearch").attr("disabled",false);
    },"json");

    function onClick(event, treeId, treeNode, clickFlag) {
        //console.log(treeNode.level);
        if(treeNode.level == "4"){  //level 被框架覆盖了
            $("#shopId",parent.document).val(treeNode.id.substring(1));
            $("#shopName",parent.document).val(treeNode.name);
            parent.openWinObj.close();
        }
    }




    //搜索
    $("#btnSearch").click(function(){
        searchData(false);
    });

    //加载全部
    $("#full").click(function(){
        if($(this).attr("checked")){ //选择了
            searchData(true);
        }
    });

    //load seach data
    function searchData(full){
        $("#btnSearch").attr("disabled",true);
        $("#treeDiv").html("<strong style=\"color:red\">数据加载中，请稍后...</strong>");
        var keyword = $("#keyword").val();
        var tel = $("#tel").val();
        var isOpen  = false;
        if(keyword || tel){
            isOpen = true;
        }
        $.post(basePath+"/shop/getProxyTreeList",{refreshTree:0,queryType:"B",keyword:keyword,full:full},function(d){
            var zNodes = d.data;
            for(var i = 0; i < zNodes.length; i++){
                var node = zNodes[i];
                node.open = isOpen;
            }
            treeObj = $.fn.zTree.init($("#treeDiv"), setting, zNodes);
            $("#btnSearch").attr("disabled",false);
        },"json");
    }


});