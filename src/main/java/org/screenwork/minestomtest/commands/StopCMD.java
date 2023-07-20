package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.Instance;
import org.screenwork.minestomtest.Main;

public class StopCMD extends Command {

    public StopCMD() {

        super("stop");


        addSyntax((sender, context) -> {
            sender.sendMessage("Stopping server...");
            Main.logger.info("Stopping server...");
            MinecraftServer.getInstanceManager().getInstances().forEach(Instance::saveChunksToStorage);
            MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> player.kick("Server is shutting down."));
            MinecraftServer.getServer().stop();
        });

    }
}
