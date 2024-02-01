package com.maplebreak.discord;

import com.maplebreak.discord.cmd.SlashCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Component
public class DiscordCommandListener {
    private final Collection<SlashCommand> commands;

    public DiscordCommandListener(GatewayDiscordClient gatewayDiscordClient, List<SlashCommand> commands) {
        this.commands = commands;
        gatewayDiscordClient.on(ChatInputInteractionEvent.class, this::handle).subscribe();
    }

    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return Flux.fromIterable(commands)
                .filter(cmd -> cmd.name().equalsIgnoreCase(event.getCommandName()))
                .next()
                .flatMap(cmd -> cmd.handle(event));
    }
}
