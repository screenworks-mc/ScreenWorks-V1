package org.screenwork.screenworksv1.testing.cake.tycoon;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.network.packet.client.ClientPacket;
import net.minestom.server.network.packet.client.play.ClientPlayerRotationPacket;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.TaskSchedule;

import java.util.HashMap;
import java.util.Random;

public class Tycoon {

    private boolean topDownMode = false; // Initialize topdown mode as false

    public Tycoon() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        SchedulerManager scheduler = MinecraftServer.getSchedulerManager();
        instanceContainer.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 1, Block.BLACK_CONCRETE);
            unit.modifier().fillHeight(2, 10, Block.LIGHT);
        });

        /* globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
                scheduler.buildTask(() -> {
                    topDownMode = true;
                }).delay(TaskSchedule.tick(3)).schedule();
        }); */

        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            if (topDownMode) {
                event.setSpawningInstance(instanceContainer);
                player.setInstance(instanceContainer);
                player.setRespawnPoint(new Pos(0, 0, 0));
                PlayerInventory inventory = player.getInventory();

                Entity camera = new Entity(EntityType.BAT);
                camera.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 1, 100));
                camera.setNoGravity(true);
                camera.setInstance(instanceContainer, new Pos(0, 10, 0));
                camera.setView(0, 90);
                scheduler.buildTask(() -> {
                    player.setNoGravity(true);
                    player.spectate(camera);
                    player.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 100, 300));
                    camera.addPassenger(player);
                    Data.registerPlayer(player);
                }).delay(TaskSchedule.tick(5)).schedule();
            }
        });

        globalEventHandler.addListener(PlayerPacketEvent.class, event -> {
            if (!topDownMode) return;

            ClientPacket packet = event.getPacket();
            Player player = event.getPlayer();
            if (packet instanceof ClientPlayerRotationPacket && Data.isRegistered(player)) {
                ClientPlayerRotationPacket rotationPacket = (ClientPlayerRotationPacket) packet;
                Data playerData = Data.getPlayerData(player);
                Entity cursor = playerData.cursor;
                Pos cursorPos = cursor.getPosition();

                cursor.teleport(new Pos(rotationPacket.yaw()/-8, 1, rotationPacket.pitch()/-8));
                if (cursorPos.blockX() == playerData.targetPos.blockX() && cursorPos.blockZ() == playerData.targetPos.blockZ()) {
                    playerData.createNewTarget();
                }
            }
        });
    }

    public void toggleTopDownMode(Player player) {
        topDownMode = !topDownMode;
        if (topDownMode) {
            player.sendMessage("Topdown mode enabled.");
        } else {
            player.sendMessage("Topdown mode disabled.");
        }
    }
}

class Data {

    public boolean hasJoinedBefore = false;
    public Entity cursor;
    public Pos targetPos = new Pos(0, 0, 0);
    public Instance playerInstance;
    public static Random random = new Random();
    public static HashMap<Player, Data> playerDataMap = new HashMap<>();

    public static Data registerPlayer(Player player) {
        return new Data(player);
    }

    public static Data getPlayerData(Player player) {
        return playerDataMap.get(player);
    }

    public static boolean isRegistered(Player player) {
        return playerDataMap.containsKey(player);
    }

    private Data(Player player) {
        playerDataMap.put(player, this);
        cursor = new Entity(EntityType.LLAMA_SPIT);
        cursor.setNoGravity(true);
        playerInstance = player.getInstance();
        cursor.setInstance(playerInstance, new Pos(0, 1, 0));
        cursor.setView(90,  0);
        createNewTarget();
        System.out.println(cursor);
    }

    public void createNewTarget() {
        playerInstance.setBlock(targetPos, Block.SEA_LANTERN);
        playerInstance.setBlock(targetPos, Block.BLACK_CONCRETE);
        Pos newPos = new Pos(random.nextInt(-5, 5), 0, random.nextInt(-5, 5));
        playerInstance.setBlock(newPos, Block.TARGET.withProperty("power", "15"));
        targetPos = newPos;
    }
}