package com.hongbao.service.support;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hongbao.dal.mapper.BaseMapper;

/**
 * Created by shengshan.tang on 2015/6/9 0009 at 11:11
 */
@Service("serviceSupport")
@Scope()
public class ServiceSupport {

    BaseMapper baseMapper;
}
