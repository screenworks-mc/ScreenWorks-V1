package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

public class TeleportCMD extends Command {


    public TeleportCMD() {
        super("tp");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /tp <tplayer>");
        });

        var playerArgument = ArgumentType.String("playertotp");

        addSyntax((sender, context) -> {
            final String player = context.get(playerArgument);
            final Pos coords = 
            teleportTo(sender, coords);
        }, playerArgument);
    }

    private void teleportTo(CommandSender sender, Pos coords) {
        sender.asPlayer().teleport(coords);
        sender.sendMessage("You teleported to " + coords);
    }
}