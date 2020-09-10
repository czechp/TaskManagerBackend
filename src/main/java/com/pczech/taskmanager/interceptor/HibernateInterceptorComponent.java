package com.pczech.taskmanager.interceptor;

import com.pczech.taskmanager.domain.AppUser;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component()
public class HibernateInterceptorComponent  extends EmptyInterceptor {

}
