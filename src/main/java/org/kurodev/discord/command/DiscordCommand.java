package org.kurodev.discord.command;

import net.dv8tion.jda.api.JDA;

/**
 * @author kuro
 **/
public interface DiscordCommand {

    void prepare(JDA jda);

    default void onShutdown() throws Exception {

    }

    String getCommand();
}
