package org.screenwork.minestomtest.events;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.LivingEntityMeta;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.NetworkBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;

public class ItemPickup {

    public ItemPickup() {

        MinecraftServer.getGlobalEventHandler().addListener(PickupItemEvent.class, event -> {
            ItemStack itemOnGround = event.getItemStack();
            UUID playerUUID = event.getLivingEntity().getUuid();
            Player player = MinecraftServer.getConnectionManager().getPlayer(playerUUID);
            boolean full = player.getInventory().addItemStack(itemOnGround);
            if (!full) {
                event.setCancelled(true);
            }
        });

    }
}
