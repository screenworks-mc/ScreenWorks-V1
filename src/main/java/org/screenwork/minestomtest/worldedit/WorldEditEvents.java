package org.screenwork.minestomtest.worldedit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorldEditEvents {

    HashMap<Player, Point> pos1 = new HashMap<>();
    HashMap<Player, Point> pos2 = new HashMap<>();

    public WorldEditEvents() {

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        ItemStack wand = ItemStack.builder(Material.WOODEN_AXE)
                .displayName(Component.text("Wand"))
                .build();

        globalEventHandler.addListener(PlayerBlockBreakEvent.class, event -> {
            if (event.getPlayer().getInventory().getItemInMainHand().material().equals(Material.WOODEN_AXE)) {
                event.setCancelled(true);
                Point pos1 = event.getBlockPosition();
                event.getPlayer().sendMessage(Component.text("MBR: Position #1 set to ").append(Component.text("(" + pos1.x() + ", " +pos1.y() + ", " + pos1.z() + ")", NamedTextColor.GREEN)));
            }
        });


        AtomicBoolean hasProcessedRightClick = new AtomicBoolean(false);
        globalEventHandler.addListener(PlayerBlockInteractEvent.class, event -> {
            if (event.getPlayer().getInventory().getItemInMainHand().material().equals(Material.WOODEN_AXE)) {
                if (!hasProcessedRightClick.get()) {
                    event.setCancelled(true);
                    Point pos2 = event.getBlockPosition();
                    event.getPlayer().sendMessage(Component.text("MBR: Position #2 set to ").append(Component.text("(" + pos2.x() + ", " +pos2.y() + ", " + pos2.z() + ")", NamedTextColor.GREEN)));

                    hasProcessedRightClick.set(true);
                } else {
                    hasProcessedRightClick.set(false);
                }
            }
        });


    }

}
