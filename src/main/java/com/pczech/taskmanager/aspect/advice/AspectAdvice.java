package com.pczech.taskmanager.aspect.advice;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.MaintenanceWorker;
import com.pczech.taskmanager.domain.Message;
import com.pczech.taskmanager.service.WebSocketService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Security;

@Aspect()
@Component()
public class AspectAdvice {
    @Autowired()
    private WebSocketService webSocketService;

    @Autowired()
    public AspectAdvice(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @AfterReturning(pointcut = "@annotation(com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect)",
            returning = "result")
    public void taskCreatedAdvice(Object result) {
        if(result instanceof MaintenanceWorker){
            webSocketService.sendToGlobalInfo(new Message("Stworzono nowego pracownika utrzymania ruchu", SecurityContextHolder.getContext().getAuthentication().getName()));
        }
    }
}
