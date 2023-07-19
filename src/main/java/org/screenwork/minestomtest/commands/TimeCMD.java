package org.screenwork.minestomtest.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

public class TimeCMD extends Command {


    public TimeCMD() {
        super("time");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /time <time>");
        });

        var choiceArg = ArgumentType.Word("choice").from("set");
        var timeArgument = ArgumentType.String("timeset");

        timeArgument.setSuggestionCallback((sender, context, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("day"));
            suggestion.addEntry(new SuggestionEntry("night"));
            suggestion.addEntry(new SuggestionEntry("noon"));
            suggestion.addEntry(new SuggestionEntry("midnight"));
            suggestion.addEntry(new SuggestionEntry("sunrise "));
            suggestion.addEntry(new SuggestionEntry("sunset"));});

        addSyntax((sender, context) -> {

            final String time = context.get(timeArgument);
            setTime(sender, time);
        }, choiceArg, timeArgument);
    }

    private void setTime(CommandSender sender, String time) {

        if (String.valueOf(time) == null) {
            sender.asPlayer().getInstance().setTime(Long.parseLong(time));
        } else {

            switch (time) {
                case "day" -> sender.asPlayer().getInstance().setTime(1000);
                case "night" -> sender.asPlayer().getInstance().setTime(13000);
                case "noon" -> sender.asPlayer().getInstance().setTime(6000);
                case "midnight" -> sender.asPlayer().getInstance().setTime(18000);
                case "sunrise" -> sender.asPlayer().getInstance().setTime(23000);
                case "sunset" -> sender.asPlayer().getInstance().setTime(12000);
            }
        }

        sender.sendMessage("World time has been set to " + time);
    }
}