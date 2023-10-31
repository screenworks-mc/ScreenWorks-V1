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

import java.util.Objects;
import java.util.Set;

public class PermissionCMD extends Command {

    public PermissionCMD() {
        super("p");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /p add <player> <permission>, /p remove <player> <permission>, /p list <player>");
        });

        addSubcommand(new addCMD());
        addSubcommand(new removeCMD());
        addSubcommand(new listCMD());
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
                Player targetPlayer = Objects.requireNonNull(MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument)));
                String permission = String.valueOf(context.get(permissionArgument));

                Permissions.assign(targetPlayer.getUuid(), permission);

                sender.sendMessage("Permission " + permission + " assigned to player " + targetPlayer.getUsername() + ".");
            }, playerArgument, permissionArgument);
        }
    }

    static class removeCMD extends Command {

        public removeCMD() {
            super("remove");
            setCondition(Conditions::playerOnly);

            var playerArgument = ArgumentType.String("player");
            playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername()));
                }
            });

            var permissionArgument = ArgumentType.String("permission");

            addSyntax((sender, context) -> {
                Player targetPlayer = Objects.requireNonNull(MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument)));
                String permission = String.valueOf(context.get(permissionArgument));

                Permissions.remove(targetPlayer.getUuid(), permission);

                sender.sendMessage("Permission " + permission + " removed from player " + targetPlayer.getUsername() + ".");
            }, playerArgument, permissionArgument);
        }
    }

    static class listCMD extends Command {

        public listCMD() {
            super("list");
            setCondition(Conditions::playerOnly);

            var playerArgument = ArgumentType.String("player");
            playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername()));
                }
            });

            addSyntax((sender, context) -> {
                Player targetPlayer = Objects.requireNonNull(MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument)));

                Set<String> permissions = Permissions.fetch(targetPlayer.getUuid());

                sender.sendMessage("Permissions belonging to " + targetPlayer.getUsername() + ": " + permissions);
            }, playerArgument);
        }
    }
}
