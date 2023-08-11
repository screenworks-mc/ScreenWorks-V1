package org.screenwork.minestomtest.testing;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;

public class TopDownCommand extends Command {

    private final TopDown topDown;

    public TopDownCommand(TopDown topDown) {
        super("topdown");

        this.topDown = topDown;

        var playerArgument = ArgumentType.Entity("player")
                .onlyPlayers(true)
                .singleEntity(true);

        playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
            for (Player player : sender.asPlayer().getInstance().getPlayers()) {
                suggestion.addEntry(new SuggestionEntry(player.getUsername()));
            }
        });

        addSyntax((sender, context) -> {
            Player target = context.get(playerArgument).findFirstPlayer(sender.asPlayer());
            if (target != null) {
                topDown.toggleTopDownMode(target);
                sender.sendMessage("Toggled topdown mode for " + target.getUsername());
            } else {
                sender.sendMessage("Player not found.");
            }
        }, playerArgument);
    }
}
