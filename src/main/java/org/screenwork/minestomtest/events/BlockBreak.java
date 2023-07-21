package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.time.TimeUnit;

public class BlockBreak {

    public BlockBreak() {

        MinecraftServer.getGlobalEventHandler().addListener(PlayerBlockBreakEvent.class, event -> {
                Block block = event.getBlock();
                Player player = event.getPlayer();
                ItemStack droppedItem = ItemStack.of(block.registry().material());

                ItemEntity itemEntity = new ItemEntity(droppedItem);
                itemEntity.setPickupDelay(500, TimeUnit.MILLISECOND);
                itemEntity.setInstance(player.getInstance());
                itemEntity.teleport(new Pos(event.getBlockPosition().add(.5, .5f, .5)));

                Vec velocity = player.getPosition().direction().mul(1);
                itemEntity.setVelocity(velocity);
        });

    }
}
