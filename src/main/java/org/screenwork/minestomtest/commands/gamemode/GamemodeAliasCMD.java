package org.screenwork.minestomtest.commands.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.GameMode;

public class GamemodeAliasCMD extends Command {

    public GamemodeAliasCMD() {
        super("gmc", "gms", "gma", "gmsp");




        addSyntax((sender, context) -> {

            switch (context.getCommandName()) {
                case "gmc" -> {
                    sender.sendMessage(Component.text("Gamemode updated to ").append(Component.text(GameMode.CREATIVE.name(), NamedTextColor.GREEN)));
                    sender.asPlayer().setGameMode(GameMode.CREATIVE);
                }
                case "gms" -> {
                    sender.sendMessage(Component.text("Gamemode updated to ").append(Component.text(GameMode.SURVIVAL.name(), NamedTextColor.GREEN)));
                    sender.asPlayer().setGameMode(GameMode.SURVIVAL);
                }
                case "gma" -> {
                    sender.sendMessage(Component.text("Gamemode updated to ").append(Component.text(GameMode.ADVENTURE.name(), NamedTextColor.GREEN)));
                    sender.asPlayer().setGameMode(GameMode.ADVENTURE);
                }
                case "gmsp" -> {
                    sender.sendMessage(Component.text("Gamemode updated to ").append(Component.text(GameMode.SPECTATOR.name(), NamedTextColor.GREEN)));
                    sender.asPlayer().setGameMode(GameMode.SPECTATOR);
                }
            }
        });
    }
}
