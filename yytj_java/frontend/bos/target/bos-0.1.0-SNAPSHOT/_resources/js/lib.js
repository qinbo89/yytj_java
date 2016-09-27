  var $loading = $(['<div id="loading" class="loading" style="background: black;opacity: 0.4;z-index: 29891015;"><div class="inner">',
            '<div class="panel">',
                '<div class="tick-tock">',
                    '<span class="t1"></span><span class="t2"></span>',
                    '<span class="t3"></span><span class="t4"></span>',
                    '<span class="t5"></span><span class="t6"></span>',
                    '<span class="t7"></span><span class="t8"></span>',
                    '<span class="t9"></span><span class="t10"></span>',
                    '<span class="t11"></span><span class="t12"></span>',
                '</div>',
                '<div class="txt">正在加载, 请稍候</div>',
            '</div>',
        '</div></div>'].join(''));

window.YTLib = {
		 
		 
        isLogin: function () {
            var nf_token = this.getToken();
            return (nf_token !== null) ? true : false;
        },

        isEmptyObject: function(obj) {
            var name;
            for ( name in obj ) {
                return false;
            }
            return true;
        },

        isPhoneNumber: function(data) {
            var phoneRegExp = /^1[34578][0-9][0-9]{8}$/; // 三大运营商号段
            return phoneRegExp.test(data);
        },

        getQueryMap: function (query) {
            var queryArr = query.split('&');

            var queryMap = {};
            $.each(queryArr, function (i, item) {
                var arr = item.split('=');
                queryMap[arr[0]] = arr[1];
            });
            return queryMap;
        },

        confirm: function (options) {
            var body = $('body');
            var confirmTmpl = ['<div class="dialog-wrap confirm-wrap">',
                    '<div class="dialog-panel" align="center">',
                        '<div class="dialog-confirm" style="max-width: 740px;">',                            
                            '<div class="msg">message</div>',
                            '<div class="btns">',
                                '<button class="btn btn-cancel">取消</button>',
                                '<button class="btn btn-confirm">确认</button>',
                            '</div>',
                        '</div>',
                    '</div>',
                '</div>'].join('');

            if ( !body.hasClass('confirm-open') ) {

                var $confirm = $(confirmTmpl),
                    // $title = $confirm.find('.title'),
                    $msg = $confirm.find('.msg'),
                    $btnCancel = $confirm.find('.btn-cancel'),
                    $btnConfirm = $confirm.find('.btn-confirm');

                if (options.title !== undefined && options.title.length > 0) {
                    // $title.text(options.title).show();
                    $('<div class="title">'+ options.title +'</div>').insertBefore($msg);
                }

                if (options.message !== undefined) {
                    $msg.html(options.message);
                }

                $btnConfirm[0].onclick = function () {
                    $confirm.fadeOut(200, function () {
                        body.removeClass('confirm-open');

                        // 执行回调
                        if (options.onConfirm !== undefined && typeof options.onConfirm === 'function') {                            
                            options.onConfirm($confirm);
                        }
                    });
                };

                // 
                if (options.cancel === false) {
                    $btnCancel.hide();
                } else {
                    $btnCancel[0].onclick = function () {
                        body.removeClass('confirm-open');
                        $confirm.fadeOut(200);

                        if (options.onCancel !== undefined && typeof options.onCancel === 'function') {
                            options.onCancel();
                        }
                    };
                }

                if (body.find('.dialog-confirm').length > 0) {
                    body.find('.dialog-confirm').parents('.dialog-wrap').remove();
                }

                $confirm.hide().appendTo(body).fadeIn(200);
                body.addClass('confirm-open');
            }
        },

        popMessage: function (msg, duration, bgColor) {
        	
        	layer.alert(msg);
        	return;
        	
            var body = $('body');
            var $dialog = $('<div class="dialog-wrap"><div class="dialog-panel"  align="center"></div></div>'),
                $dialogPanel = $dialog.find('.dialog-panel');

            if (!msg) {
                return;
            }

            if ( !body.hasClass('pop-open') ) {
                // 
                var $popMsg = $('<div class="pop-msg"  style="max-width: 740px;" />');

                //弹层显示时间, ms
                if (!!duration) {
                    duration = typeof duration === 'number' ? duration : parseInt(duration);
                } else {
                    duration = 2000;
                }

                !!bgColor && $popMsg.css('background-color', bgColor);

                $popMsg.html(msg).appendTo($dialogPanel);

                $dialog.hide().appendTo(body);
                $dialog.fadeIn(200, function () {
                    body.addClass('pop-open');
                });

                setTimeout(function () {
                    $dialog.fadeOut(200, function () {
                        $dialog.remove();
                        body.removeClass('pop-open');
                    });
                }, duration);
            }
        },

        validation: function ($form) {
            var self = this;
            var validResult = true,
                validMsg = '';

            var $elems = $form.find('[required="required"]');

            if ($elems.length < 1) {
                return {
                    result: validResult,
                    message: validMsg
                };
            }

            $elems.each(function (i, elem) {
                // 禁用元素不校验
                if (elem.disabled) {
                    return;
                }

                var $elem = $(elem);
                var elemVal = $.trim( $elem.val() ),
                    minlength = $elem.attr('min-length'),
                    equalTo = $elem.attr('equal'),
                    dataType = $elem.attr('data-type');

                // 空值校验
                if ( elemVal === '' ) {
                    var requiredMsg = $elem.attr('required-msg') || '内容不能为空';
                    validResult = false;
                    validMsg = requiredMsg;

                    return false;
                }

                // 类型校验
                if (!!dataType) {
                    switch (dataType) {
                        case 'phone':
                            // 非手机号
                            if ( !NFLib.isPhoneNumber(elemVal) ) {
                                validResult = false;
                                validMsg = '请输入正确的手机号码';

                                return false;
                            }
                            break;
                        case 'password':
                            // 兼容第三方输入法不识别password输入框问题
                            // 非半角字符（含汉字）不合法
                            if ( /[^\x00-\xff]/g.test(elemVal) ) {
                                validResult = false;
                                validMsg = '密码只能由字母、数字、英文符号组成';

                                return false;
                            }
                            break;
                    }
                }

                // 最小长度
                if (!!minlength) {
                    minlength = parseInt(minlength);
                    if ( elemVal.length < minlength ) {
                        validResult = false;
                        validMsg = $elem.attr('minlength-msg') || '不能少于'+ minlength +'个字符';
                        return false;
                    }
                }

                // 等同校验
                if (!!equalTo) {
                    var equalMeta = equalTo.split(',');
                    var equalVal = $.trim( $('#' + equalMeta[0]).val() );

                    if (elemVal !== equalVal) {
                        validResult = false;
                        validMsg = equalMeta[1] + '两次输入不相同，请重新输入';

                        return false;
                    }
                }

            });

            return {
                result: validResult,
                message: validMsg
            };
        },
       ajaxMultiForm : function(parma, callback, $trigger) {
    	   var self = this;

    	   parma = parma || {};
    	   
           var $trigger = $trigger || $('');
           var showLoading = parma.loading;

           // 发起请求后等待，防止重复请求
           if ( $trigger.hasClass('waiting') ) {
               return;
           }
           $trigger.addClass('waiting');

           // 显示loading图标
           if (showLoading ) {
               $loading.appendTo($('body'));

               // 100ms内响应不显示loading, 减少闪现
               $loading.appendTo($('body')).hide();
               setTimeout(function () {
                   $loading.show();
               }, 100);
           }

           console.log(parma.data)
           
            var data = new FormData();
            for ( var key in parma.data) {
                data.append(key, parma.data[key]);
            }
           $.ajax(jQuery.extend(parma, {
                url : parma.url,
                type : 'POST',
                contentType : false,
                processData : false,
                data:data,
                success: function (res,textStatus, jqXHR) {
                    $trigger.removeClass('waiting');
                    $loading.remove();

                    // console.log('请求ok：', textStatus, jqXHR)

                    if (res.success) {
                        !!callback && callback.call(this, res);
                    } else {
                        // 提示自动隐藏后
                        setTimeout(function () {
                        	 self.popMessage(res.message);
                        	 
                        }, 1500);                        
                    }
                },
                error: function (res, status) {
                    $trigger.removeClass('waiting');
                    $loading.remove();

                    console.log('请求失败：', res)

                    
                    // 请求超时
                    if (status && status === 'timeout') {
                        self.popMessage('服务器正忙，请稍后再试！');
                    }
                  
                    // 请求异常处理
                    if (res.status >= 500) {
                        // 服务异常
                        self.popMessage('服务器开小差了，请稍后再试！');

                    } else if (res.status >= 400) {
                        // 请求异常
                        self.popMessage('请求失败，请稍后再试！');

                    } /*else {
                        // 请求异常
                        self.popMessage('请求失败！');
                    }*/
                    return;
                }
            }));
        },
        doAjaxRequest: function (parma, callback, $trigger) {
            var self = this;

            parma = parma || {};
            
            var $trigger = $trigger || $('');
            var showLoading = parma.loading;

            // 发起请求后等待，防止重复请求
            if ( $trigger.hasClass('waiting') ) {
                return;
            }
            $trigger.addClass('waiting');

            // 显示loading图标
            if (showLoading ) {
                // $loading.appendTo($('body'));

                // 100ms内响应不显示loading, 减少闪现
                $loading.appendTo($('body')).hide();
                setTimeout(function () {
                    $loading.show();
                }, 100);
            }

            console.log(parma.data)
            $.ajax({
                url: parma.url,
                type: 'post',
           //     dataType: 'json',
                data: parma.data,
               // contentType: 'application/json',
              //  timeout: 6000,
                success: function (res,textStatus, jqXHR) {
                    $trigger.removeClass('waiting');
                    $loading.remove();

                    // console.log('请求ok：', textStatus, jqXHR)

                    if (res.success) {
                        !!callback && callback.call(this, res);
                    } else {
                        // 提示自动隐藏后
                        setTimeout(function () {
                        	 self.popMessage(res.message);
                        	 
                        }, 1500);                        
                    }
                },
                error: function (res, status) {
                    $trigger.removeClass('waiting');
                    $loading.remove();

                    console.log('请求失败：', res)

                    
                    // 请求超时
                    if (status && status === 'timeout') {
                        self.popMessage('服务器正忙，请稍后再试！');
                    }
                  
                    // 请求异常处理
                    if (res.status >= 500) {
                        // 服务异常
                        self.popMessage('服务器开小差了，请稍后再试！');

                    } else if (res.status >= 400) {
                        // 请求异常
                        self.popMessage('请求失败，请稍后再试！');

                    } /*else {
                        // 请求异常
                        self.popMessage('请求失败！');
                    }*/
                    return;
                }
            });
        },

        goAlipayRequest: function (payInfo) {
            var $form = $("<form/>")
                .attr({
                    action : 'http://115.236.11.98:9191/zfb_fw/pay/alipay_api.html',
                    method : "post"
                }).appendTo($("body"));

            var formHTML = '';
                formHTML += '<input type="hidden" name="out_trade_no" value="'+ payInfo.orderId +'" />';
                formHTML += '<input type="hidden" name="subject" value="'+ payInfo.productName +'" />';
                formHTML += '<input type="hidden" name="total_fee" value="'+ payInfo.payPrice +'" />';
                formHTML += '<input type="hidden" name="pay_type" value="'+ payInfo.payMethod +'" />';

            $form.html(formHTML);

            setTimeout(function () {
                $form.submit();
            }, 0);
        },

        setLocalData: function (DataName, value) {
        	if(!value){
        		value = "";
        	}
        	console.log("setLocalData:"+DataName+"="+value);
            if (!window.localStorage) {
                // 不支持localStorage，设置cookies，保存时间30天
                var exdate = new Date();
                exdate.setDate(exdate.getDate() + 30);
                document.cookie = DataName + '=' + value + ';expires=' + exdate.toGMTString();
            } else {
                window.localStorage.setItem(DataName, value);
            }
        },
        getLocalData: function (DataName) {
            if (!window.localStorage) {
                // 读取cookie
                if (document.cookie.length < 1) {
                	console.log("getLocalData:"+DataName+"="+"");
                    return "";
                }

                var indexStart = document.cookie.indexOf(DataName + '=');
                if (indexStart > 0) {
                    indexStart = indexStart + DataName.length + 1;
                }

                var indexEnd = document.cookie.indexOf(';', indexStart);
                if (indexEnd < 0) {
                    indexEnd = document.cookie.length
                }

                var data = document.cookie.substring(indexStart, indexEnd);
                if (data && data.length > 0) {
                    return data;
                    console.log("getLocalData:"+DataName+"="+data);
                } else {
                	 console.log("getLocalData:"+DataName+"=");
                    return "";
                }

            } else {
                var data = window.localStorage.getItem(DataName);
                if (data && data.length > 0) {
                	console.log("getLocalData:"+DataName+"="+data);
                    return data;
                } else {
                	console.log("getLocalData:"+DataName+"=");
                    return "";
                }
            }
        },
        getLocalJSONData: function (DataName) {
        	return JSON.parse(getLocalData());
        },

        clearLocalData: function (DataName) {
            if (!window.localStorage) {
                var exdate = new Date();
                exdate.setDate(exdate.getDate() - 1);
                document.cookie = DataName + '=;expires=' + exdate.toGMTString();
            } else {
                window.localStorage.removeItem(DataName);
            }
        },
        clearAllLocalData: function () {
        	  for(var i=0;i<window.localStorage.length;i++){
        		  	var name = window.localStorage.key(i);
        		  	window.localStorage.removeItem(name);
             }
        },
        getToken: function () {
            var userinfo = this.getLocalJSONData(nf_token_flag);
            return userinfo ? userinfo.token : null;
        },

        switchTrigger: function ($switch, handler) {
            $switch.on('click', function () {
                $(this).toggleClass('on');

                setTimeout(function () {
                    handler.call(this, $switch.hasClass('on'));
                }, 0);
            });
        }
    };
