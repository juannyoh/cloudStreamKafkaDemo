package com.dituhui.saas.alan.a.in;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * SPRING操作类
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    //@Autowired
    //private SaasLoger saasLoger;

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = arg0;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过class获取BeanNames.
    public static <T> List<String> getBeanNamesForType(Class<T> clazz) {
//        return Lists.newArrayList(getApplicationContext().getBeanNamesForType(clazz));
    	return null;
    }

    // 通过class获取Beans.
    public static <T> Map<String, T> getBeansForType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}