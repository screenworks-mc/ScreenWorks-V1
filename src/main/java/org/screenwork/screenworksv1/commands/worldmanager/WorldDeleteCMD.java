package org.screenwork.screenworksv1.commands.worldmanager;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldDeleteCMD extends Command {

    public WorldDeleteCMD() {
        super("delete");

        var worldNameArg = ArgumentType.String("worldName");

        addSyntax(this::executer, worldNameArg);
    }

    private void executer(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

        Player player = (Player) commandSender;

        MinecraftServer.getInstanceManager().getInstances().stream().filter(instanceContainer -> instanceContainer
                .getDimensionName().replaceAll("minecraft:", "")
                .replaceAll("screenwork:", "")
                .equals(context.get("worldName"))).findFirst().ifPresent(instanceContainer -> MinecraftServer.getInstanceManager().unregisterInstance(instanceContainer));

        player.sendMessage("World deleted.");
    }
}
