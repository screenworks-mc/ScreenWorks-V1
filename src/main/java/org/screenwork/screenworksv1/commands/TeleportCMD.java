package org.screenwork.screenworksv1.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

public class TeleportCMD extends Command {


    public TeleportCMD() {
        super("tp", "teleport");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /tp <player>");
        });

        var playerArgument = ArgumentType.String("playertotp");

        playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                suggestion.addEntry(new SuggestionEntry(player.getUsername()));
            }
        });

        addSyntax((sender, context) -> {
            final String player = context.get(playerArgument);
            final Pos coords = MinecraftServer.getConnectionManager().findPlayer(player).getPosition();
            teleportTo(sender, coords, player);
        }, playerArgument);
    }

    private void teleportTo(CommandSender sender, Pos coords, String player) {
        sender.asPlayer().teleport(coords);
        sender.sendMessage("You teleported to " + player);
    }
}