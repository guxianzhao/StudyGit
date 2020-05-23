package com.example.nosqldemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrintAspect {
    private final String POINT_CUT = "execution(public * com.example.nosqldemo.controller.*.*(..))";

//    @Pointcut(POINT_CUT)
//    public void pointCut(){
//        System.out.println("切入点的方法");
//    }
//
//    @Before(value = "pointCut()")
//    public void before(JoinPoint joinPoint){
//        System.out.println("在方法之前执行！");
//    }
//
//@After(value = POINT_CUT)
//public void doAfterAdvice(JoinPoint joinPoint){
//    System.out.println("后置方法执行");
//}
    @AfterThrowing(value = POINT_CUT,throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception){
        System.out.println("方法有异常");
    }


}
