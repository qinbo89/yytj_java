/**
 * Created by shan on 7/22/2015.
 */
$(document).ready(function(){
    var basePath = $("#basePath").val();

    $("a[id='enterEdit']").click(function(){
        var url = basePath+'/hongbao/enterEdit.html?id='+$(this).attr("data-id");
        openSelectTopWin("红包修改",url,0.75,0.6);
    });

    $("a[id='offShelves']").click(function(){
        if(!confirm("您确认要下架？")){
            return;
        }
        var url = basePath+'/hongbao/offShelves?id='+$(this).attr("data-id");
        $.post(url,{},function(d){
            if(d.success){
                alert("下架成功");
            }else{
                alert("下架失败");
            }
        });
    });

    //审核修改
    $("#btnUpdate").click(function(){
        var params = getFormParams();
        $.ajaxFileUpload
        (
            {
                url: basePath+"/hongbao/update",
                secureuri: false,
                fileElementId: 'imgFile',
                dataType: 'json',
                data: params,
                success: function (data, status) {
                    alert("修改成功");
                    $("form:first",parent.window.document).submit();
                    parent.openWinObj.close();
                },
                error: function (data, status, e) {
                    if(e.toString().indexOf("token") != -1){  //TODO
                        alert("修改成功")
                        $("form:first",parent.window.document).submit();
                        //$(parent.document).find("#btnSearch").trigger("click");
                        parent.openWinObj.close();

                    }else{
                        alert("修改失败,原因："+ data.message+e);
                    }
                }
            }
        )
    });

    //弹出代商户查询
    $("#btnQueryShop").click(function(){
        var url = basePath+'/hongbao/enterSelShop.html';
        openSelectTopWin("商户查询",url,0.40,0.85);
    });

    $("#btnClear").click(function(){
        $("#listForm").find(":text").each(function(index,item){
            $(item).val("");
        });
        $("#listForm").find(":hidden").each(function(index,item){
            $(item).val("");
        });
        $("#listForm").find("select").each(function(index,item){
            $(item).find("option:first").prop("selected",true);
        });
    });




});

