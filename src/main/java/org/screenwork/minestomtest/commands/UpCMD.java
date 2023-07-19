package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class UpCMD extends Command {

    public UpCMD() {
        super("up");

        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /up <amount>"));

        var amountArg = ArgumentType.Integer("amount");

        addSyntax(this::execute, amountArg);

    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

        Player player = (Player) commandSender;

        int amount = context.get("amount");

        var pos = player.getPosition();
        var block = pos.add(0, amount, 0);

        player.getInstance().setBlock(block, Block.GLASS);
        player.teleport(block.add(0, 1, 0));


    }

}
