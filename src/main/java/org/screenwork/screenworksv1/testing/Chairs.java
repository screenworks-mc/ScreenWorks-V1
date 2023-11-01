package org.screenwork.screenworksv1.testing;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.client.play.ClientSteerVehiclePacket;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;

public class Chairs {

    private final List<Entity> chairs = new ArrayList<>();
    private static Potion INVIS = new Potion(PotionEffect.INVISIBILITY, (byte) 1, 100);

    public Chairs() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockInteractEvent.class, this::onPlayerBlockInteract);
        globalEventHandler.addListener(PlayerMoveEvent.class, this::onPlayerMove);
        globalEventHandler.addListener(PlayerSpawnEvent.class, this::onPlayerSpawn);
        globalEventHandler.addListener(PlayerPacketEvent.class, this::onPlayerPacket);
        globalEventHandler.addListener(PlayerEntityInteractEvent.class, this::onEntityInteract);
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

    private void onEntityInteract(PlayerEntityInteractEvent event) {
        if (!Player.Hand.MAIN.equals(event.getHand())) return;
        Entity entity = event.getTarget();
        if (!isChair(entity)) return;

        float yaw = entity.getPosition().yaw() + 45f;

        entity.setView(yaw, 0);
        for (Entity passenger : entity.getPassengers()) {
            passenger.setView(yaw, 0);
        }
    }

    private boolean isChair(Entity entity) {
        for (Entity chair : chairs) {
            if (entity.getUuid().equals(chair.getUuid())) {
                return true;
            }
        }
        return false;
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
            if (playerPos.distance(chairPos) < 0.5) {
                if (!chair.getPassengers().contains(event.getPlayer())) {
                    chair.addPassenger(player);
                }
            }
        }
    }

    private void spawnChairEntity(Instance instance, Pos position) {
        Entity chairEntity = new Entity(EntityType.SLIME);
        chairEntity.setNoGravity(true);
        chairEntity.getEntityMeta().setInvisible(true);
        chairEntity.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 100, 300));
        chairEntity.setInstance(instance);
        chairEntity.teleport(new Pos(position.x(), position.y() + 0.1, position.z()));
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
