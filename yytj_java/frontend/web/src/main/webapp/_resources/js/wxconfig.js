function randomString(len) {
    len = len || 32;
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
    var maxPos = chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

var nonceStr = randomString(20)
var t = (new Date().getTime());
var pageUrl= $('#currentRequestURI').val();
var appId = $('#appId').val();


var opts={
    type:"get",
    dataType:"json",
    success:function(data){
        signature = data['data']['Signature'];
        wx.config({
            //debug: true,
            appId: appId,
            timestamp: t,
            nonceStr: nonceStr,
            signature:signature,
            jsApiList: [
                'onMenuShareTimeline',
                'onMenuShareAppMessage'
               
            ]
        });

    },
    url:"/wx/getWebSignature",
    data:{url:pageUrl,timestamp:t,noncestr:nonceStr}
}

//get signature and config wx
$.ajax(opts);
	     
	     