wx.ready(function () {
    wx.onMenuShareTimeline(obj);
	wx.onMenuShareAppMessage(obj);
});

wx.error(function (res) {
  alert(res.errMsg);
});

