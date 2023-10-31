package org.screenwork.screenworksv1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.lan.OpenToLAN;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.DimensionType;
import nl.kiipdevelopment.minescreen.MineScreen;
import org.bson.Document;
import org.screenwork.screenworksv1.blocks.ItemFrame;
import org.screenwork.screenworksv1.chase.MSummonCMD;
import org.screenwork.screenworksv1.commands.*;
import org.screenwork.screenworksv1.mbr.EditEvents;
import org.screenwork.screenworksv1.mbr.SetCMD;
import org.screenwork.screenworksv1.mbr.WandCMD;
import org.screenwork.screenworksv1.permissions.PermissionCMD;
import org.screenwork.screenworksv1.space.Attributes;
import org.screenwork.screenworksv1.space.Lightspeed;
import org.screenwork.screenworksv1.events.*;
import org.screenwork.screenworksv1.moderationsys.profile.BanID;
import org.screenwork.screenworksv1.pack.pack;
import org.screenwork.screenworksv1.space.ShipCreator;
import org.screenwork.screenworksv1.testing.Chairs;
import org.screenwork.screenworksv1.testing.TopDown;
import org.screenwork.screenworksv1.testing.TopDownCommand;
import org.screenwork.screenworksv1.commands.gamemode.GamemodeAliasCMD;
import org.screenwork.screenworksv1.commands.gamemode.GamemodeCMD;
import org.screenwork.screenworksv1.moderationsys.commands.BanCMD;
import org.screenwork.screenworksv1.commands.GiveCMD;
import org.screenwork.screenworksv1.moderationsys.commands.KickCMD;
import org.screenwork.screenworksv1.moderationsys.commands.UnbanCMD;
import org.screenwork.screenworksv1.commands.worldmanager.WorldManagerCMD;
import org.screenwork.screenworksv1.testing.cake.InstanceCMD;
import org.screenwork.screenworksv1.testing.cake.MongoClientConnection;
import org.screenwork.screenworksv1.testing.sdqnger.ShowGui;
import org.screenwork.screenworksv1.testing.sdqnger.VerificationData;
import org.screenwork.screenworksv1.testing.sdqnger.VerificationSys;
import org.screenwork.screenworksv1.testing.cake.tycoon.Tycoon;
import org.screenwork.screenworksv1.visual.Notifications;
import org.screenwork.screenworksv1.visual.Scoreboard;
import org.screenwork.screenworksv1.visual.TAB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
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

        //MongoDB Setup

        // Connection URL;
        // String uri = "";

        /* try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("");
            MongoCollection<Document> collection = database.getCollection("");
        } */

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

        // LAN
        OpenToLAN.open();

        // ImGUI
        // launch();
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

        new ItemFrame();
        new Chairs();

        new VerificationData();
        new VerificationSys();

        new TAB();
        new Notifications();
        new Scoreboard();

        new Tycoon();
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
        MinecraftServer.getCommandManager().register(new HelpCMD());

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

        // Chase
        MinecraftServer.getCommandManager().register(new MSummonCMD());

        // Permissions
        MinecraftServer.getCommandManager().register(new PermissionCMD());
    }
}
