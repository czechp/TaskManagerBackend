package com.pczech.taskmanager.interceptor;

import com.pczech.taskmanager.domain.TaskStatus;
import com.pczech.taskmanager.domain.TaskSuperClass;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Component()
public class HibernateInterceptorComponent extends EmptyInterceptor {

}

