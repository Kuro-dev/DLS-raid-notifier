package org.kurodev;

import org.kurodev.config.MySettings;
import org.kurodev.discord.bot.DiscordBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static final MySettings SETTINGS = new MySettings();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Path SETTINGS_FILE = Paths.get("./settings.properties");

    public static void main(String[] args) throws IOException {
        loadSettings();
        DiscordBot bot = new DiscordBot();
        Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
        Thread discordBotThread = new Thread(bot, "JDA thread");
        discordBotThread.start();
        SpringApplication.run(Main.class, args);

    }

    public static void loadSettings() throws IOException {
        if (Files.exists(SETTINGS_FILE)) {
            logger.info("---------LOADING SETTINGS---------");
            SETTINGS.load(Files.newInputStream(SETTINGS_FILE));
        } else {
            logger.info("---------CREATING NEW DEFAULT SETTINGS---------");
            SETTINGS.restoreDefault();
            saveSettings();
        }
    }

    public static void saveSettings() {
        try {
            if (!Files.exists(SETTINGS_FILE)) {
                Files.createDirectories(SETTINGS_FILE.getParent());
                Files.createFile(SETTINGS_FILE);
            }
            OutputStream ous = Files.newOutputStream(SETTINGS_FILE);
            SETTINGS.store(ous, "Bot settings file");
            ous.flush();
            ous.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
