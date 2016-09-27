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
