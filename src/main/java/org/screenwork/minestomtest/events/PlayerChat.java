package org.screenwork.minestomtest.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.screenwork.minestomtest.Main;

import static org.screenwork.minestomtest.Main.instanceContainer;

public class PlayerChat {

    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

    public PlayerChat() {

        globalEventHandler.addListener(PlayerChatEvent.class, event -> Main.logger.info("<" + event.getPlayer().getUsername() + "> " + event.getMessage()));

        globalEventHandler.addListener(PlayerChatEvent.class, event -> {
            if ((event.getMessage().startsWith("save"))) {
                instanceContainer.saveChunksToStorage();
            } else if ((event.getMessage().startsWith("paper"))) {
                event.getPlayer().getInventory().addItemStack(ItemStack.builder(Material.PAPER).meta(builder -> builder.customModelData(69420)).build());
                EntityCreature entity = new EntityCreature(EntityType.ITEM_FRAME);
                entity.setInstance(instanceContainer);
                entity.teleport(new Pos(0, 42, 0));
            }
        });
    }
}
