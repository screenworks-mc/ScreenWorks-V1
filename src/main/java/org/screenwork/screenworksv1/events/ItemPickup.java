package org.screenwork.screenworksv1.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.item.ItemStack;

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
