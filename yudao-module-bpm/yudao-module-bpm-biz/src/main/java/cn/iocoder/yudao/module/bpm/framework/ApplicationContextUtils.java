package cn.iocoder.yudao.module.bpm.framework;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName ApplicationContextUtils
 * @Description
 * @Author 肖润杰
 * @Time 2023/3/29 17:05
 * @Version 1.0
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }
    public static <T> T getBean(Class<T> bean,Object... args){
        return applicationContext.getBean(bean,args);
    }
    public static <T> T getBean(Class<T> bean){
        return applicationContext.getBean(bean);
    }
    public static <T> T autoWire(T bean){
         applicationContext.getAutowireCapableBeanFactory()
                .autowireBeanProperties(bean,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,true);
        return bean;
    }
    public static <T> T autoWire(Class<T> bean){
        return (T) applicationContext.getAutowireCapableBeanFactory()
                .autowire(bean,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,true);
    }
}
