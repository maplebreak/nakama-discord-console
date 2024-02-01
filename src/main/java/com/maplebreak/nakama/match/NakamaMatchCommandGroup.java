package com.maplebreak.nakama.match;

import com.maplebreak.discord.cmd.SlashCommand;
import com.maplebreak.discord.cmd.SlashCommandGroup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NakamaMatchCommandGroup implements SlashCommandGroup {
    private final Map<String, SlashCommand> matchCommandMap;

    public NakamaMatchCommandGroup(List<NakamaMatchCommand> matchCommands) {
        this.matchCommandMap = index(matchCommands);
    }

    @Override
    public String name() {
        return "match";
    }

    @Override
    public Map<String, SlashCommand> subCommandMap() {
        return matchCommandMap;
    }
}
