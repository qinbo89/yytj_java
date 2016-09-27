package com.hongbao.restapi.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.controller.ResponseObject;

@Controller
public class CommonController extends BaseController {

  
    
    @RequestMapping("/generalError")
    @ResponseBody
    public ResponseObject generalError(Throwable e) {
    	e.printStackTrace();
        ResponseObject response = new ResponseObject();
        response.setSuccess(false);
        response.setMessage("请求地址错误");
        return response;
    }
}
