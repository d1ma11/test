package service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import service.factory.AnimalFactory;

import java.util.Random;

public class CreateAnimalServicePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CreateAnimalServiceImpl) {
            ((CreateAnimalServiceImpl) bean).setAnimalType(AnimalFactory.AnimalType.values()[new Random().nextInt(3)]);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
