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
import org.screenwork.minestomtest.commands.BanCMD;

import java.util.ArrayList;
import java.util.List;

public class UnbanCMD extends Command {

    public UnbanCMD() {
        super("pardon", "unban");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /pardon <player>, /unban <player>");
        });

        var playerArgument = ArgumentType.String("playertounban");

        playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
            for (String string : BanCMD.playerBans) {
                suggestion.addEntry(new SuggestionEntry(string));
            }
        });

        addSyntax((sender, context) -> {
            final String string = context.get(playerArgument);
            sender.sendMessage("You just pardoned " + string + ".");
            BanCMD.playerBans.remove(string);
            System.out.println("BAN: " + string + " was just pardoned by " + sender.asPlayer().getUsername());
        }, playerArgument);
    }
}