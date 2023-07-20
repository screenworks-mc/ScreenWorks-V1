package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.time.TimeUnit;

import java.time.Duration;

public class ItemDrop {

    public ItemDrop() {

        MinecraftServer.getGlobalEventHandler().addListener(ItemDropEvent.class, event -> {
            ItemStack itemInMainHand = event.getPlayer().getItemInMainHand();
            if (!itemInMainHand.isAir()) {
                Player player = event.getPlayer();
                ItemStack droppedItem = event.getItemStack();

                ItemEntity itemEntity = new ItemEntity(droppedItem);
                itemEntity.setPickupDelay(500, TimeUnit.MILLISECOND);
                itemEntity.setInstance(player.getInstance());
                itemEntity.teleport(player.getPosition().add(0, 1.5f, 0));

                Vec velocity = player.getPosition().direction().mul(6);
                itemEntity.setVelocity(velocity);
            }
        });

    }
}
