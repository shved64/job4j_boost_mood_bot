package ru.job4j;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import ru.job4j.bmb.content.Content;
import ru.job4j.bmb.model.Award;
import ru.job4j.bmb.model.MoodLog;
import ru.job4j.bmb.model.User;
import ru.job4j.bmb.repository.AchievementRepository;
import ru.job4j.bmb.repository.AwardRepository;
import ru.job4j.bmb.repository.MoodLogRepository;
import ru.job4j.bmb.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoodService {
    private final MoodLogRepository moodLogRepository;
    private final RecommendationEngine recommendationEngine;
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final AwardRepository awardRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm")
            .withZone(ZoneId.systemDefault());

    public MoodService(MoodLogRepository moodLogRepository,
                       RecommendationEngine recommendationEngine,
                       UserRepository userRepository,
                       AchievementRepository achievementRepository,
                       AwardRepository awardRepository) {
        this.moodLogRepository = moodLogRepository;
        this.recommendationEngine = recommendationEngine;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
        this.awardRepository = awardRepository;
    }

    public Content chooseMood(User user, Long moodId) {
        return recommendationEngine.recommendFor(user.getChatId(), moodId);
    }

    public Optional<Content> weekMoodLogCommand(long chatId, Long clientId) {
        List<MoodLog> moodLogs = filterMoodLogs(7);
        String formattedLogs = formatMoodLogs(moodLogs, "Mood Logs for the Last Week");
        var content = new Content(chatId);
        content.setText(formattedLogs);
        return Optional.of(content);
    }

    public Optional<Content> monthMoodLogCommand(long chatId, Long clientId) {
        List<MoodLog> moodLogs = filterMoodLogs(30);
        String formattedLogs = formatMoodLogs(moodLogs, "Mood Logs for the Last Month");
        var content = new Content(chatId);
        content.setText(formattedLogs);
        return Optional.of(content);
    }

    private String formatMoodLogs(List<MoodLog> logs, String title) {
        if (logs.isEmpty()) {
            return title + ":\nNo mood logs found.";
        }
        var sb = new StringBuilder(title + ":\n");
        logs.forEach(log -> {
            String formattedDate = formatter.format(Instant.ofEpochSecond(log.getCreatedAt()));
            sb.append(formattedDate).append(": ").append(log.getMood().getText()).append("\n");
        });
        return sb.toString();
    }

    private List<MoodLog> filterMoodLogs(long days) {
        long daysInMillis = days * 24 * 60 * 60 * 1000;
        long currentTime = System.currentTimeMillis();
        return moodLogRepository.findAll().stream()
                .filter(log -> log.getCreatedAt() >= (currentTime - daysInMillis))
                .collect(Collectors.toList());
    }

    public Optional<Content> awards(long chatId, Long clientId) {
        var content = new Content(chatId);
        List<MoodLog> moodLogs = moodLogRepository.findAll().stream()
                .filter(log -> log.getUser().getId().equals(clientId))
                .toList();
        int positiveDays = 0;
        int consecutiveDays = 0;
        int maxConsecutiveDays = 0;
        for (MoodLog log : moodLogs) {
            if (log.getMood().isGood()) {
                positiveDays++;
                consecutiveDays++;
                maxConsecutiveDays = Math.max(maxConsecutiveDays, consecutiveDays);
            } else {
                consecutiveDays = 0;
            }
        }
        List<Award> allAwards = awardRepository.findAll();
        List<String> achievedAwards = new ArrayList<>();
        for (Award award : allAwards) {
            boolean achieved = switch (award.getId().intValue()) {
                case 1 -> positiveDays >= 1;
                case 2 -> maxConsecutiveDays >= 7;
                case 3 -> positiveDays >= 3;
                case 4 -> positiveDays >= 5;
                case 5 -> maxConsecutiveDays >= 10;
                case 6 -> positiveDays >= 15;
                case 7 -> maxConsecutiveDays >= 20;
                case 8 -> positiveDays >= 30;
                case 9 -> positiveDays >= 7;
                case 10 -> positiveDays >= 25;
                case 11 -> positiveDays >= 30;
                case 12 -> positiveDays >= 60;
                case 13 -> positiveDays >= 45;
                case 14 -> positiveDays >= 14;
                case 15 -> positiveDays >= 50;
                default -> false;
            };
            if (achieved) {
                achievedAwards.add(award.getTitle() + ": " + award.getDescription());
            }
        }
        String message = achievedAwards.isEmpty() ? "You have no awards yet." : "Your Awards:\n" + String.join("\n", achievedAwards);
        content.setText(message);
        return Optional.of(content);
    }
}




