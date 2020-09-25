package com.pczech.taskmanager.interceptor;

import com.pczech.taskmanager.domain.TaskSuperClass;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Component()
public class HibernateInterceptorComponent extends EmptyInterceptor {
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof TaskSuperClass) {
            if (((TaskSuperClass) entity).getCreationDate() == null)
                ((TaskSuperClass) entity).setCreationDate(LocalDateTime.now());
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
}
