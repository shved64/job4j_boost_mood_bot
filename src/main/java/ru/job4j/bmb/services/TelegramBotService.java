package ru.job4j.bmb.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import ru.job4j.content.Content;

@Service
public class TelegramBotService {
    private final BotCommandHandler handler;

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
}