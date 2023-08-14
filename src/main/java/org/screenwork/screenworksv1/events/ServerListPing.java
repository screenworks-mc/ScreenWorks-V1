package org.screenwork.screenworksv1.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.server.ServerListPingEvent;

public class ServerListPing {

    public ServerListPing() {

        MinecraftServer.getGlobalEventHandler().addListener(ServerListPingEvent.class, event -> {
            event.getResponseData().setMaxPlayer(800);
            event.getResponseData().setDescription("ScreenWork Minecraft Server");
            event.getResponseData().setOnline(MinecraftServer.getConnectionManager().getOnlinePlayers().size());
            event.getResponseData().addPlayer("play.screenwork.net");
            event.getResponseData().setVersion("1.20.1");
        });

    }
}
