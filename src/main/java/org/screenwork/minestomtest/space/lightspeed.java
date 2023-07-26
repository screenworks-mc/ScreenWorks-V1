package org.screenwork.minestomtest.space;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.DimensionType;

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
    }
}