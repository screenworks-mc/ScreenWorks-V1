package org.screenwork.screenworksv1.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

import java.text.DecimalFormat;

public class HelpCMD extends Command {

    public HelpCMD() {
        super("help");


        addSyntax((sender, context) -> {
            sender.sendMessage(Component.text("--- ScreenWorks Help ---", NamedTextColor.GRAY));
            sender.sendMessage(Component.text("To report a player use /report!", NamedTextColor.GREEN));
            sender.sendMessage(Component.text("For support, open a ticket in our discord /discord", NamedTextColor.GREEN));
            sender.sendMessage(Component.text("For our hub do /hub", NamedTextColor.GREEN));;
            sender.sendMessage(Component.text("For rules do /rules", NamedTextColor.GREEN));
        });
    }
}