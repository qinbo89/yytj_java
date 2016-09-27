package com.hongbao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.mapper.DuobaoMapper;
import com.hongbao.dal.model.Duobao;
import com.hongbao.service.DuobaoService;
import com.hongbao.service.UserService;


@Service("duobaoService")
public class DuobaoServiceImpl extends BaseServiceImpl implements DuobaoService {

	private Logger log = LoggerFactory.getLogger(DuobaoServiceImpl.class);

	@Autowired
	private DuobaoMapper duobaoMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationConfig applicationConfig;
	
	
    public List<Duobao> duobaoList() {
        
        if (applicationConfig.isProd()== false) {
            List<Duobao> duobaoList = new ArrayList<Duobao>();
            
            Duobao duobao3 = new Duobao();
            duobao3.setId(123123213l);
            duobao3.setTitle("测试测试2测试三星 SAMSUNG Galaxy S6 Edge+ 32G 全网通4G手机");
            duobao3.setImageUrl("http://m.1yhlg.com/statics/uploads/shopimg/20151211/61812375797063.png");
            duobao3.setTotalScore(680000);
            duobao3.setCurrentScore(190000);
            duobao3.setStatus(1);
            duobao3.setOnceScore(100);
            duobaoList.add(duobao3);
            
            
            Duobao duobao2 = new Duobao();
            duobao2.setId(123123213l);
            duobao2.setTitle("测试测试2测试三星 SAMSUNG Galaxy S6 Edge+ 32G 全网通4G手机");
            duobao2.setImageUrl("http://onegoods.nosdn.127.net/goods/895/263eb29c37baa1b9744c7118aa150665.png");
            duobao2.setTotalScore(680000);
            duobao2.setCurrentScore(190000);
            duobao2.setStatus(1);
            duobao2.setOnceScore(100);
            duobaoList.add(duobao2);
            
            
            Duobao duobao = new Duobao();
            duobao.setId(123123213l);
            duobao.setTitle("测试测试1测试三星 SAMSUNG Galaxy S6 Edge+ 32G 全网通4G手机");
            duobao.setImageUrl("http://m.1yhlg.com/statics/uploads/shopimg/20150505/24136228763623.jpg");
            duobao.setTotalScore(500000);
            duobao.setCurrentScore(380000);
            duobao.setStatus(2);
            duobao.setOnceScore(100);
            duobaoList.add(duobao);
            
            return duobaoList;
        }
        return duobaoMapper.duobaoList();
    }
    
    public Duobao findById(Long duobaoId) {
        if (applicationConfig.isProd()== false) {
            Duobao duobao = new Duobao();
            duobao.setId(123123213l);
            duobao.setTitle("测试测试测试三星 SAMSUNG Galaxy S6 Edge+ 32G 全网通4G手机");
            duobao.setImageUrl("http://onegoods.nosdn.127.net/goods/950/730946aa0631ac6fcaf9cbaac10a51e1.png");
            duobao.setOnceScore(100);
            duobao.setTotalScore(100000);
            duobao.setCurrentScore(10000);
            duobao.setStatus(1);
            return duobao;
        }
        return duobaoMapper.findById(Long.valueOf(duobaoId));
    }
    
    public void addCurrentScore(Long id, Integer score) {
         duobaoMapper.addCurrentScore(id,score);

    }
    
    public void updateStatus(Long id, int status) {
         duobaoMapper.updateStatus(id,status);
    }

	@Override
	protected Object load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
