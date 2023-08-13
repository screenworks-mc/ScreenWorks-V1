package org.screenwork.minestomtest.testing.sdqnger;

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
import org.screenwork.minestomtest.Main;

public class VerificationSys {

    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
    SchedulerManager scheduler = MinecraftServer.getSchedulerManager();

    public VerificationSys() {

        globalEventHandler.addListener(PlayerPacketEvent.class, event -> {

            Player player = event.getPlayer();

            if (!Main.verificationDataList.containsKey(player.getUuid())) return;

            ClientPacket packet = event.getPacket();

            if (packet instanceof ClientPlayerRotationPacket) {

                ClientPlayerRotationPacket rotationPacket = (ClientPlayerRotationPacket) packet;

                VerificationData verificationData = Main.verificationDataList.get(player.getUuid());

                Entity cursor = verificationData.cursor;
                Pos cursorPos = cursor.getPosition();

                cursor.teleport(new Pos(rotationPacket.yaw()/-8, 1, rotationPacket.pitch()/-8));
                if (cursorPos.blockX() == verificationData.targetPos.blockX() && cursorPos.blockZ() == verificationData.targetPos.blockZ()) {

                }

            }});

        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {

            Player player = event.getPlayer();

            //if (!(event.getPlayer().getPermissionLevel() >= 1)) {
            if (true) {

                var instanceContainer = generateWorld();

                event.setSpawningInstance(instanceContainer);

                player.setInstance(instanceContainer);
                player.setRespawnPoint(new Pos(0, 0, 0));

                generateCamera(instanceContainer, player);
                generateCursor(instanceContainer);

            }
        });

    }

    private InstanceContainer generateWorld() {

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 1, Block.BLACK_CONCRETE);
            unit.modifier().fillHeight(2, 10, Block.LIGHT);
        });

        return instanceContainer;
    }

    private void generateCamera(InstanceContainer instanceContainer, Player player) {

        Entity camera = new Entity(EntityType.BAT);
        camera.addEffect(new Potion(PotionEffect.INVISIBILITY, (byte) 1, 100));
        camera.setNoGravity(true);
        camera.setInstance(instanceContainer, new Pos(0, 10, 0));
        camera.setView(0, 90);
        scheduler.buildTask(() -> {
            player.setNoGravity(true);
            player.spectate(camera);
            camera.addPassenger(player);

        }).delay(TaskSchedule.tick(1)).schedule();
    }

    private void generateCursor(InstanceContainer instanceContainer) {

        Entity cursor = new Entity(EntityType.SHULKER);
        cursor.setBoundingBox(0.5f, 0.5f, 0.5f);
        cursor.setNoGravity(true);
        cursor.setCustomNameVisible(true);
        cursor.setInvisible(true);
        cursor.setSilent(true);
        cursor.setGlowing(true);
        cursor.setNoGravity(true);

        cursor.setInstance(instanceContainer, new Pos(0, 1, 0));
        cursor.setView(90,  0);

        System.out.println(cursor);

    }

    private void generateCursorArmourStand(Instance instance, Player player) {
        Entity cursor = new Entity(EntityType.ARMOR_STAND);
        cursor.setInstance(instance);
        cursor.setBoundingBox(0.5f, 0.5f, 0.5f);
        cursor.setNoGravity(true);
        cursor.setCustomNameVisible(true);
        cursor.setInvisible(true);
        cursor.setSilent(true);
        cursor.setGlowing(true);
        cursor.setInstance(instance);
    }

}
