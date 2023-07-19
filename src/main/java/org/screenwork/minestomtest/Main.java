package org.screenwork.minestomtest;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import org.screenwork.minestomtest.commands.*;
import org.screenwork.minestomtest.worldedit.*;
import org.screenwork.minestomtest.commands.gamemode.GamemodeAliasCMD;
import org.screenwork.minestomtest.commands.gamemode.GamemodeCMD;
import org.screenwork.minestomtest.commands.moderation.BanCMD;
import org.screenwork.minestomtest.commands.GiveCMD;
import org.screenwork.minestomtest.commands.moderation.KickCMD;
import org.screenwork.minestomtest.commands.moderation.UnbanCMD;
import org.screenwork.minestomtest.commands.worldmanager.WorldManagerCMD;
import org.screenwork.minestomtest.events.PlayerLogin;
import org.screenwork.minestomtest.events.ServerListPing;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] arguments) {

        logger.info("Example log from {}", Main.class.getSimpleName());

        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        // Set the brand name
        MinecraftServer.setBrandName("ScreenWork - V1");
        
        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // Set the ChunkGenerator
        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(39, 40, Block.GLOWSTONE));


        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });


        new PlayerLogin();
        new ServerListPing();
        new WorldEditEvents();
        setupCommands();







        // Start the server on port 25566
        minecraftServer.start("0.0.0.0", 25566);

        globalEventHandler.addListener(PlayerCommandEvent.class, event -> {
            //Make actual logger later
            System.out.println(event.getPlayer().getUsername() + " ran: /" + event.getCommand());
        });

    }

    private static void setupCommands() {

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
        MinecraftServer.getCommandManager().register(new MessageCMD());
        MinecraftServer.getCommandManager().register(new WorldTP());
        MinecraftServer.getCommandManager().register(new WorldManagerCMD());
        MinecraftServer.getCommandManager().register(new SetBlockCMD());
        MinecraftServer.getCommandManager().register(new WandCMD());
        MinecraftServer.getCommandManager().register(new UpCMD());


    }
}