package org.screenwork.minestomtest.commands.worldmanager;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldInfo extends Command {

    public WorldInfo() {
        super("info");

        addSyntax(this::executer);

    }

    private void executer(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

        Player player = (Player) commandSender;

        player.sendMessage("World Info:");
        player.sendMessage("Name: " + player.getInstance().getDimensionName());
        player.sendMessage("Chunk Count: " + player.getInstance().getChunks().size());


    }
}
