package com.maplebreak.nakama.match;

import com.heroiclabs.nakama.Client;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class NakamaMatchListCommand implements NakamaMatchCommand {

    @Resource
    private Client nakamaClient;

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.reply("Empty matches.");
    }

    @Override
    public String name() {
        return "list";
    }
}
