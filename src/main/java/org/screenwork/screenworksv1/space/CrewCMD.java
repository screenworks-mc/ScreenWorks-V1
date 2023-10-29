package org.screenwork.screenworksv1.space;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;

public class CrewCMD extends Command {

    public CrewCMD() {
        super("crew");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /crew create <crewname>, /crew invite <player>, /crew kick <player>, /crew leave, /crew list");
        });

        addSubcommand(new createCMD());
        addSubcommand(new inviteCMD());
        addSubcommand(new kickCMD());
    }

    static class createCMD extends Command {

        public createCMD() {
            super("create");
            setCondition(Conditions::playerOnly);

            var playerArgument = ArgumentType.String("player");
            playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername()));
                }
            });

            var permissionArgument = ArgumentType.String("permission");

            addSyntax((sender, context) -> {
                // Permissions.assign(Objects.requireNonNull(MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument))), new Permission(String.valueOf(context.get(permissionArgument))));
                sender.sendMessage("Permission " + context.get(permissionArgument) + " assigned to player " + context.get(playerArgument) + ".");
            }, playerArgument, permissionArgument);
        }
    }

    static class inviteCMD extends Command {

        public inviteCMD() {
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
                // Permissions.remove(Objects.requireNonNull(MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument))), new Permission(String.valueOf(context.get(permissionArgument))));
                sender.sendMessage("Permission " + context.get(permissionArgument) + " removed from player " + context.get(playerArgument) + ".");
            }, playerArgument, permissionArgument);
        }
    }

    static class kickCMD extends Command {

        public kickCMD() {
            super("list");
            setCondition(Conditions::playerOnly);

            var playerArgument = ArgumentType.String("player");
            playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername()));
                }
            });

            addSyntax((sender, context) -> {
                // sender.sendMessage("Permissions belonging to " + context.get(playerArgument) + ": " + Permissions.fetch(Objects.requireNonNull(MinecraftServer.getConnectionManager().findPlayer(context.get(playerArgument)))));
            }, playerArgument);
        }
    }
}
