package org.screenwork.screenworksv1.permissions;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.util.concurrent.atomic.AtomicReference;

public class PermissionCMD extends Command {

    public PermissionCMD() {
        super("p");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /p add <player> <permission>, /p remove <player> <permission>, /p list <player>");
        });

        addSubcommand(new addCMD());
    }

    static class addCMD extends Command {

        public addCMD() {
            super("add");
            setCondition(Conditions::playerOnly);

            var playerArgument = ArgumentType.String("player");
            playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername()));
                }
            });

            var permissionArgument = ArgumentType.String("permission");

            addSyntax((sender, context) -> {
                Permissions.assign(new Player(MinecraftServer.getConnectionManager().findPlayer(String.valueOf(context.get(playerArgument))).getUuid(), String.valueOf(context.get(playerArgument)), MinecraftServer.getConnectionManager().findPlayer(String.valueOf(context.get(playerArgument))).getPlayerConnection()), new Permission(String.valueOf(context.get(permissionArgument))));
                sender.sendMessage("Permission " + context.get(permissionArgument) + " assigned to player " + context.get(playerArgument) + ".");
            }, playerArgument, permissionArgument);
        }
    }
}
