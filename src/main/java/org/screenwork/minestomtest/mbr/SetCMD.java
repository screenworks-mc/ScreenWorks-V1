package org.screenwork.minestomtest.mbr;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class SetCMD extends Command {

    private final EditEvents worldEditEvents;

    public SetCMD(EditEvents EditEvents) {
        super("set");

        this.worldEditEvents = EditEvents;

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /set <block>");
        });

        var blockArg = ArgumentType.Word("block");

        blockArg.setSuggestionCallback((sender, context, suggestion) -> {
            for (Block block : Block.values()) {
                suggestion.addEntry(new SuggestionEntry(block.name()));
            }
        });

        addSyntax((sender, context) -> {
            if (!(sender.isPlayer())) {
                sender.sendMessage("Only players can use this command.");
                return;
            }

            final String blockName = context.get(blockArg);

            Player player = sender.asPlayer();
            Point pos1 = EditEvents.getPos1(player);
            Point pos2 = EditEvents.getPos2(player);

            sender.sendMessage("Point 1 is " +  pos1);
            sender.sendMessage("Point 2 is " +  pos2);

            if (pos1 == null || pos2 == null) {
                sender.sendMessage(Component.text("You need to set both positions before using this command.", NamedTextColor.RED));
                return;
            }

            setBlocksInRegion(player, pos1, pos2, Block.fromNamespaceId(blockName));
            sender.sendMessage("BLOCK:" + Block.fromNamespaceId(blockName));

        }, blockArg);
    }

    private void setBlocksInRegion(Player player, Point pos1, Point pos2, Block block) {
        int minX = (int) Math.min(pos1.x(), pos2.x());
        int minY = (int) Math.min(pos1.y(), pos2.y());
        int minZ = (int) Math.min(pos1.z(), pos2.z());
        int maxX = (int) Math.max(pos1.x(), pos2.x());
        int maxY = (int) Math.max(pos1.y(), pos2.y());
        int maxZ = (int) Math.max(pos1.z(), pos2.z());

        Instance instance = player.getInstance();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    instance.setBlock(x, y, z, block);
                }
            }
        }

        player.sendMessage(Component.text("Blocks set in the region between positions ")
                .append(Component.text("(" + pos1.x() + ", " + pos1.y() + ", " + pos1.z() + ") and "))
                .append(Component.text("(" + pos2.x() + ", " + pos2.y() + ", " + pos2.z() + ")")));
    }
}
