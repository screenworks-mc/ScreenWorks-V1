package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import org.screenwork.minestomtest.Main;

public class PlayerLogin {

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        public PlayerLogin() {

            globalEventHandler.addListener(PlayerLoginEvent .class, event -> {
                event.getPlayer().setGameMode(GameMode.CREATIVE);
                event.getPlayer().setPermissionLevel(4);
                Main.logger.info("[+] " + event.getPlayer().getUsername());});

            globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> Main.logger.info("[-] " + event.getPlayer().getUsername()));

            globalEventHandler.addListener(PlayerSkinInitEvent .class, event -> {
                PlayerSkin skin = PlayerSkin.fromUsername(event.getPlayer().getUsername());
                event.setSkin(skin);});

            globalEventHandler.addListener(PlayerChatEvent.class, event -> Main.logger.info("<" + event.getPlayer().getUsername() + "> " + event.getMessage()));

        }
}
