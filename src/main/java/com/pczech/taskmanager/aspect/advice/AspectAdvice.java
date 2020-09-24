package com.pczech.taskmanager.aspect.advice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect()
@Component()
public class AspectAdvice {
    @AfterReturning(pointcut = "@annotation(com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect)",
            returning = "result")
    public void taskCreatedAdvice(Object result) {

    }
}
