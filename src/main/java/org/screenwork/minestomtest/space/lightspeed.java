package org.screenwork.minestomtest.space;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
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

import java.util.Objects;

public class lightspeed extends Command {

    public lightspeed() {
        super("lightspeed");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /lightspeed");
        });

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage("Only players can use this command.");
                return;
            }
            Entity player = sender.asPlayer();
            createCustomDimension(player);
            System.out.println("Player ran lightspeed command");
        });
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
                customInstance.setBlock(0, 42, 0, Block.STONE);
                break;
            default:
                break;
        }
    }

    private void createCustomDimension(Entity player) {
        MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.builder(NamespaceID.from("lightspeed")).build());
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer customInstance = instanceManager.createInstanceContainer(Objects.requireNonNull(MinecraftServer
                .getDimensionTypeManager().getDimension(NamespaceID.from("lightspeed"))));
        customInstance.enableAutoChunkLoad(true);
        customInstance.setGenerator(unit ->
                unit.modifier().fill(Block.AIR));
        createShip("default", customInstance);
        MinecraftServer.getInstanceManager().registerInstance(customInstance);

        Pos teleportPosition = new Pos(0.5, 43, 0.5);
        player.setInstance(customInstance);
        player.teleport(teleportPosition);

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            loadLines((Player) player);
            // 20 ticks = 1 second
        }).delay(TaskSchedule.tick(100)).schedule();
    }
}