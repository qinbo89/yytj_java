package com.hongbao.task.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hongbao.service.util.ServiceUtil;

@Aspect
@Component
public class AuthAspect {

    @Autowired
    private ServiceUtil serviceUtil;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundController(final ProceedingJoinPoint joinPoint) throws Throwable {
        return serviceUtil.aroundController(joinPoint);
    }
}
