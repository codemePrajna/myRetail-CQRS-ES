package com.myRetail.config;

import org.aspectj.lang.annotation.Pointcut;

public class PointCutConfig {

    @Pointcut("execution(* com.myRetail..*(..))")
    public void genericLayerExecution() {
    }

}
