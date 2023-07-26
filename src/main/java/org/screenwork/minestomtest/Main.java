package org.screenwork.minestomtest;

import com.google.gson.stream.JsonReader;
import net.hollowcube.polar.PolarLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import org.screenwork.minestomtest.commands.*;
import org.screenwork.minestomtest.space.AttributeEvents;
import org.screenwork.minestomtest.space.Attributes;
import org.screenwork.minestomtest.space.lightspeed;
import org.screenwork.minestomtest.events.*;
import org.screenwork.minestomtest.moderationsys.profile.BanID;
import org.screenwork.minestomtest.pack.pack;
import org.screenwork.minestomtest.worldedit.*;
import org.screenwork.minestomtest.commands.gamemode.GamemodeAliasCMD;
import org.screenwork.minestomtest.commands.gamemode.GamemodeCMD;
import org.screenwork.minestomtest.moderationsys.commands.BanCMD;
import org.screenwork.minestomtest.commands.GiveCMD;
import org.screenwork.minestomtest.moderationsys.commands.KickCMD;
import org.screenwork.minestomtest.moderationsys.commands.UnbanCMD;
import org.screenwork.minestomtest.commands.worldmanager.WorldManagerCMD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static HashMap<UUID, BanID> banInfo = new HashMap<>();

    public static void main(String[] arguments) throws IOException {
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        MinecraftServer.setBrandName("ScreenWork V1");

        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // PolarLoader polar = (new PolarLoader(Path.of("src/main/java/org/screenwork/minestomtest/commands/instances")));
        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(39, 40, Block.DIAMOND_BLOCK));

        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            if (event.getPlayer().getUsername().equals("xDadx")) {
                event.getPlayer().kick("L");
            }
            event.setSpawningInstance(instanceContainer);
            instanceContainer.setChunkSupplier(LightingChunk::new);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        new PlayerDisconnect();
        new PlayerChat();
        new PlayerLogin();
        new ItemDrop();
        new ItemPickup();
        new BlockBreak();
        new pack();
        new ServerListPing();
        new WorldEditEvents();
        new Attributes();
        System.out.println("Attributes class instantiated");
        setupCommands();

        minecraftServer.start("0.0.0.0", 25566);

        globalEventHandler.addListener(PlayerCommandEvent.class, event -> {
            logger.info(event.getPlayer().getUsername() + " ran: /" + event.getCommand());
        });

        globalEventHandler.addListener(PlayerChatEvent.class, event -> {
            if ((event.getMessage().startsWith("save"))) {
                instanceContainer.saveChunksToStorage();
            }
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
        MinecraftServer.getCommandManager().register(new StopCMD());
        MinecraftServer.getCommandManager().register(new DisplayCMD());
        MinecraftServer.getCommandManager().register(new TpsCMD());
        MinecraftServer.getCommandManager().register(new ServerStatsCMD());
        MinecraftServer.getCommandManager().register(new lightspeed());
    }
}
