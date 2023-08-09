package org.screenwork.minestomtest.space;

import net.hollowcube.util.schem.Rotation;
import net.hollowcube.util.schem.SchematicReader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class lightspeed extends Command {

    public lightspeed() {
        super("lightspeed");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /lightspeed <ship>");
        });

        var shipArg = ArgumentType.String("ship");

        shipArg.setSuggestionCallback((sender, context, suggestion) -> suggestion.addEntry(new SuggestionEntry("default")));

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage("Only players can use this command.");
                return;
            }
            Entity player = sender.asPlayer();
            createCustomDimension(player, String.valueOf(shipArg));
            System.out.println("Player ran lightspeed command");
        }, shipArg);
    }

    private void createLine(Player player, int startX, int startY, int startZ, int endX, int endY, int endZ, int particleCount) {
        double deltaX = (endX - startX) / (double) particleCount;
        double deltaY = (endY - startY) / (double) particleCount;
        double deltaZ = (endZ - startZ) / (double) particleCount;

        for (int i = 0; i < particleCount; i++) {
            double currentX = startX + i * deltaX;
            double currentY = startY + i * deltaY;
            double currentZ = startZ + i * deltaZ;

            ParticlePacket particle = ParticleCreator.createParticlePacket(Particle.DUST, true,
                    currentX, currentY, currentZ, 0f, 0f, 0f, 0, 1, writer -> {
                        writer.writeFloat(0.7f);
                        writer.writeFloat(0.7f);
                        writer.writeFloat(1.0f);
                        writer.writeFloat(1.0f);
                    });
            player.sendPacketToViewersAndSelf(particle);
        }
    }

    public void loadLines(Player player) {
        createLine(player, 0, 42, 0, 10, 42, 10, 1000);
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
        MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.builder(NamespaceID.from("lightspeed")).build());
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer customInstance = instanceManager.createInstanceContainer(Objects.requireNonNull(MinecraftServer
                .getDimensionTypeManager().getDimension(NamespaceID.from("lightspeed"))));
        customInstance.enableAutoChunkLoad(true);
        customInstance.setGenerator(unit ->
                unit.modifier().fill(Block.AIR));
        MinecraftServer.getInstanceManager().registerInstance(customInstance);

        Pos teleportPosition = new Pos(0.5, 43, 0.5);
        player.setInstance(customInstance);
        player.teleport(teleportPosition);

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            loadLines((Player) player);
            createShip(ship, customInstance);
            player.teleport(teleportPosition);
            // 20 ticks = 1 second
        }).delay(TaskSchedule.tick(100)).schedule();
    }
}