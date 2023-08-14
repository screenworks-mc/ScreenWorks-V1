package org.screenwork.screenworksv1.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerCommandEvent;
import org.screenwork.screenworksv1.Main;

public class PlayerCommand {

    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
    public PlayerCommand() {

        globalEventHandler.addListener(PlayerCommandEvent.class, event -> {
            Main.logger.info(event.getPlayer().getUsername() + " ran: /" + event.getCommand());
        });

    }
}
