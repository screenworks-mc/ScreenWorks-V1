package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;

public class PlayerLogin {

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        public PlayerLogin() {

            globalEventHandler.addListener(PlayerLoginEvent .class, event -> {
                event.getPlayer().setGameMode(GameMode.CREATIVE);
                event.getPlayer().setPermissionLevel(4);
                System.out.println("[+] " + event.getPlayer().getUsername());});

            globalEventHandler.addListener(PlayerSkinInitEvent .class, event -> {
                PlayerSkin skin = PlayerSkin.fromUsername(event.getPlayer().getUsername());
                event.setSkin(skin);});

            globalEventHandler.addListener(PlayerChatEvent.class, event -> {
                //Make actual logger later
                System.out.println("<" + event.getPlayer().getUsername() + "> " + event.getMessage());});

        }


}
