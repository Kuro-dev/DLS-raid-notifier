package org.kurodev.discord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.kurodev.Main;
import org.kurodev.config.Setting;
import org.kurodev.discord.command.AutoRegister;
import org.kurodev.discord.command.DiscordCommand;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class DiscordBot implements Runnable {
    private static JDA jda;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static JDA getJda() {
        return jda;
    }

    @Override
    public void run() {
        try {
            logger.info("Starting Discord Bot...");
            jda = JDABuilder.createDefault(Main.SETTINGS.getSetting(Setting.TOKEN)).build();
            jda.awaitReady();
            registerCommands();
            logger.info("Discord Bot started");
        } catch (Exception e) {
            logger.error("Failed to start discord bot", e);
        }
    }

    private void registerCommands() throws Exception {
        Reflections reflections = new Reflections("org.kurodev.discord.command");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(AutoRegister.class);

        List<DiscordCommand> instances = new ArrayList<>(annotatedClasses.size());
        for (Class<?> c : annotatedClasses) {
            var a = c.getAnnotation(AutoRegister.class);
            if (a.load()) {
                if (DiscordCommand.class.isAssignableFrom(c)) {
                    instances.add((DiscordCommand) c.getConstructor().newInstance());

                }
            }
        }
        List<String> commandNames = instances.stream().map(DiscordCommand::getCommand).toList();
        instances.forEach(discordCommand -> discordCommand.prepare(jda));
        jda.retrieveCommands().queue(commands -> {
            commands.forEach(cmd -> {
                if (!commandNames.contains(cmd.getName())) {
                    logger.info("Deleting command '{}'", cmd.getName());
                    jda.deleteCommandById(cmd.getId()).queue();
                }
            });
        });
    }

    public void shutdown() {
        jda.shutdown();
    }
}
