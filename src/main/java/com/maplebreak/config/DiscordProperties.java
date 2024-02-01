package com.maplebreak.config;

import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.ShardInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discord")
public record DiscordProperties(Long applicationId, Long guildId, String token, String activity) {
    public ClientPresence initialPresence(ShardInfo ignored) {
        return ClientPresence.online(ClientActivity.playing(activity));
    }
}