package org.screenwork.minestomtest;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerCommandEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.ping.ServerListPingType;
import org.screenwork.minestomtest.commands.*;
import org.screenwork.minestomtest.commands.gamemode.GamemodeAliasCMD;
import org.screenwork.minestomtest.commands.gamemode.GamemodeCMD;

public class Main {

    public static void main(String[] arguments) {

        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        // Set the brand name
        MinecraftServer.setBrandName("ScreenWork - V1");
        
        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // Set the ChunkGenerator
        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        globalEventHandler.addListener(ServerListPingEvent.class, event -> {
            event.getResponseData().setMaxPlayer(100);
            event.getResponseData().setDescription("ScreenWork Minecraft Server");
            event.getResponseData().setOnline(18);
            event.getResponseData().addPlayer("play.screenwork.net");
            event.getResponseData().setVersion("1.20.1");
        });

        // Start the server on port 25566
        minecraftServer.start("0.0.0.0", 25566);

        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            event.getPlayer().setGameMode(GameMode.CREATIVE);
            System.out.println("[+] " + event.getPlayer() + " - (AKA: " + event.getPlayer().getUsername() + ")");
        });

        globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            PlayerSkin skin = PlayerSkin.fromUsername(event.getPlayer().getUsername());
            event.setSkin(skin);
        });

        globalEventHandler.addListener(PlayerCommandEvent.class, event -> {
            //Make actual logger later
            System.out.println(event.getPlayer().getUsername() + " ran: /" + event.getCommand());
        });

        globalEventHandler.addListener(PlayerChatEvent.class, event -> {
            //Make actual logger later
            System.out.println("<" + event.getPlayer().getUsername() + "> " + event.getMessage());
        });

        MinecraftServer.getCommandManager().register(new GamemodeCMD());
        MinecraftServer.getCommandManager().register(new GamemodeAliasCMD());
        MinecraftServer.getCommandManager().register(new GiveCMD());
        MinecraftServer.getCommandManager().register(new TimeCMD());
        MinecraftServer.getCommandManager().register(new SummonCMD());
        MinecraftServer.getCommandManager().register(new TeleportCMD());
        MinecraftServer.getCommandManager().register(new KillCMD());
        MinecraftServer.getCommandManager().register(new KickCMD());
        MinecraftServer.getCommandManager().register(new BanCMD());
        MinecraftServer.getCommandManager().register(new UnbanCMD());
        MinecraftServer.getCommandManager().register(new ToBlockCMD());
        MinecraftServer.getCommandManager().register(new RebuildLightCacheCommand());

    }
}