package org.screenwork.minestomtest.moderationsys.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;

public class BanCMD extends Command {

    public static final List<String> playerBans = new ArrayList<String>();
    final ArgumentString reasonArgument = ArgumentType.String("banreason");

    public BanCMD() {
        super("ban");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /ban <player> <reason>");
        });

        var playerArgument = ArgumentType.String("target");

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

            String targetArg = context.get(playerArgument);

            if (playerBans.contains(targetArg)) {
                sender.sendMessage("That player is already banned!");
                return;
            }

            final Player target = MinecraftServer.getConnectionManager().findPlayer(targetArg);

            if (target != null)
                target.kick("You were just banned! Reason: " + context.get(reasonArgument));

            sender.sendMessage("You just banned " + target.getUsername() + " for \"" + context.get(reasonArgument) + "\".");

            playerBans.add(target.getUsername());

            System.out.println("Ban List: " + playerBans);
            System.out.println("BAN: " + target.getUsername() + " was just banned by " + sender.asPlayer().getUsername());

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