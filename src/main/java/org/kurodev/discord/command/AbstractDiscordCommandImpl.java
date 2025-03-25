package org.kurodev.discord.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDiscordCommandImpl extends ListenerAdapter implements DiscordCommand {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String command;

    public AbstractDiscordCommandImpl(String command) {
        this.command = command;
    }

    @Override
    public final void prepare(JDA jda) {
        if (getDescription().isBlank()) {
            throw new RuntimeException(this.getClass().getSimpleName() + " has no description");
        }
        jda.retrieveCommands().queue(commands -> {
            var serverCommands = commands.stream().map(Command::getName).toList();
            if (!serverCommands.contains(command)) {
                CommandCreateAction command = jda.upsertCommand(this.command, getDescription());
                finalizeCommandPreparation(command);
                command.queue(inst -> logger.info("{} registered", inst.getName()));
            }
        });
        logger.info("Registering new command '{}'", command);
        jda.addEventListener(this);
    }

    protected void finalizeCommandPreparation(CommandCreateAction command) {

    }

    public abstract String getDescription();

    @Override
    public final void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (command.equalsIgnoreCase(event.getInteraction().getName())) {
            invoke(event);
        }
    }

    @Override
    public final String getCommand() {
        return command;
    }

    @Override
    public final void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        if (command.equalsIgnoreCase(event.getInteraction().getName())) {
            getAutocompleteSuggestions(event);
        }
    }

    protected void getAutocompleteSuggestions(CommandAutoCompleteInteractionEvent event) {

    }

    /**
     * This command has been invoked by a User
     */
    protected abstract void invoke(SlashCommandInteractionEvent event);
}
