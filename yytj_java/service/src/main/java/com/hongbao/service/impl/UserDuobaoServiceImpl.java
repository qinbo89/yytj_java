package com.hongbao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.mapper.UserDuobaoMapper;
import com.hongbao.dal.model.UserDuobao;
import com.hongbao.service.UserDuobaoService;
import com.hongbao.service.UserService;


@Service("userDuobaoService")
public class UserDuobaoServiceImpl extends BaseServiceImpl implements UserDuobaoService {

	private Logger log = LoggerFactory.getLogger(UserDuobaoServiceImpl.class);
	@Autowired
    private UserService userService;
	@Autowired
	private UserDuobaoMapper userDuobaoMapper;
    @Autowired
    private ApplicationConfig applicationConfig;
    
    public UserDuobao findByDuobaoIdAndUserId(Long duobaoId, Long userId) {
        return userDuobaoMapper.findByDuobaoIdAndUserId(duobaoId,userId);
    }

    
    public void addScore(String id, Integer onceScore) {
        userDuobaoMapper.addScore(id,onceScore);
    }

    
    public void insert(UserDuobao userDuobao) {
        userDuobaoMapper.insert(userDuobao);
    }

   
    public List<UserDuobao> findByUserId(Long userId) {
        if (applicationConfig.isProd()== false) {
            List<UserDuobao> list = new ArrayList<UserDuobao>();
            UserDuobao userDuobao = new UserDuobao();
            userDuobao.setId(1232143543l);
            userDuobao.setDuobaoId(1L);
            userDuobao.setScore(500);
            userDuobao.setUserId(1L);
            userDuobao.setWin(0);
            list.add(userDuobao);
            return list;
        }
        return userDuobaoMapper.findByUserId(userId);
    }

    
    public List<UserDuobao> findByDuobaoId(Long duobaoId) {
        return userDuobaoMapper.findByDuobaoId(duobaoId);
    }

    
    public int updateWinStatus(Long id, int status) {
        return userDuobaoMapper.updateWinStatus(id,status);
    }


	@Override
	public void addScore(Long id, Integer onceScore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
