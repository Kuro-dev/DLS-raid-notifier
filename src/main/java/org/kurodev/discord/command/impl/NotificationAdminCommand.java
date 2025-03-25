package org.kurodev.discord.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;
import org.kurodev.Main;
import org.kurodev.data.MentionService;
import org.kurodev.data.UserService;
import org.kurodev.data.entity.server.ServerRole;
import org.kurodev.discord.command.AbstractDiscordCommandImpl;
import org.kurodev.discord.command.AutoRegister;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * this is probably redundant now and can be removed later
 */
@AutoRegister
public class NotificationAdminCommand extends AbstractDiscordCommandImpl {
    private static final String EVENT_ADD_MODAL_ID = "event-name-input-modal";
    private static final String EVENT_ADD_SELECT_ID = "event-input-field";

    private static final String REMOVE_SELECT_ID = "event-entfernen-select-field";
    private static final String ADD_BUTTON_ID = "add-button";

    private static final String REMOVE_BUTTON_ID = "remove-button";
    private final UserService userService;
    private final MentionService mentionService;

    public NotificationAdminCommand() {
        super("notification-admin");
        ApplicationContext context = Main.getSpringContext();

        // Retrieve the service bean
        this.userService = context.getBean(UserService.class);
        this.mentionService = context.getBean(MentionService.class);
    }

    @Override
    public String getDescription() {
        return "Rollen hinzufügen und entfernen";
    }

    @Override
    protected void invoke(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) {
            event.reply("Can only be used in a guild").setEphemeral(true).queue();
            return;
        }
        if (!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
            event.reply("Can only be used by Admins").setEphemeral(true).queue();
            return;
        }
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Event admin")
                .setDescription("Click a button below!")
                .build();

        Button addBtn = Button.primary(ADD_BUTTON_ID, "Rolle hinzufügen");
        Button rmBtn = Button.secondary(REMOVE_BUTTON_ID, "Rolle entfernen");
        event.replyEmbeds(embed)
                .addActionRow(addBtn, rmBtn)
                .setEphemeral(true)
                .queue();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        switch (event.getComponentId()) {
            case ADD_BUTTON_ID -> handleAddNewEvent(event);
            case REMOVE_BUTTON_ID -> handleRemoval(event, event.getGuild());
        }
    }

    protected void handleAddNewEvent(ButtonInteractionEvent event) {
        StringSelectMenu.Builder menu = StringSelectMenu.create(EVENT_ADD_SELECT_ID);
        List<Role> roles = event.getGuild().getRoles();
        roles.forEach(role -> {
            menu.addOption(role.getName(), role.getId());
        });
        menu.setMinValues(1);
        //mehr als 25 darf nicht genommen werden.
        menu.setMaxValues(Math.min(roles.size(), 25));
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Wähle Pingbare rollen aus:")
                .build();

        event.replyEmbeds(embed)
                .setEphemeral(true)
                .addActionRow(menu.build())
                .queue();
    }

    private void handleRemoval(ButtonInteractionEvent event, Guild guild) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Rollen entfernen:")
                .build();

        StringSelectMenu.Builder menu = StringSelectMenu.create(REMOVE_SELECT_ID);
        mentionService.getServerRoleOptions(guild.getIdLong()).forEach(serverRole -> {
            menu.addOption(serverRole.getName(), String.valueOf(serverRole.getId()));
        });
        menu.setMinValues(1);
        menu.setMaxValues(Math.min(guild.getRoles().size(), 25));

        event.replyEmbeds(embed)
                .addActionRow(menu.build())
                .setEphemeral(true)
                .queue();
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        List<String> values = event.getValues();
        final long guildId = event.getGuild().getIdLong();
        if (event.getComponentId().equals(REMOVE_SELECT_ID)) {
            List<Integer> dbIdsToDelete = event.getValues().stream().map(Integer::parseInt).toList();
            mentionService.delete(dbIdsToDelete);
            event.reply("Successfully deleted")
                    .setEphemeral(true)
                    .queue();
        }
        if (event.getComponentId().equals(EVENT_ADD_SELECT_ID)) {
            List<ServerRole> rollen = values.stream().map(s -> new ServerRole(Long.parseLong(s), guildId, event.getGuild().getRoleById(s).getName())).toList();
            mentionService.save(rollen);
            event.reply("Successfully saved")
                    .setEphemeral(true)
                    .queue();
        }
    }
}
