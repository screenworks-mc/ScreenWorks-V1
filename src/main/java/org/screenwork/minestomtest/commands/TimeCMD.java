package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class TimeCMD extends Command {


    public TimeCMD() {
        super("time");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /time <time>");
        });

        var timeArgument = ArgumentType.Integer("timeset");

        addSyntax((sender, context) -> {
            final int time = context.get(timeArgument);
            setTime(sender, time);
        }, timeArgument);
    }

    private void setTime(CommandSender sender, int time) {
        sender.asPlayer().getInstance().setTime(time);
        sender.sendMessage("World time has been set to " + time);
    }
}