package org.screenwork.screenworksv1.blocks;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.ItemFrameMeta;
import net.minestom.server.event.player.PlayerEntityInteractEvent;

import static org.screenwork.screenworksv1.Main.logger;

public class ItemFrame {

    public ItemFrame() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerEntityInteractEvent.class, event -> {
            EntityType entityType = event.getEntity().getEntityType();
            Player player = event.getPlayer();
            Player.Hand hand = event.getHand();
            if (entityType == EntityType.ITEM_FRAME) {
                Entity entity = event.getEntity();
                if (hand.name() == "OFF") {
                    if (((ItemFrameMeta) entity.getEntityMeta()).getItem() != null) {
                        ((ItemFrameMeta) entity.getEntityMeta()).getRotation().rotateClockwise();
                        logger.debug("Item frame item rotated (" + ((ItemFrameMeta) entity.getEntityMeta()).getItem() + " rotated to " + ((ItemFrameMeta) entity.getEntityMeta()).getRotation() + ")");
                    } else {
                        ((ItemFrameMeta) entity.getEntityMeta()).setItem(player.getItemInMainHand());
                        logger.debug("Item Set to " + player.getItemInMainHand());
                    }
                }
            }
        });
    }
}
