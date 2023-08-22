package org.screenwork.screenworksv1.testing.cake;

import net.minestom.server.MinecraftServer;
import net.minestom.server.ServerProcess;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.world.DimensionType;

import java.io.File;

public class InstanceCMD extends Command {


    public InstanceCMD() {
        super("instance");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /instance import <world> <name>");
        });

        File directoryPath = new File("src/main/java/org/screenwork/minestomtest/worlds");
        File[] subdirectories = directoryPath.listFiles(File::isDirectory);

        var importArg = ArgumentType.Word("import").from("import");
        var worldArg = ArgumentType.Word("world");
        var nameArg = ArgumentType.String("name");

        worldArg.setSuggestionCallback((sender, context, suggestion) -> {
            for (File subdirectory : subdirectories) {
                suggestion.addEntry(new SuggestionEntry(subdirectory.getName()));
            }
        });


        addSyntax((sender, context) -> {

            final String world = context.get(worldArg);
            final String name = context.get(nameArg);
            importInstance(sender, world, name);
        }, importArg, worldArg, nameArg);
    }

    private void importInstance(CommandSender sender, String world, String name) {

        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer(DimensionType.OVERWORLD, new AnvilLoader("src/main/java/org/screenwork/minestomtest/worlds/" + world));
        sender.asPlayer().setInstance(instance);
        sender.sendMessage("World " + name + " has been imported!");
    }
}