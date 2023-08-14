package org.screenwork.screenworksv1.commands;


import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;


public class ToBlockCMD extends Command {

    public ToBlockCMD() {
        super("toblock", "to", "jump");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /to");
        });

        addSyntax((sender, context) -> {
            final Instance instance = sender.asPlayer().getInstance();

            try {
                Point point = sender.asPlayer().getTargetBlockPosition(256);
                point = point.add(0, 1, 0); // Add 1 to the Y axis (so you don't spawn in the block
                Pos cords = new Pos(point, sender.asPlayer().getPosition().yaw(), sender.asPlayer().getPosition().pitch());

                sender.asPlayer().teleport(cords);
            } catch (Exception e) {
                sender.sendMessage("No block found");
            }
        });
    }
}
