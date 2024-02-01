package com.maplebreak.discord.cmd;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface SlashCommandGroup extends SlashCommand {
    String name();
    Map<String, SlashCommand> subCommandMap();

    default Map<String, SlashCommand> index(List<? extends SlashCommand> commandList) {
        if (CollectionUtils.isEmpty(commandList)) return Collections.emptyMap();
        return commandList.stream().collect(Collectors.toMap(SlashCommand::name, Function.identity()));
    }

    default Mono<Void> handle(ChatInputInteractionEvent event) {
        final List<ApplicationCommandInteractionOption> subMapleCommandOptionList = event.getOptions();
        if (CollectionUtils.isEmpty(subMapleCommandOptionList)) {
            return event.reply("illegal command option").withEphemeral(true);
        }

        return subMapleCommandOptionList.stream()
                .filter(it -> it.getValue().isPresent())
                .map(ApplicationCommandInteractionOption::getName)
                .findFirst()
                .map(subCommandMap()::get)
                .map(it -> it.handle(event))
                .orElse(event.reply("SUB COMMAND NOT FOUND."));
    }
}
