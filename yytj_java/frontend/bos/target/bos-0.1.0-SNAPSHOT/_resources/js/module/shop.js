/**
 * Created by shan on 7/22/2015.
 */
$(document).ready(function(){
    var basePath = $("#basePath").val();

    //审核不通过
    $("#btnNotPass").click(function(){
        var idVal = $("#id").val();
        $.dialog.confirm('您确定要执行不通过吗？', function(){
            $.post(basePath+"/shop/auditNotPass",{"id":idVal},function(d){
                if(d.success){
                    $.dialog.tips( '执行成功');
                    window.location = basePath+"/shop/list.html"
                }else{
                    $.dialog.tips( '执行失败');
                }
            },"json");
        }, function(){
            this.close();
        });
    });

    //审核通过
    $("#btnPass").click(function(){
        var idVal = $("#id").val();
        $.dialog.confirm('您确定要执行通过吗？', function(){
            $.post(basePath+"/shop/auditPass",{"id":idVal,licenseNo:$("#licenseNo").val()},function(d){
                if(d.success){
                    $.dialog.tips( '执行成功');
                    window.location = basePath+"/shop/list.html"
                }else{
                    $.dialog.tips( '执行失败');
                }
            },"json");
        }, function(){
            this.close();
        });
    });
    //检测
    $("#btnCheck").click(function(){
        var idVal = $("#id").val();
        var licenseNo = $("#licenseNo").val();
        if(!licenseNo){
            $.dialog.alert("营业执照编号不能为空");
            return;
        }
        $.post(basePath+"/shop/checkLicenseNo",{"id":idVal,licenseNo:licenseNo},function(d){
            if(d.success){
                if(d.data){
                    $.dialog.tips( '检测通过');
                }else{
                    $.dialog.tips( '检测不通过，有重复记录');
                }
            }else{
                $.dialog.tips( '执行失败');
            }
        },"json");
    });


    //指派
    //进入指派
    $("#btnAssign").click(function(){
        var url = basePath+'/shop/enterAssign.html?id='+$("#id").val();
        openSelectTopWin("业务员指派",url,0.50,0.85);
    });

    //恢复商户
    $("a[id='restore']").click(function(){
        var idVal = $(this).attr("data-id");
        $.dialog.confirm('您确定要恢复吗？', function(){
            $.post(basePath+"/shop/restore",{"id":idVal},function(d){
                if(d.success){
                    $.dialog.tips( '执行成功');
                    window.location = basePath+"/shop/list.html"
                }else{
                    $.dialog.tips( '执行失败');
                }
            },"json");
        }, function(){
            this.close();
        });
    });

    //客服修改商户
    //进入修改
    $("a[id='enterUpdate']").click(function(){
        var url = basePath+'/shop/enterUpdate.html?id='+$(this).attr("data-id");
        openSelectTopWin("商户折扣修改",url,0.55,0.4);
    });
    //进入修改地址
    $("a[id='enterAddrUpdate']").click(function(){
        var url = basePath+'/shop/enterUpdateAddr.html?id='+$(this).attr("data-id");
        openSelectTopWin("商户地址修改",url,0.65,0.5);
    });

    //修改
    $("#btnUpdateZk").click(function(){
        var params = $("form:first").serialize();
        $.post(basePath+"/shop/update",params,function(d){
            if(d.success){
                alert("修改成功");
                $("form:first",parent.document).submit();
                //parent.window.location = basePath+"/shop/updateShopList.html"
            }else{
                alert("修改失败,原因："+ d.message);
            }
        },"json");
    });
    //修改
    $("#btnUpdateAddr").click(function(){
        var params = $("form:first").serialize();
        $.post(basePath+"/shop/updateAddr",params,function(d){
            if(d.success){
                alert("修改成功");
                $("form:first",parent.document).submit();
            }else{
                alert("修改失败,原因："+ d.message);
            }
        },"json");
    });


    //弹出区域查询
    $("#btnQueryZone").click(function(){
        var url = basePath+'/shop/enterSelZone.html';
        openSelectTopWin("区县区域查询",url,0.35,0.85);
    });

    //弹出代理商查询
    $("#btnQueryProxy").click(function(){
        var url = basePath+'/shop/enterSelProxy.html';
        openSelectTopWin("代理商查询",url,0.35,0.85);
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
