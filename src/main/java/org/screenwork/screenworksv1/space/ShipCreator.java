package org.screenwork.screenworksv1.space;

import net.hollowcube.util.schem.Rotation;
import net.hollowcube.util.schem.SchematicReader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.TaskSchedule;

import java.nio.file.Path;

public class ShipCreator extends Command {

    public ShipCreator() {
        super("createship");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /createship <ship>");
        });

        var shipArg = ArgumentType.String("ship");

        shipArg.setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("default")));

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage("Only players can use this command.");
                return;
            }
            Entity player = sender.asPlayer();
            String ship = context.get(shipArg);
            createCustomDimension(player, ship);
        }, shipArg);
    }

    public void createShip(String ship, InstanceContainer customInstance) {
        switch (ship) {
            case "default":
                var schem = SchematicReader.read(Path.of("src/main/java/org/screenwork/minestomtest/schematics/falcon.schem"));
                schem.build(Rotation.NONE, null).apply(customInstance, -36, 36, -29, null);
                break;
            default:
                break;
        }
    }

    private void createCustomDimension(Entity player, String ship) {
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer customInstance = instanceManager.createInstanceContainer();
        customInstance.enableAutoChunkLoad(true);
        customInstance.setGenerator(unit ->
                unit.modifier().fill(Block.AIR));
        MinecraftServer.getInstanceManager().registerInstance(customInstance);

        Pos teleportPosition = new Pos(0.5, 43, 0.5);
        player.setInstance(customInstance);
        player.teleport(teleportPosition);

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            createShip(ship, customInstance);
            player.teleport(teleportPosition);
            // 20 ticks = 1 second
        }).delay(TaskSchedule.tick(20)).schedule();
    }
}