package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerChatEvent;
import org.screenwork.minestomtest.Main;

public class PlayerChat {

    public PlayerChat() {

        MinecraftServer.getGlobalEventHandler().addListener(PlayerChatEvent.class, event -> Main.logger.info("<" + event.getPlayer().getUsername() + "> " + event.getMessage()));

    }
}
