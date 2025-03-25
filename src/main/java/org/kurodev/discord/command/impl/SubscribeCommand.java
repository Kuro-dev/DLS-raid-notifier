package org.kurodev.discord.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.kurodev.Main;
import org.kurodev.data.MentionService;
import org.kurodev.data.UserService;
import org.kurodev.discord.command.AbstractDiscordCommandImpl;
import org.kurodev.discord.command.AutoRegister;
import org.springframework.context.ApplicationContext;

/**
 * Change this functionality to use @mentions instead and just give/take roles depending on the times
 */
@AutoRegister
public class SubscribeCommand extends AbstractDiscordCommandImpl {
    private final MentionService mentionService;
    private final UserService userService;

    public SubscribeCommand() {
        super("subscribe");
        ApplicationContext context = Main.getSpringContext();
        this.mentionService = context.getBean(MentionService.class);
        this.userService = context.getBean(UserService.class);
    }

    @Override
    public String getDescription() {
        return "Subscribe to a notification and get notified";
    }


    @Override
    protected void invoke(SlashCommandInteractionEvent event) {
        //TODO actually implement this now.
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Button Menu")
                .setDescription("Click a button below!");


        Button primaryButton = Button.primary("custom-id-1", "Primary Button");
        Button successButton = Button.success("custom-id-2", "Success Button");
        Button dangerButton = Button.danger("custom-id-3", "Danger Button");

        StringSelectMenu menu = StringSelectMenu.create("my-menu")
                .setPlaceholder("Choose an option")
                .addOption("Option 1", "value1", "Description 1")
                .addOption("Option 2", "value2", "Description 2")
                .addOption("Option 3", "value3", "Description 3")
                .build();

        event.replyEmbeds(embed.build())
                .addActionRow(primaryButton, successButton)
                .addActionRow(dangerButton)
                .addActionRow(menu)
                .setEphemeral(true)
                .queue();
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("my-menu")) {
            String selectedValue = event.getValues().get(0); // Get first selected option
            event.reply("You chose: **" + selectedValue + "**")
                    .setEphemeral(true)
                    .queue();
        }
    }


}
