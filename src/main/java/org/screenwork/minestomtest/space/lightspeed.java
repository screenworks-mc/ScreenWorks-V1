package org.screenwork.minestomtest.space;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.world.DimensionType;

import java.util.Objects;
import java.util.Random;

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

    private void createCustomDimension(Entity player) {
        MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.builder(NamespaceID.from("lightspeed")).build());
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer customInstance = instanceManager.createInstanceContainer(Objects.requireNonNull(MinecraftServer
                .getDimensionTypeManager().getDimension(NamespaceID.from("lightspeed"))));
        customInstance.enableAutoChunkLoad(true);
        customInstance.setGenerator(unit ->
                unit.modifier().fill(Block.AIR));
        MinecraftServer.getInstanceManager().registerInstance(customInstance);

        Pos teleportPosition = new Pos(0, 42, 0);
        player.setInstance(customInstance);
        player.teleport(teleportPosition);

        MinecraftServer.getSchedulerManager().buildTask(() -> spawnStarsAroundPlayer(player))
                .delay(20, TimeUnit.SERVER_TICK)
                .repeat(1, TimeUnit.SERVER_TICK)
                .schedule();
    }

    private void spawnStarsAroundPlayer(Entity player) {
        Random random = new Random();
        Pos playerPosition = player.getPosition();

        int numStars = 100;

        for (int i = 0; i < numStars; i++) {
            double distance = 50.0;
            double angle = random.nextDouble() * Math.PI * 2.0;
            double x = playerPosition.blockX() + distance * Math.cos(angle);
            double y = playerPosition.blockY() + random.nextDouble() * 10.0 - 5.0;
            double z = playerPosition.blockZ() + distance * Math.sin(angle);

            ParticlePacket particle = ParticleCreator.createParticlePacket(Particle.DUST, true, x, y, z, 0f, 0f, 0f, 0, 1, writer -> {
                writer.writeFloat(0.3f);
                writer.writeFloat(0.3f);
                writer.writeFloat(0.3f);
                writer.writeFloat(0.3f);
            });

            player.sendPacketToViewersAndSelf(particle);
        }
    }
}
