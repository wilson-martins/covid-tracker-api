package com.mc855.tracker.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan({
        "com.mc855.tracker.service",
        "com.mc855.tracker.web.rest",
        "com.mc855.tracker.app",
        "com.mc855.tracker.config",
        "com.mc855.tracker.domain",
})
@EnableJpaRepositories({
        "com.mc855.tracker.repository"
})
@EnableJpaAuditing
@EntityScan(basePackages = {
        "com.mc855.tracker.domain"
})
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties
@Slf4j
public class Application {

    static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        // It will set UTC timezone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        ctx = SpringApplication.run(Application.class, args);

        String databaseUrl = ctx.getEnvironment().getProperty("spring.datasource.url");
        log.info("Using database [{}]", databaseUrl);

        List<String> beans = Arrays.asList(ctx.getBeanDefinitionNames());
        Collections.sort(beans);
        log.info("Beans loaded -->");
        beans.forEach(name -> {
            log.info("{} :: {}", name, ctx.getBean(name) != null && ctx.getBean(name).getClass() != null ? ctx.getBean(name).getClass().getName() : "");
        });

        log.info("Application started");
    }

    public static <T> T getBean(Class<T> type) {
        return ctx.getBean(type);
    }
}

