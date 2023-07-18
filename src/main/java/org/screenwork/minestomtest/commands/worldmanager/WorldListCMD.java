package org.screenwork.minestomtest.commands.worldmanager;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldListCMD extends Command {

        public WorldListCMD() {
            super("list");

            addSyntax(this::executer);
        }

    private void executer(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

            Player player = (Player) commandSender;

            player.sendMessage("Worlds:");
            MinecraftServer.getInstanceManager().getInstances().forEach(instance -> player.sendMessage(instance.getDimensionName()));

    }
}
