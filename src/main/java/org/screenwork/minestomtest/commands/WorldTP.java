package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;

public class WorldTP extends Command {

    public WorldTP() {
        super("worldtp", "wtp");

        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /worldtp <world>"));
        var Argument = ArgumentType.String("world");

        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

        Player player = (Player) commandSender;

        String worldname = context.get("world");

        MinecraftServer.getInstanceManager().getInstances().stream().filter(instance -> instance.getDimensionName().equals(worldname)).findFirst()
                .ifPresentOrElse(instance -> {
            player.setInstance(instance);
            player.sendMessage("You have been teleported to " + worldname);
        }, () -> player.sendMessage("That world does not exist."));

    }

}
