package org.kurodev.discord.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import org.kurodev.discord.command.AbstractDiscordCommandImpl;
import org.kurodev.discord.command.AutoRegister;

/**
 * this is probably redundant now and can be removed later
 */
@AutoRegister
public class NotificationAdminCommand extends AbstractDiscordCommandImpl {
    private static final String EVENT_ADD_MODAL_ID = "event-name-input-modal";
    private static final String TEXT_FIELD_ID = "event-input-field";

    private static final String REMOVE_SELECT_ID = "event-entfernen-select-field";
    private static final String ADD_BUTTON_ID = "add-button";

    private static final String REMOVE_BUTTON_ID = "remove-button";

    public NotificationAdminCommand() {
        super("notification-admin");
    }

    @Override
    public String getDescription() {
        return "Events hinzufügen und entfernen";
    }

    @Override
    protected void invoke(SlashCommandInteractionEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Event admin")
                .setDescription("Click a button below!")
                .build();


        Button addBtn = Button.primary(ADD_BUTTON_ID, "event hinzufügen");
        Button rmBtn = Button.secondary(REMOVE_BUTTON_ID, "event entfernen");
        event.replyEmbeds(embed)
                .addActionRow(addBtn, rmBtn)
                .setEphemeral(true)
                .queue();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        switch (event.getComponentId()) {
            case ADD_BUTTON_ID -> handleAddNewEvent(event);
            case REMOVE_BUTTON_ID -> handleRemoval(event);
        }
    }

    protected void handleAddNewEvent(ButtonInteractionEvent event) {
        Modal modal = Modal.create(EVENT_ADD_MODAL_ID, "Wie soll das neue Event heißen?")
                .addActionRow(
                        TextInput.create(TEXT_FIELD_ID, "Eventname", TextInputStyle.SHORT)
                                .setPlaceholder("raid XY")
                                .setMaxLength(50)
                                .build()
                )
                .build();

        event.replyModal(modal).queue();
    }

    private void handleRemoval(ButtonInteractionEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Event admin")
                .setDescription("Click a button below!")
                .build();

        StringSelectMenu menu = StringSelectMenu.create(REMOVE_SELECT_ID)
                .build();
        event.replyEmbeds(embed)
                .addActionRow(menu)
                .setEphemeral(true)
                .queue();
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals(REMOVE_SELECT_ID)) {
            String selectedValue = event.getValues().get(0); // Get first selected option
            event.reply("You chose: **" + selectedValue + "**")
                    .setEphemeral(true)
                    .queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("text-input-modal")) {
            String userInput = event.getValue(TEXT_FIELD_ID).getAsString();
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Neues Event wurde erstellt:")
                    .setDescription("Jetzt mit `/subscribe " + userInput + "` registrieren.");
            event.replyEmbeds(embed.build()).queue();
        }
    }
}
