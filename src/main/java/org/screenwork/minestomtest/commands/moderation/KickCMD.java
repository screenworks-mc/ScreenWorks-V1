package org.screenwork.minestomtest.commands.moderation;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;

public class KickCMD extends Command {


    public KickCMD() {
        super("kick");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /kick <player> <reason>");
        });

        var playerArgument = ArgumentType.String("playertokick");
        var reasonArgument = ArgumentType.String("kickreason");

        playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                suggestion.addEntry(new SuggestionEntry(player.getUsername()));
            }
        });

        reasonArgument.setSuggestionCallback((sender, context, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("\"Hacking\""));
            suggestion.addEntry(new SuggestionEntry("\"Staff Disrespect\""));
            suggestion.addEntry(new SuggestionEntry("\"Being Annoying\""));
        });

        addSyntax((sender, context) -> {

            final Player player = MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument));
            MinecraftServer.getConnectionManager().getPlayer(player.getPlayerConnection()).kick(" " + context.get(reasonArgument));

            sender.sendMessage("You just kicked " + player.getUsername() + " for \"" + context.get(reasonArgument) + "\".");

        }, playerArgument, reasonArgument);
    }
}