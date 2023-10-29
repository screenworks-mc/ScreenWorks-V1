package org.screenwork.screenworksv1.visual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.notifications.Notification;
import net.minestom.server.advancements.notifications.NotificationCenter;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.scoreboard.Sidebar;

public class Scoreboard {
    public Scoreboard() {
        Sidebar sidebar = new Sidebar(Component.text("Title", NamedTextColor.GREEN));

        Sidebar.ScoreboardLine line = new Sidebar.ScoreboardLine(
                "some_line_0",
                Component.text("Hello, Sidebar!", NamedTextColor.RED),
                0
        );

        sidebar.createLine(line);

        MinecraftServer.getGlobalEventHandler().addListener(PlayerSpawnEvent.class, event -> {
            sidebar.addViewer(event.getPlayer());
        });
    }
}
