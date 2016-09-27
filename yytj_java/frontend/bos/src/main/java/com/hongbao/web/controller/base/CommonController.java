package com.hongbao.web.controller.base;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hongbao.dal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.base.annotation.NotNeedLogin;
import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.RequestUtilService;
import com.hongbao.service.UserService;
import com.hongbao.utils.DateUtil;
import com.hongbao.utils.ImageCheckCodeUtil;

@Controller
public class CommonController extends H5BaseController {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private JedisUtil jedisUtil;
    
    @Autowired
    private RequestUtilService requestUtilService;
    
    
    @RequestMapping("/generalError.html")
    @NotNeedLogin
    public String generalError(Throwable e, HttpServletRequest request) {
        return "error";
    }


    @RequestMapping("/")
    @NotNeedLogin
    public String index()  {
        User user = userService.getCurrentUser();
        if(user == null){
            return "redirect:login.html";
        }else{
            return "redirect:tryapp/list.html";

        }

    }


    @RequestMapping("/imageCheckCode.html")
    @NotNeedLogin
    @ResponseBody
    public String imageCheckCode(HttpServletResponse response) throws IOException {
        ImageCheckCodeUtil imgg = new ImageCheckCodeUtil();
        BufferedImage img  = imgg.genImage();
        jedisUtil.setObject(requestUtilService.getCookie(UserService.ACCESS_CONTROL_ID) +"_imageCheckCode", DateUtil.HOUR_SECONDS,imgg.sRand);
        log.warn("put " +requestUtilService.getCookie(UserService.ACCESS_CONTROL_ID) +"_imageCheckCode value=" +imgg.sRand  );
        ImageIO.write(img, "JPEG", response.getOutputStream());
        return null;
    }
}
