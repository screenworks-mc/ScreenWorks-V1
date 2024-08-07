package org.screenwork.screenworksv1.moderationsys.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class UnbanCMD extends Command {

    public UnbanCMD() {
        super("pardon", "unban");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /pardon <player>, /unban <player>");
        });

        var playerArgument = ArgumentType.String("playertounban");

        playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
            /* for (String string : BanCMD.playerBans) {
                suggestion.addEntry(new SuggestionEntry(string));
            } */
        });

        addSyntax((sender, context) -> {

            final String string = context.get(playerArgument);

            // BanCMD.playerBans.remove(string);
            sender.sendMessage("You just pardoned " + string + ".");


            System.out.println("BAN: " + string + " was just pardoned by " + sender.asPlayer().getUsername());

        }, playerArgument);
    }
}