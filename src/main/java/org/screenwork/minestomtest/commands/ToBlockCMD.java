package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class ToBlockCMD extends Command {

    public ToBlockCMD() {
        super("toblock", "to", "jump");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /to");
        });


        addSyntax((sender, context) -> {
            //get block at loc
            final Instance instance = sender.asPlayer().getInstance();
            final Pos coords = Pos.fromPoint(sender.asPlayer().getLineOfSight(256).stream().filter(block -> instance.getBlock(block).equals(Block.AIR)).findFirst().orElse(null));
            System.out.println(coords);

            if (coords != null) {
                sender.asPlayer().teleport(coords);
            } else {
                sender.sendMessage("No block found");
            }

        });
    }
}
