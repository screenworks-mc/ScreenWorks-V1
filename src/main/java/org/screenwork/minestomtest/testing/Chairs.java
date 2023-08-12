package org.screenwork.minestomtest.testing;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.*;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.play.ClientSteerVehiclePacket;
import net.minestom.server.network.packet.server.play.PlayerPositionAndLookPacket;
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.time.Tick;
import net.minestom.server.utils.time.TimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chairs {

    private final List<Entity> chairs = new ArrayList<>();

    public Chairs() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockInteractEvent.class, this::onPlayerBlockInteract);
        globalEventHandler.addListener(PlayerMoveEvent.class, this::onPlayerMove);
        globalEventHandler.addListener(PlayerSpawnEvent.class, this::onPlayerSpawn);
        globalEventHandler.addListener(PlayerPacketEvent.class, this::onPlayerPacket);
    }

    private void onPlayerPacket(PlayerPacketEvent event) {
        Player player = event.getPlayer();
        if (event.getPacket() instanceof ClientSteerVehiclePacket packet) {
            if ((packet.flags() & 0b10) != 0) {
                for (Entity chair : chairs) {
                    if (chair.getPassengers().contains(player)) {
                        chair.removePassenger(player);
                        Pos standingPos = new Pos(player.getPosition().x() + 0.5, player.getPosition().y(), player.getPosition().z() + 0.5);
                        player.teleport(standingPos);
                        player.facePosition(Player.FacePoint.FEET, new Pos(chair.getPosition().x() + 0, chair.getPosition().y() - 1, chair.getPosition().z() + 0));
                        player.sendMessage("You stand up from the chair.");
                    }
                }
            }
        }
    }

    private void onPlayerSpawn(PlayerSpawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().addItemStack(ItemStack.builder(Material.PAPER).meta(metaBuilder -> {
            metaBuilder.customModelData(69420);
            metaBuilder.displayName(Component.text("Chair"));
        }).build());
    }

    private void onPlayerBlockInteract(PlayerBlockInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInMainHand();
        if (itemStack.material() == Material.PAPER && itemStack.getMeta().getCustomModelData() == 69420) {
            if (event.getHand() == Player.Hand.MAIN) {
                Instance instance = player.getInstance();
                if (instance != null) {
                    Pos spawnPos = new Pos(event.getBlockPosition().x() + 0.5, event.getBlockPosition().y() + 1, event.getBlockPosition().z() + 0.5);
                    spawnChairEntity(instance, spawnPos);
                }
            }
        }
    }


    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Pos playerPos = player.getPosition();

        for (Entity chair : chairs) {
            Pos chairPos = chair.getPosition();
            if (playerPos.distance(chairPos) < 0.1) {
                if (!chair.getPassengers().contains(event.getPlayer())) {
                    chair.addPassenger(player);
                    player.sendMessage("You are sitting in a chair.");
                }
            }
        }
    }

    private void spawnChairEntity(Instance instance, Pos position) {
        Entity chairEntity = new Entity(EntityType.MINECART);
        chairEntity.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 1, 30));
        chairEntity.setInstance(instance);
        chairEntity.teleport(position);
        ItemStack chairItem = ItemStack.builder(Material.PAPER).meta(metaBuilder -> {
            metaBuilder.customModelData(69420);
            metaBuilder.displayName(Component.text("Chair"));
        }).build();
        Entity chairItemDisplay = new Entity(EntityType.ITEM_DISPLAY);
        ItemDisplayMeta displayMeta = (ItemDisplayMeta) chairItemDisplay.getEntityMeta();
        displayMeta.setItemStack(chairItem);
        displayMeta.setCustomName(Component.text("Chair Display"));
        chairEntity.addPassenger(chairItemDisplay);
        chairs.add(chairEntity);
    }
}
