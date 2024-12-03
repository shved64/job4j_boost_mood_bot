package ru.job4j;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.content.Content;

@Service
public class RecommendationEngine {

    @PostConstruct
    public void init() {
        System.out.println("RecommendationService bean has been initialized.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("RecommendationService bean is about to be destroyed.");
    }

    public Content recommendFor(long chatId, Long moodId) {
        return new Content(moodId);
    }
}
