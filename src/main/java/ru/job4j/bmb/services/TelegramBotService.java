package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;
import ru.job4j.content.Content;

@Service
public class TelegramBotService implements BeanNameAware {
    private final BotCommandHandler handler;
    private String beanName;

    public TelegramBotService(BotCommandHandler handler) {
        this.handler = handler;
    }

    @PostConstruct
    public void init() {
        System.out.println("TelegramBotService bean has been initialized.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("TelegramBotService bean is about to be destroyed.");
    }

    public void content(Content content) {
        handler.receive(content);
    }

    public void receive(Content content) {
        handler.receive(content);
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean name set to: " + beanName);
    }
}