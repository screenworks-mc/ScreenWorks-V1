package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;

public class BanCMD extends Command {

    public static final List<String> playerBans = new ArrayList<String>();
    final public ArgumentString reasonArgument = ArgumentType.String("banreason");

    public BanCMD() {
        super("ban");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /ban <player> <reason>");
        });

        var playerArgument = ArgumentType.String("playertoban");

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
            player.kick("You were just banned! Reason: " + context.get(reasonArgument));
            sender.sendMessage("You just banned " + player.getUsername() + " for \"" + context.get(reasonArgument) + "\".");
            playerBans.add(player.getUsername());
            System.out.println("Ban List: " + playerBans.toString());
            System.out.println("BAN: " + player.getUsername() + " was just banned by " + sender.asPlayer().getUsername());
        }, playerArgument, reasonArgument);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            // System.out.println("Ban List: " + playerBans.toString());
            if (playerBans.contains(event.getPlayer().getUsername())) {
                event.getPlayer().kick("You are currently banned! Reason: " + reasonArgument);
                System.out.println(event.getPlayer().getUsername() + " just tried to join, but is banned!");
            }
        });
    }
}