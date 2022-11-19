package com.sorog.carrent;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectClass {
    @Autowired
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AspectClass.class);


    @Pointcut("execution(* com.sorog.carrent.controllers.*.*(..))")
    public void calledRestController() {
    }

    @After("calledRestController()")
    public void log2(JoinPoint point) {
        log.info(point.getSignature().getName() + " called...");
    }

}