package ru.job4j.bmb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${telegramm.bot.name}")
    private String botName;

    public void printConfig() {
        System.out.println("Bot Name: " + botName);
    }
}
