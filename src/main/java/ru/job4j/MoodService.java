package ru.job4j;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class MoodService {

    @PostConstruct
    public void init() {
        System.out.println("MoodService bean has been initialized.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("MoodService bean is about to be destroyed.");
    }
}
