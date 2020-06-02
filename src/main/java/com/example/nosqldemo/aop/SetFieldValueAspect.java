package com.example.nosqldemo.aop;

import com.example.nosqldemo.service.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Aspect
public class SetFieldValueAspect {

    @Autowired
    private BeanUtil beanUtil;

    // 以本注解为切面点
    @Around("@annotation(com.example.nosqldemo.annotation.NeedSetValueField)")
    public Object setFieldValue(ProceedingJoinPoint pjp) throws Throwable {

        Object ret = pjp.proceed();
        // 获取注解，通过反射执行方法
        beanUtil.setFieldValueForCollection((Collection) ret);
        return new Object();
    }
}
