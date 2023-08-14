package org.screenwork.screenworksv1.commands.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;

import java.util.Arrays;

public class GamemodeCMD extends Command {

    public GamemodeCMD() {
        super("gamemode", "gm");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /gamemode <gamemode>");
        });

        var gamemodeArgument = ArgumentType.Word("gamemode").from(Arrays.stream(GameMode.values()).map(Enum::toString).map(String::toLowerCase).toArray(String[]::new));

        addSyntax((sender, context) -> {

            final String gamemodeString = context.get(gamemodeArgument);
            final GameMode gameMode = GameMode.valueOf(gamemodeString.toUpperCase());

            sender.sendMessage(Component.text("Gamemode updated to ").append(Component.text(gameMode.name(), NamedTextColor.GREEN)));
            sender.asPlayer().setGameMode(gameMode);
        }, gamemodeArgument);
    }
}
