$(document).ready(function(){

    $("#btnSearch").click(function(){
        $("#listForm").submit();
    });

    $("#btnClear").click(function(){
        $("form:first").find(":text").each(function(index,item){
            $(item).val("");
        });
        $("form:first").find(":hidden").each(function(index,item){
            $(item).val("");
        });
        $("form:first").find("select").each(function(index,item){
            $(item).find("option:first").prop("selected",true);
        });
    });

    //ener触发
    $(document).keypress(function(e){
        if(e.which == 13){
            $("input[enter='true']").trigger("click");
        }
    });

    basePath = $("#basePath").val();

});
/**
 * Created by shan on 9/1/2015.
 */
function openSelectTopWin(title,url,scale_width,scale_height){
    var width = document.body.clientWidth * scale_width;
    var height = document.body.clientHeight * scale_height;
    openWinObj = $.dialog({
        id:"hframeId",
        title: title,
        lock: true,
        width: width,
        height: height,
        max: false,
        min: false,
        content: 'url:'+url
    });
}


//获得提交表单数据
function getFormParams(id){
    var sel = id || "form";
    var paramsArray = $(sel).serializeArray();
    var params = {};
    while(paramsArray.length > 0){
        var data = paramsArray.pop();
        var oldValue = params[data.name];
        if(typeof(oldValue) != "undefined"){  //存在数据
            if(typeof(oldValue) == "object"){
                oldValue.push(data.value); //add
            }else{
                var arr = [];
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



