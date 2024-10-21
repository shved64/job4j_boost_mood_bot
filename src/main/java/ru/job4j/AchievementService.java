package ru.job4j;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {

    @PostConstruct
    public void init() {
        System.out.println("AchievementService bean has been initialized.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("AchievementService bean is about to be destroyed.");
    }
}
