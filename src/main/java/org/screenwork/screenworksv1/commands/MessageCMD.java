package org.screenwork.screenworksv1.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.Suggestion;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MessageCMD extends Command {

    public MessageCMD() {

        super("msg", "message", "tell", "whisper", "w", "m");

        var targetPlayer = ArgumentType.String("target");
        var messageArg = ArgumentType.StringArray("message");

        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /msg <player> <message>"));

        targetPlayer.setSuggestionCallback(this::targetPlayer);
        addSyntax(this::execute, targetPlayer, messageArg);
    }

    private void targetPlayer(@NotNull CommandSender commandSender, @NotNull CommandContext context, @NotNull Suggestion suggestion) {
        Player player = (Player) commandSender;

        for (Player onlinePlayer : player.getInstance().getPlayers()) {
            suggestion.addEntry(new SuggestionEntry(onlinePlayer.getUsername()));
        }
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext context) {

        Player player = (Player) commandSender;

        Player target = MinecraftServer.getConnectionManager().findPlayer(context.get("target"));

        String[] messageArray = context.get("message");
        String message = Arrays.toString(messageArray).replace("[", "").replace("]", "");


        if (target == null) {
            player.sendMessage("That player is not online.");
            return;
        }

        target.sendMessage("[" + player.getUsername() + " -> ] " + message);
        player.sendMessage("[me -> " + target.getUsername() + "] " + message);



    }

}
