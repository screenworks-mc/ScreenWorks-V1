package org.screenwork.minestomtest.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TpsCMD extends Command {

    public TpsCMD() {
        super("tps");


        addSyntax((sender, context) -> {
            double tps = MinecraftServer.TICK_PER_SECOND;
            DecimalFormat decimalFormat = new DecimalFormat("##.##");
            tps = Double.parseDouble(decimalFormat.format(tps));
            String msg;
            if (tps > 20.0) {
                msg = "*20";
            } else {
                msg = String.valueOf(tps);
            }
            sender.sendMessage("TPS: " + msg);
        });

    }

}
