package ru.mtsteta.courses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mtsteta.courses.service.StatisticsCounter;

@Configuration
public class StatisticsConfig {
    @Bean
    public StatisticsCounter statisticsCounter() {
        return new StatisticsCounter();
    }
}
