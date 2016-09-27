package com.hongbao.web.weixin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hongbao.dal.model.AppDownload;
import com.hongbao.service.appdownload.AppDownloadService;




@Controller
public class AppDownLoadController {
	@Autowired
	private AppDownloadService appDownloadService;
	
	///weixin/appDownLoadPage
	@RequestMapping("/innerPage/appDownLoadPage")
	public String appDownLoadPage(Model model){
		AppDownload appDownload = appDownloadService.getAppDownload();
		model.addAttribute("appDownload", (appDownload==null)?"":appDownload);
		return "weixin/appDownload";
	}

	
	@RequestMapping("/innerPage/aboutUs")
	public String aboutUs(Model model){
		return "weixin/aboutUs";
	}

    @RequestMapping("/")
    public String home(Model model) {
        AppDownload appDownload = appDownloadService.getAppDownload();
        model.addAttribute("appDownload", (appDownload==null)?"":appDownload);
        return "dgfp/index";
    }
}
