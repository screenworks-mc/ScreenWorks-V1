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
        Pos playerPosition = player.getPosition();

        int numStars = 200; // Increase the number of stars for a denser appearance
        double sphereRadius = 10.0; // Adjust the radius of the sphere
        Random random = new Random();

        for (int i = 0; i < numStars; i++) {
            // Calculate spherical coordinates for each star
            double inclination = Math.acos(2 * random.nextDouble() - 1); // Random inclination angle (0 to pi)
            double azimuth = 2 * Math.PI * random.nextDouble(); // Random azimuth angle (0 to 2*pi)

            // Convert spherical coordinates to Cartesian coordinates
            double x = playerPosition.x() + sphereRadius * Math.sin(inclination) * Math.cos(azimuth);
            double y = playerPosition.y() + sphereRadius * Math.cos(inclination);
            double z = playerPosition.z() + sphereRadius * Math.sin(inclination) * Math.sin(azimuth);

            // Apply random Y offset to distribute stars across different heights
            double yOffset = random.nextDouble() * 4.0; // Random offset between 0 and 4 blocks
            y += yOffset;

            // Randomize shooting star's properties
            int duration = random.nextInt(40) + 20; // Random duration between 20 and 60

            // Calculate motion vector for the shooting star
            double dx = playerPosition.x() - x;
            double dy = playerPosition.y() - y;
            double dz = playerPosition.z() - z;

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            double speed = 0.2; // Adjust the speed for the stars' motion

            double motionX = speed * dx / distance;
            double motionY = speed * dy / distance;
            double motionZ = speed * dz / distance;

            // Adjust the colors for the stars and fading tails
            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();
            float alpha = 1.0f;

            // Spawn particles to create the star and its fading tail
            for (int j = 0; j < duration; j++) {
                double tailX = x - motionX * j * 2;
                double tailY = y - motionY * j * 2;
                double tailZ = z - motionZ * j * 2;

                float tailAlpha = alpha * (1.0f - (float) j / duration); // Fade out the tail

                ParticlePacket starParticle = ParticleCreator.createParticlePacket(Particle.DUST, true,
                        tailX, tailY, tailZ, 0, 0, 0, 1, 0, writer -> {
                            writer.writeFloat(red);
                            writer.writeFloat(green);
                            writer.writeFloat(blue);
                            writer.writeFloat(tailAlpha);
                        });

                player.sendPacketToViewersAndSelf(starParticle);
            }
        }
    }
}
