package com.example.nosqldemo.aop;

public class AopService {
    public String sayHello(String name) throws Exception {
        if (name.equals("")){
            throw  new Exception("空字符串了");
        }
        return "hello";
    }
}
