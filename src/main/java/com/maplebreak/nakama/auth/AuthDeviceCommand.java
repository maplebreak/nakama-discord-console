package com.maplebreak.nakama.auth;

import com.heroiclabs.nakama.Client;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class AuthDeviceCommand implements NakamaAuthCommand {
    @Resource
    private Client nakamaClient;

    @Override
    public String name() {
        return "device";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.reply("auth failed");
    }
}
