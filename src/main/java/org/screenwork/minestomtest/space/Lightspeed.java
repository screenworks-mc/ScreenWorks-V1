package org.screenwork.minestomtest.space;

import net.hollowcube.util.schem.Rotation;
import net.hollowcube.util.schem.SchematicReader;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
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
import java.util.Objects;

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

    private void serveParticles(Entity player) {
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            loadLines((Player) player);
        }).delay(TaskSchedule.tick(20)).schedule();
    }
}