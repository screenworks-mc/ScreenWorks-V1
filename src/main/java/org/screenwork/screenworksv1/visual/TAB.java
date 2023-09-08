package org.screenwork.screenworksv1.visual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;

public class TAB {
    public TAB() {
        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            final TextComponent header = Component.text()
                    .append(Component.newline())
                    .append(Component.text("ScreenWorks").color(NamedTextColor.DARK_GRAY).decoration(TextDecoration.BOLD, TextDecoration.State.TRUE))
                    .append(Component.newline())
                    .append(Component.text("                                                            "))
                    .build();

            final TextComponent footer = Component.text()
                    .append(Component.newline())
                    .append(Component.text("screenworks.net").color(NamedTextColor.GRAY))
                    .append(Component.newline())
                    .append(Component.text("                                                            "))
                    .build();

            event.getPlayer().sendPlayerListHeaderAndFooter((Component) header, (Component) footer);
        });
    }
}
