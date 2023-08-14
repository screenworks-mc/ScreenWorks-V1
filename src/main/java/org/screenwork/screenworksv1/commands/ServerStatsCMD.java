package org.screenwork.screenworksv1.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

import java.text.DecimalFormat;

public class ServerStatsCMD extends Command {

    public ServerStatsCMD() {
        super("stats");


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
            int online = MinecraftServer.getConnectionManager().getOnlinePlayers().size();
            sender.sendMessage("---- Server Stats ----");
            sender.sendMessage("TPS: " + msg);
            sender.sendMessage("Players: " + online);
            sender.sendMessage("Chunk View Distance: " + MinecraftServer.getChunkViewDistance());
            sender.sendMessage("----------------------");
        });
    }
}
