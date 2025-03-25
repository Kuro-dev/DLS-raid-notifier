package org.kurodev;

import org.kurodev.discord.bot.DiscordBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "org.kurodev")
public class Main implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {

        springContext = SpringApplication.run(Main.class, args);
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DiscordBot bot = new DiscordBot();
        Thread discordBotThread = new Thread(bot, "JDA thread");
        discordBotThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
    }
}
