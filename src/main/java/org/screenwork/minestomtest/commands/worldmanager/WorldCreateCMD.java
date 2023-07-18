package org.screenwork.minestomtest.commands.worldmanager;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WorldCreateCMD extends Command {

        public WorldCreateCMD() {
            super("create");

            setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /worldmanager create <worldName>"));
            var worldNameArg = ArgumentType.String("worldName");

            addSyntax(this::executer, worldNameArg);
        }

    private void executer(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

            Player player = (Player) commandSender;
            String worldName = context.get("worldName");

            MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.builder(NamespaceID.from("screenwork:" + worldName)).build());

            var instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer(Objects.requireNonNull(MinecraftServer
                    .getDimensionTypeManager().getDimension(NamespaceID.from("screenwork:" + worldName))));

            instanceContainer.setGenerator(GenerationUnit::modifier);

            player.setInstance(instanceContainer);

    }


}
