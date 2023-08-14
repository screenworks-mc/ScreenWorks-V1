package org.screenwork.minestomtest;

import net.hollowcube.polar.PolarLoader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;
import nl.kiipdevelopment.minescreen.MineScreen;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import org.screenwork.minestomtest.blocks.ItemFrame;
import org.screenwork.minestomtest.commands.*;
import org.screenwork.minestomtest.mbr.EditEvents;
import org.screenwork.minestomtest.mbr.SetCMD;
import org.screenwork.minestomtest.mbr.WandCMD;
import org.screenwork.minestomtest.space.Attributes;
import org.screenwork.minestomtest.space.Lightspeed;
import org.screenwork.minestomtest.events.*;
import org.screenwork.minestomtest.moderationsys.profile.BanID;
import org.screenwork.minestomtest.pack.pack;
import org.screenwork.minestomtest.space.ShipCreator;
import org.screenwork.minestomtest.testing.Chairs;
import org.screenwork.minestomtest.testing.TopDown;
import org.screenwork.minestomtest.testing.TopDownCommand;
import org.screenwork.minestomtest.commands.gamemode.GamemodeAliasCMD;
import org.screenwork.minestomtest.commands.gamemode.GamemodeCMD;
import org.screenwork.minestomtest.moderationsys.commands.BanCMD;
import org.screenwork.minestomtest.commands.GiveCMD;
import org.screenwork.minestomtest.moderationsys.commands.KickCMD;
import org.screenwork.minestomtest.moderationsys.commands.UnbanCMD;
import org.screenwork.minestomtest.commands.worldmanager.WorldManagerCMD;
import org.screenwork.minestomtest.testing.cake.InstanceCMD;
import org.screenwork.minestomtest.testing.sdqnger.ShowGui;
import org.screenwork.minestomtest.testing.sdqnger.TestGui;
import org.screenwork.minestomtest.testing.sdqnger.VerificationData;
import org.screenwork.minestomtest.testing.sdqnger.VerificationSys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static HashMap<UUID, BanID> banInfo = new HashMap<>();
    public static HashMap<UUID, VerificationData>  verificationDataList = new HashMap<>();

    private final EditEvents EditEvents = new EditEvents();

    public static InstanceContainer instanceContainer;
    public static InstanceContainer instance;

    public static void main(String[] arguments) throws IOException {

        //Server Setup

        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        MinecraftServer.setBrandName("ScreenWork V1");

        //World Setup
        instanceContainer = instanceManager.createInstanceContainer();
        // PolarLoader polar = (new PolarLoader(Path.of("src/main/java/org/screenwork/minestomtest/commands/instances")));
        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(39, 40, Block.DIAMOND_BLOCK));
        instance = MinecraftServer.getInstanceManager().createInstanceContainer(DimensionType.OVERWORLD, new AnvilLoader("src/main/java/org/screenwork/minestomtest/worlds/football"));


        //MineScreen Test
        MineScreen.instance().init();

        setupCommands();
        setupEvents();

        minecraftServer.start("0.0.0.0", 25566);
    }

    private static void setupEvents() {

        new PlayerDisconnect();
        new PlayerChat();
        new PlayerLogin();
        new ItemDrop();
        new ItemPickup();
        new BlockBreak();
        new pack();
        new ServerListPing();
        new EditEvents();

        new Attributes();
        System.out.println("Attributes class instantiated");

        new ItemFrame();
        new Chairs();

        new VerificationData();
        new VerificationSys();

    }

    private static void setupCommands() {

        //Moderator Commands
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

        // Player Commands
        MinecraftServer.getCommandManager().register(new MessageCMD());

        //World Manager
        MinecraftServer.getCommandManager().register(new WorldTP());
        MinecraftServer.getCommandManager().register(new WorldManagerCMD());

        //Mass Block Replacement System
        MinecraftServer.getCommandManager().register(new SetBlockCMD());
        MinecraftServer.getCommandManager().register(new WandCMD());
        MinecraftServer.getCommandManager().register(new UpCMD());

        EditEvents EditEvents = new EditEvents();
        MinecraftServer.getCommandManager().register(new SetCMD(EditEvents));

        //Admin Commands
        MinecraftServer.getCommandManager().register(new StopCMD());
        MinecraftServer.getCommandManager().register(new DisplayCMD());
        MinecraftServer.getCommandManager().register(new TpsCMD());
        MinecraftServer.getCommandManager().register(new ServerStatsCMD());
        MinecraftServer.getCommandManager().register(new RebuildLightCacheCommand());

        //Space
        TopDown topDown = new TopDown();

        MinecraftServer.getCommandManager().register(new Lightspeed());
        MinecraftServer.getCommandManager().register(new ShipCreator());
        MinecraftServer.getCommandManager().register(new TopDownCommand(topDown));

        //Interactive GUI
        MinecraftServer.getCommandManager().register(new ShowGui());

        MinecraftServer.getCommandManager().register(new InstanceCMD());

    }
}
