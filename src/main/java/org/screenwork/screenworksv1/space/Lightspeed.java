package org.screenwork.screenworksv1.space;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;

public class Lightspeed extends Command {

    public Lightspeed() {
        super("lines");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /lines <player>");
        });

        var playerArg = ArgumentType.String("player");

        playerArg.setSuggestionCallback((sender, context, suggestion) -> {
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                suggestion.addEntry(new SuggestionEntry(player.getUsername()));
            }
        });

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage("Only players can use this command.");
                return;
            }
            Entity player = sender.asPlayer();
            serveParticles(player);
        }, playerArg);
    }

    private void createLine(Player player, int startX, int startY, int startZ, int endX, int endY, int endZ, int particleCount) {
        int particlesPerIteration = 25;
        int iterations = particleCount / particlesPerIteration;

        double deltaX = (endX - startX) / (double) particleCount;
        double deltaY = (endY - startY) / (double) particleCount;
        double deltaZ = (endZ - startZ) / (double) particleCount;

        for (int i = 0; i < iterations; i++) {
            int startIndex = i * particlesPerIteration;
            int endIndex = Math.min((i + 1) * particlesPerIteration, particleCount);

            for (int j = startIndex; j < endIndex; j++) {
                double currentX = startX + j * deltaX;
                double currentY = startY + j * deltaY;
                double currentZ = startZ + j * deltaZ;

                float gradient = (float) j / particleCount;
                float red = 0.0f;
                float green = 0.0f;
                float blue = gradient * 1.0f;
                float alpha = 1.0f;

                ParticlePacket particle = ParticleCreator.createParticlePacket(Particle.DUST, true,
                        currentX, currentY, currentZ, 0f, 0f, 0f, 0, 1, writer -> {
                            writer.writeFloat(red);
                            writer.writeFloat(green);
                            writer.writeFloat(blue);
                            writer.writeFloat(alpha);
                        });
                player.sendPacketToViewersAndSelf(particle);
            }

            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadLines(Player player) {
        createLine(player, 0, 42, 15, 0, 42, 5, 1000);
        createLine(player, 3, 42, 15, 3, 42, 5, 1000);
        createLine(player, -3, 42, 15, -3, 42, 5, 1000);
        createLine(player, 5, 42, 15, 5, 42, 5, 1000);
        createLine(player, -5, 42, 15, -5, 42, 5, 1000);
    }

    private void serveParticles(Entity player) {
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            loadLines((Player) player);
        }).delay(TaskSchedule.tick(20)).schedule();
    }
}