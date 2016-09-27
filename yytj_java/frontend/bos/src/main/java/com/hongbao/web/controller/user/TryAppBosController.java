package com.hongbao.web.controller.user;

import java.util.Map;

import com.hongbao.dal.base.controller.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hongbao.dal.base.annotation.NeedLogin;
import com.hongbao.dal.model.TryApp;
import com.hongbao.dal.page.PageInfo;
import com.hongbao.dal.query.TryAppQuery;
import com.hongbao.service.tryapp.TryAppService;
import com.hongbao.utils.BeanUtils;
import com.hongbao.web.controller.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("tryapp")
public class TryAppBosController extends BaseController {

	@Autowired
	private TryAppService tryAppService;

	@RequestMapping("list.html")
	@NeedLogin
	public String list(TryAppQuery query, Model model) {
		Map<String, Object> queryMap = BeanUtils.beanToMap(query);
		PageInfo<TryApp> pageVO = tryAppService.getTryAppList(
				query.getPageNum(), query.getPageSize(), queryMap);
		model.addAttribute("dataList", pageVO.getRecords());
		model.addAttribute("query", query);
		model.addAttribute("pageInfo", pageVO);
		return "tryapp/list";
	}

	@RequestMapping("newTry.html")
	@NeedLogin
	public String newTry(Model model) {

		return "tryapp/newTry";
	}

	@RequestMapping("save")
	@ResponseBody
	@NeedLogin
	public ResponseObject save(TryApp tryApp) {
		ResponseObject ret = new ResponseObject();
		tryApp.setPackageName(tryApp.getSchema());
		tryApp.setIsAdmin("1");
		tryApp.setArchive(false);
		tryApp.setStatus("0");
		tryApp.setTaskType("1");
		tryAppService.insert(tryApp);
		return ret;
	}

}
