package org.screenwork.minestomtest;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.screenwork.minestomtest.blocks.ItemFrame;
import org.screenwork.minestomtest.commands.*;
import org.screenwork.minestomtest.space.Attributes;
import org.screenwork.minestomtest.space.Lightspeed;
import org.screenwork.minestomtest.events.*;
import org.screenwork.minestomtest.moderationsys.profile.BanID;
import org.screenwork.minestomtest.pack.pack;
import org.screenwork.minestomtest.space.ShipCreator;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static HashMap<UUID, BanID> banInfo = new HashMap<>();
    private final WorldEditEvents worldEditEvents = new WorldEditEvents();

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
        new ItemFrame();
        System.out.println("Attributes class instantiated");
        WorldEditEvents worldEditEvents = new WorldEditEvents();
        MinecraftServer.getCommandManager().register(new SetCMD(worldEditEvents));
        setupCommands();

        minecraftServer.start("0.0.0.0", 25566);

        globalEventHandler.addListener(PlayerCommandEvent.class, event -> {
            logger.info(event.getPlayer().getUsername() + " ran: /" + event.getCommand());
        });

        globalEventHandler.addListener(PlayerChatEvent.class, event -> {
            if ((event.getMessage().startsWith("save"))) {
                instanceContainer.saveChunksToStorage();
            } else if ((event.getMessage().startsWith("paper"))) {
                event.getPlayer().getInventory().addItemStack(ItemStack.builder(Material.PAPER).meta(builder -> builder.customModelData(69420)).build());
                EntityCreature entity = new EntityCreature(EntityType.ITEM_FRAME);
                entity.setInstance(instanceContainer);
                entity.teleport(new Pos(0, 42, 0));
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
        MinecraftServer.getCommandManager().register(new Lightspeed());
        MinecraftServer.getCommandManager().register(new ShipCreator());
    }
}
