package com.maplebreak.discord.cmd;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public interface SlashCommand {
    String name();
    Mono<Void> handle(ChatInputInteractionEvent event);
}
