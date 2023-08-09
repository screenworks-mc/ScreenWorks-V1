package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.screenwork.minestomtest.Main;

public class PlayerDisconnect {

    public PlayerDisconnect() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerDisconnectEvent.class, event -> Main.logger.info("[-] " + event.getPlayer().getUsername()));
    }
}
