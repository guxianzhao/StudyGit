package com.example.nosqldemo.service;

import com.example.nosqldemo.annotation.NeedSetValue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

@Component
public class BeanUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setFieldValueForCollection(Collection collection) throws Exception {

        Class<?> clazz = collection.iterator().next().getClass();

        Field[] fields = clazz.getDeclaredFields();

//        Map<String, Object> cache = new HashMap<>();

        for (Field needField : fields) {
            NeedSetValue sv = needField.getAnnotation(NeedSetValue.class);
            if (sv == null) {
                continue;
            }
            needField.setAccessible(true);

            Object bean = this.applicationContext.getBean(sv.beanClass());

            Method method = sv.beanClass().getMethod(sv.method(), clazz.getDeclaredField(sv.param()).getType());

            Field paramField = clazz.getDeclaredField(sv.param());
            paramField.setAccessible(true);

            Field targetFile = null;
            Boolean needInnerField = !StringUtils.isEmpty(sv.targetField());

            String keyPrefix = sv.beanClass() + "-" + sv.method() + "-" + sv.targetField();

            for (Object obj : collection) {
                Object paramValue = paramField.get(obj);
                if (paramValue == null) {
                    continue;
                }

                Object value = null;
//                String key = keyPrefix + paramValue;
//                if (cache.containsKey(key)) {
//                    value = cache.get(key);
//                } else {
                    value = method.invoke(bean, paramValue);

                    if (needInnerField && value != null) {
                        if (targetFile == null) {
                            targetFile = value.getClass().getDeclaredField(sv.targetField());
                            targetFile.setAccessible(true);
                        }
                        value = targetFile.get(value);
                    }
//                    cache.put(key, value);
//                }
//                needField.set(obj, value);
            }

        }
    }
}
