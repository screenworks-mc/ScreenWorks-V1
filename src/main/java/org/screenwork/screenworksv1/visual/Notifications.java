package org.screenwork.screenworksv1.visual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.FrameType;
import net.minestom.server.advancements.notifications.Notification;
import net.minestom.server.advancements.notifications.NotificationCenter;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.screenwork.screenworksv1.Main;

import java.util.Objects;

public class Notifications {
    public Notifications() {
        Notification notification = new Notification(
                Component.text("Hello, Notifications!", NamedTextColor.GREEN),
                FrameType.GOAL,
                ItemStack.of(Material.GOLD_INGOT)
        );

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            NotificationCenter.send(notification, event.getPlayer());
        });
    }
}
