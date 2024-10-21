package ru.job4j;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class RemindService {

    @PostConstruct
    public void init() {
        System.out.println("RemindService bean has been initialized.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("RemindService bean is about to be destroyed.");
    }
}
