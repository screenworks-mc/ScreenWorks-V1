package org.screenwork.screenworksv1.commands.worldmanager;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
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
        var worldTypeArg = ArgumentType.Word("worldType").from("void", "flat").setDefaultValue("void");

        addSyntax(this::executer, worldNameArg, worldTypeArg);
    }

    private void executer(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

        Player player = (Player) commandSender;
        String worldName = context.get("worldName");
        String worldType = context.get("worldType");

        MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.builder(NamespaceID.from("screenwork:" + worldName)).build());

        var instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer(Objects.requireNonNull(MinecraftServer
                .getDimensionTypeManager().getDimension(NamespaceID.from("screenwork:" + worldName))));

        switch (worldType) {
            case "void" -> instanceContainer.setGenerator(GenerationUnit::modifier);

            case "flat" -> instanceContainer.setGenerator(unit -> {
                final Point start = unit.absoluteStart();
                final Point size = unit.size();

                for (int y = -64; y <= -60; y++) {
                    for (int x = 0; x < size.blockX(); x++) {
                        for (int z = 0; z < size.blockZ(); z++) {
                            unit.modifier().setBlock(start.add(x, y - start.blockY(), z), Block.BEDROCK);
                        }
                    }
                }

                for (int y = -63; y <= -61; y++) {
                    for (int x = 0; x < size.blockX(); x++) {
                        for (int z = 0; z < size.blockZ(); z++) {
                            unit.modifier().setBlock(start.add(x, y - start.blockY(), z), Block.DIRT);
                        }
                    }
                }

                for (int x = 0; x < size.blockX(); x++) {
                    for (int z = 0; z < size.blockZ(); z++) {
                        unit.modifier().setBlock(start.add(x, -60 - start.blockY(), z), Block.GRASS_BLOCK);
                    }
                }


            });
        }

        player.setInstance(instanceContainer);
        instanceContainer.setChunkSupplier(LightingChunk::new);
        player.sendMessage("World created.");
        instanceContainer.saveChunksToStorage();

    }
}
