package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;
import ru.job4j.content.Content;
@Service
public class BotCommandHandler implements BeanNameAware {
    private String beanName;

    void receive(Content content) {
        System.out.println(content);
    }

    @PostConstruct
    public void init() {
        System.out.println("BotCommandHandler bean has been initialized.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("BotCommandHandler bean is about to be destroyed.");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean name set to: " + beanName);
    }
}