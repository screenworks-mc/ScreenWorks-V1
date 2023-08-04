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
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorldEditEvents {

    private final Map<Player, Point> pos1 = new HashMap<>();
    private final Map<Player, Point> pos2 = new HashMap<>();
    private final Map<Player, AtomicBoolean> hasProcessedRightClickMap = new HashMap<>(); // Map to associate the flag with each player

    public WorldEditEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        ItemStack wand = ItemStack.builder(Material.WOODEN_AXE)
                .displayName(Component.text("Wand"))
                .lore(Component.text("Left click to set position #1"))
                .lore(Component.text("Right click to set position #2"))
                .build();

        globalEventHandler.addListener(PlayerBlockBreakEvent.class, event -> {
            if (event.getPlayer().getInventory().getItemInMainHand().material().equals(Material.WOODEN_AXE)) {
                event.setCancelled(true);
                Point position = event.getBlockPosition();
                pos1.put(event.getPlayer(), position);
                event.getPlayer().sendMessage(Component.text("MBR: Position #1 set to ").append(Component.text("(" + position.x() + ", " + position.y() + ", " + position.z() + ")", NamedTextColor.GREEN)));
            }
        });

        globalEventHandler.addListener(PlayerBlockInteractEvent.class, event -> {
            if (event.getPlayer().getInventory().getItemInMainHand().material().equals(Material.WOODEN_AXE)) {
                Player player = event.getPlayer();
                AtomicBoolean hasProcessedRightClick = hasProcessedRightClickMap.computeIfAbsent(player, p -> new AtomicBoolean(false));

                if (!hasProcessedRightClick.get()) {
                    event.setCancelled(true);
                    Point position = event.getBlockPosition();
                    pos2.put(player, position);
                    player.sendMessage(Component.text("MBR: Position #2 set to ").append(Component.text("(" + position.x() + ", " + position.y() + ", " + position.z() + ")", NamedTextColor.GREEN)));

                    hasProcessedRightClick.set(true);
                } else {
                    hasProcessedRightClick.set(false);
                }
            }
        });
    }

    public Point getPos1(Player player) {
        return pos1.get(player);
    }

    public Point getPos2(Player player) {
        return pos2.get(player);
    }
}