package com.pczech.taskmanager.aspect.advice;

import com.pczech.taskmanager.domain.*;
import com.pczech.taskmanager.service.EmailSenderService;
import com.pczech.taskmanager.service.WebSocketService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect()
@Component()
public class AspectAdvice {
    private final WebSocketService webSocketService;
    private final EmailSenderService emailSenderService;

    @Autowired()
    public AspectAdvice(WebSocketService webSocketService, EmailSenderService emailSenderService) {
        this.webSocketService = webSocketService;
        this.emailSenderService = emailSenderService;
    }


    @AfterReturning(pointcut = "@annotation(com.pczech.taskmanager.aspect.annotation.ObjectCreatedAspect)",
            returning = "result")
    public void objectCreated(Object result) {
        webSocketService.sendToGlobalInfo(prepareMessage(result, CrudOperations.CREATE));
        if (result instanceof MaintenanceTask)
            sendEmailNewBreakDown(result);
    }

    // todo: implement rest of CRUD methods


    private Message prepareMessage(Object object, CrudOperations crudOperations) {
        Message message = new Message();
        message.setAuthor(getCurrentUser());
        switch (crudOperations) {
            case CREATE: {
                message.setContent("Utworzono obiekt " + getObjectName(object));
                break;
            }
            case UPDATE: {
                message.setContent("Utworzono zmodyfikowano obiekt " + getObjectName(object));
                break;
            }
            case READ: {
                break;
            }
            case DELETE: {
                message.setContent("Usunięto obiekt ");
                break;
            }
            default:
                message.setContent("Not found");
                break;
        }
        return message;

    }

    private String getObjectName(Object object) {
        String result = "";
        if (object instanceof AppUser)
            result = ((AppUser) object).getUsername() + " - użytkownik";
        else if (object instanceof MaintenanceWorker)
            result = ((MaintenanceWorker) object).getFirstName()
                    + " "
                    + ((MaintenanceWorker) object).getSecondName()
                    + " - pracownik utrzymania ruchu";
        return result;
    }

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "Not found";
    }

    private void sendEmailNewBreakDown(Object object) {
        MaintenanceTask maintenanceTask = (MaintenanceTask) object;
        String body = new StringBuilder()
                .append("Awaria zlecona przez utrzymanie ruchu\n")
                .append("Zlecający: ")
                .append(maintenanceTask.getMaintenanceWorker().getFirstName())
                .append(" ")
                .append(maintenanceTask.getMaintenanceWorker().getSecondName())
                .append("\n")
                .append("Miejsce awarii: ")
                .append(maintenanceTask.getBreakdownPlace())
                .append("\n")
                .append("Opis awari: ")
                .append(maintenanceTask.getDescription())
                .toString();
        emailSenderService.sendEmailToEverybody("Awaria utrzymania ruchu - " + maintenanceTask.getBreakdownPlace(), body);
    }

}
