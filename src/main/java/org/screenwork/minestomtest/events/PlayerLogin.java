package org.screenwork.minestomtest.events;

import net.hollowcube.polar.PolarLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.world.DimensionType;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import org.screenwork.minestomtest.Main;
import org.screenwork.minestomtest.moderationsys.profile.BanID;
import org.screenwork.minestomtest.testing.sdqnger.TestGui;

import java.io.IOException;
import java.nio.file.Path;

import static org.screenwork.minestomtest.Main.instanceContainer;
import static org.screenwork.minestomtest.Main.instance;

public class PlayerLogin {

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        public PlayerLogin() {

            globalEventHandler.addListener(PlayerLoginEvent.class, event -> {

                if (Main.banInfo.containsKey(event.getPlayer().getUuid())) {
                    BanID banID = Main.banInfo.get(event.getPlayer().getUuid());

                    // event.getPlayer().kick("You are currently banned for: " + banID.getReason() + " by " + banID.getModerator() + " for " + banID.getDuration() + " more seconds.");

                }});

            //Permissions and gamemode
            globalEventHandler.addListener(PlayerLoginEvent.class, event -> {

                event.getPlayer().setGameMode(GameMode.CREATIVE);
                event.getPlayer().setPermissionLevel(4);
                Main.logger.info("[+] " + event.getPlayer().getUsername());
            });

            //Skin
            globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
                PlayerSkin skin = PlayerSkin.fromUsername(event.getPlayer().getUsername());
                event.setSkin(skin);});

            globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
                final Player player = event.getPlayer();
                event.setSpawningInstance(instanceContainer);

                instanceContainer.setChunkSupplier(LightingChunk::new);
                player.setRespawnPoint(new Pos(0, 42, 0));
            });

        }
}
