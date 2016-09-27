package com.hongbao.web.controller.yydb;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongbao.dal.GlobalResult;
import com.hongbao.dal.base.controller.ResponseObject;
import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.model.Duobao;
import com.hongbao.dal.model.ScoreType;
import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserDuobao;
import com.hongbao.dal.model.UserScore;
import com.hongbao.dal.model.UserScoreTopTen;
import com.hongbao.dal.mybatis.IdTypeHandler;
import com.hongbao.service.DuobaoService;
import com.hongbao.service.UserDuobaoService;
import com.hongbao.service.UserService;
import com.hongbao.service.score.UserScoreService;
import com.hongbao.web.controller.score.UserScoreVO;

@Controller
public class DuobaoController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserScoreService userScoreService;
    @Autowired
    private DuobaoService duobaoService;
    @Autowired
    private UserDuobaoService userDuobaoService;
    @Autowired
    private ApplicationConfig applicationConfig;
    @RequestMapping("/weixin/duobaoList")
    public String duobaoList(Model model) {
        List<Duobao> duobaoList = duobaoService.duobaoList();
        if(CollectionUtils.isNotEmpty(duobaoList)){
            for (Duobao duobao : duobaoList) {
                setDuobaoScore(duobao);
            }
        }
        model.addAttribute("duobaoList", duobaoList);
        return "/yy/duobaoList";
    }
    
    @RequestMapping("/weixin/doubaoRule")
    public String doubaoRule(Model model) {
        return "/yy/doubaoRule";
    }

    private void setDuobaoScore(Duobao duobao) {
        
        duobao.setLeftScore((duobao.getTotalScore() - duobao.getCurrentScore())/100);
        duobao.setPercent(new Float(new DecimalFormat("####.00").format(duobao.getCurrentScore() * 1.0f * 100 / duobao.getTotalScore())));
        duobao.setLeftPercent(100 - duobao.getPercent());
        if(duobao.getCurrentScore() ==null){
            duobao.setCurrentScore(0);
        }
        duobao.setCurrentScore(duobao.getCurrentScore()/100);
        
        if(duobao.getTotalScore() ==null){
            duobao.setTotalScore(0);
        }
        duobao.setTotalScore(duobao.getTotalScore()/100);
        
        if(duobao.getOnceScore() ==null){
            duobao.setOnceScore(0);
        }
        duobao.setOnceScore(duobao.getOnceScore()/100);
        
        
        
    }
    
    
    @RequestMapping("/weixin/duobaoDetail")
    public String duobaoDetail(Model model,Long duobaoId) {
        User user = userService.getCurrentUserNotUnauthorizedException();

        
        Duobao duobao = duobaoService.findById(duobaoId);
        setDuobaoScore(duobao);
        model.addAttribute("duobao", duobao);
        UserScoreVO scoreVO = new UserScoreVO();
        if (user != null) {
            Long userId = user.getId();
            long totalScore = userScoreService.getTotalScoreByUserId(userId);
            long deductedScore = userScoreService.getDeductedScoreByUserId(userId);
            scoreVO.setUsableScore(totalScore - deductedScore);
            model.addAttribute("usableScoreYuan", scoreVO.getUsableScoreYuan());
        }
        
        List<UserDuobao> userDuobaoList = null;
        
        if(applicationConfig.isProd()){
            userDuobaoList =  userDuobaoService.findByDuobaoId(duobaoId);
        }else{
            userDuobaoList = new ArrayList<UserDuobao>();
            userDuobaoList = new ArrayList<UserDuobao>();
            UserDuobao userDuobao = new UserDuobao();
            userDuobao.setId(1232143543l);
            userDuobao.setDuobaoId(1L);
            userDuobao.setScore(500);
            userDuobao.setUserId(1L);
            userDuobao.setWin(1);
            userDuobao.setCreatedAt(new Date());
            userDuobaoList.add(userDuobao);
        }
        model.addAttribute("userDuobaoList", userDuobaoList);
        List<UserDuobao> winUserDuobaoList = new ArrayList<UserDuobao>();
        if(CollectionUtils.isNotEmpty(userDuobaoList)){
            for (UserDuobao userDuobao : userDuobaoList) {
                if(userDuobao.getWin() == 1){
                    winUserDuobaoList.add(userDuobao);
                }
                User duobaoUser =  null;
                if(applicationConfig.isProd()){
                     duobaoUser = userService.load(userDuobao.getUserId());
                }else{
                    duobaoUser = new User();
                    duobaoUser.setLoginname("苍井空");
                    duobaoUser.setNickname("苍井空");
                }
                duobaoUser.setNickname(StringUtils.replaceOnce(duobaoUser.getNickname(), duobaoUser.getNickname().substring(0,1), "*"));
                userDuobao.setUser(duobaoUser);
            }
        }
        model.addAttribute("winUserDuobaoList", winUserDuobaoList);
        return "/yy/duobaoDetail";
    }
    
    @RequestMapping("/weixin/addDuobao")
    public String addDuobao(Model model,Long duobaoId) {
        User user = userService.getCurrentUserNotUnauthorizedException();
        Duobao duobao = duobaoService.findById(duobaoId);
        model.addAttribute("duobao", duobao);

        UserScoreVO scoreVO = new UserScoreVO();

        if (user != null) {
            Long userId = user.getId();
            long totalScore = userScoreService.getTotalScoreByUserId(userId);
            long deductedScore = userScoreService.getDeductedScoreByUserId(userId);
            scoreVO.setUsableScore(totalScore - deductedScore);
            if (scoreVO.getUsableScore() < duobao.getOnceScore()) {
                model.addAttribute("errorMessage", "余额不足");
                setDuobaoScore(duobao);
                return "/weixin/duobaoDetail";
            }
            if (duobao.getStatus() == 2) {
                model.addAttribute("errorMessage", "已揭晓");
                setDuobaoScore(duobao);
                return "/weixin/duobaoDetail";
            }
            
            UserScore userScore = new UserScore();
            userScore.setScore(duobao.getOnceScore());
            userScore.setType(ScoreType.CashApplyScore);
            userScore.setUserId(userId);
            userScoreService.insert(userScore);

            duobaoService.addCurrentScore(duobaoId, duobao.getOnceScore());

            Duobao duobaoNew = duobaoService.findById(duobaoId);
            if (duobaoNew.getCurrentScore() >= duobaoNew.getTotalScore()) {
                duobaoService.updateStatus(duobaoId, 2);
                List<UserDuobao> userDuobaoList =  userDuobaoService.findByDuobaoId(duobaoId);
                if(CollectionUtils.isNotEmpty(userDuobaoList)){
                    UserDuobao winner = userDuobaoList.get(RandomUtils.nextInt(0, userDuobaoList.size()));
                    userDuobaoService.updateWinStatus(winner.getId(),1);
                    
                }
            }
            
            	Long userIdLong = user.getId();
                 UserDuobao userDuobao = null;// userDuobaoService.findByDuobaoIdAndUserId(duobao.getId(), userIdLong);
                userDuobao = new UserDuobao();
                userDuobao.setUserId(userIdLong);
                userDuobao.setDuobaoId(duobaoId);
                userDuobao.setScore(duobao.getOnceScore());
                userDuobao.setWin(0);
                userDuobaoService.insert(userDuobao);
        }else{
            setDuobaoScore(duobao);
            model.addAttribute("errorMessage", "未登陆");
            return "/weixin/duobaoDetail";
        }
        
        return "redirect:/weixin/duobaoDetail?duobaoId="+duobaoId;
    }
    @RequestMapping("/weixin/myDuobaoList")
    public String myDuobaoList(Model model) {
        User user = userService.getCurrentUserNotUnauthorizedException();
            Long userIdLong = null;
            if(user!=null){
                userIdLong =  user.getId();
            }
    
            List<UserDuobao> userDuobaoList = userDuobaoService.findByUserId(userIdLong);
            if(CollectionUtils.isNotEmpty(userDuobaoList)){
                for (UserDuobao userDuobao : userDuobaoList) {
                    Duobao duobao = duobaoService.findById(userDuobao.getDuobaoId());
                    setDuobaoScore(duobao);
                    userDuobao.setDuobao(duobao);
                    if(userDuobao.getScore()==null){
                        userDuobao.setScore(0);
                    }
                    userDuobao.setScore(userDuobao.getScore()/100);
                }
            }
            model.addAttribute("userDuobaoList", userDuobaoList);
        return "/yy/myDuobaoList";
    }

	@RequestMapping("/weixin/getScoreInfo")
	public String getScoreInfo(Model model) {
		User user = userService.getCurrentUserNotUnauthorizedException();
		UserScoreVO scoreVO = new UserScoreVO();
		if (user != null) {
			Long userId = user.getId();
			long totalScore = userScoreService.getTotalScoreByUserId(userId);
			long cashApplyScore = userScoreService
					.getCashApplyScoreByUserId(userId);
			long deductedScore = userScoreService
					.getDeductedScoreByUserId(userId);
			scoreVO.setCashApplyScore(cashApplyScore);
			scoreVO.setUsableScore(totalScore - deductedScore);
			scoreVO.setTotalScore(totalScore);

			long tryScore = userScoreService.getScoreByUserIdScoreType(userId,
					ScoreType.TryPlayScore);
			scoreVO.setTryScore(tryScore);

			long apprenticeScore = userScoreService.getScoreByUserIdScoreType(
					userId, ScoreType.ApprenticeScore);
			scoreVO.setApprenticeScore(apprenticeScore);
			model.addAttribute("scoreInfo", scoreVO);
		}
		return "/yy/revenueInquiry";
	}

	
	@RequestMapping("/weixin/cashIn")
	@ResponseBody
	public ResponseObject<Map<String,Object>> cashIn() {
		ResponseObject<Map<String,Object>> obj = new ResponseObject<Map<String,Object>>();
		User user = userService.getCurrentUserNotUnauthorizedException();
		UserScore userScore = new UserScore();
		userScore.setUserId(user.getId());
		userScore.setScore(100);
		userScore.setType(ScoreType.CashInTmp);
		userScoreService.insert(userScore);
		Map<String,Object> data = new HashMap<String,Object>();
		obj.setData(data);
		data.put("cash", userScore.getScore());
		data.put("cash", userScore.getId());
		return obj;
	}

}
