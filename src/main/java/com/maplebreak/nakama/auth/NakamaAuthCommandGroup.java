package com.maplebreak.nakama.auth;

import com.maplebreak.discord.cmd.SlashCommand;
import com.maplebreak.discord.cmd.SlashCommandGroup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NakamaAuthCommandGroup implements SlashCommandGroup {
    private final Map<String, SlashCommand> authCommandMap;

    public NakamaAuthCommandGroup(List<NakamaAuthCommand> authCommandList) {
        this.authCommandMap = index(authCommandList);
    }

    @Override
    public String name() {
        return "auth";
    }

    @Override
    public Map<String, SlashCommand> subCommandMap() {
        return authCommandMap;
    }
}
