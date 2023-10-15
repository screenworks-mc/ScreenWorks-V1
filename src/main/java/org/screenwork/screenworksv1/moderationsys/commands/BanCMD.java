package org.screenwork.screenworksv1.moderationsys.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import org.screenwork.screenworksv1.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class BanCMD extends Command {

    public BanCMD() {
        super("ban");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /ban <player> <reason>");
        });

        var playerArgument = ArgumentType.String("target");
        var reasonArgument = ArgumentType.String("banreason");


        playerArgument.setSuggestionCallback((sender, context, suggestion) -> {
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                suggestion.addEntry(new SuggestionEntry(player.getUsername()));
            }
        });

        reasonArgument.setSuggestionCallback((sender, context, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("\"Illegal Movement Modification\""));
            suggestion.addEntry(new SuggestionEntry("\"Illegal Client Modification\""));
            suggestion.addEntry(new SuggestionEntry("\"Illegal PvP/PvE Modification\""));
            suggestion.addEntry(new SuggestionEntry("\"Punishment evasion\""));
            suggestion.addEntry(new SuggestionEntry("\"XRAY\""));
            suggestion.addEntry(new SuggestionEntry("\"Illegal Combat Modifications\""));
            suggestion.addEntry(new SuggestionEntry("\"Flight\""));
            suggestion.addEntry(new SuggestionEntry("\"Slurs\""));
            suggestion.addEntry(new SuggestionEntry("\"Staff Disrespect\""));
            suggestion.addEntry(new SuggestionEntry("\"DDOS Threats\""));

        });

        addSyntax((sender, context) -> {

            String targetArg = context.get(playerArgument);
            UUID uuid = UUID.fromString(getUUID(targetArg));


            /* if (Main.banInfo.get()) {
                sender.sendMessage("That player is already banned!");
                return;
            } */

            final Player target = MinecraftServer.getConnectionManager().findPlayer(targetArg);

            if (target != null)
                target.kick("You were just banned! Reason: " + context.get(reasonArgument));

            sender.sendMessage("You just banned " + target.getUsername() + " for \"" + context.get(reasonArgument) + "\".");

            // System.out.println("Ban List: " + playerBans);
            System.out.println("BAN: " + target.getUsername() + " was just banned by " + sender.asPlayer().getUsername());

        }, playerArgument, reasonArgument);
    }

    public static String getUUID(String name) {
        String uuid = "";
        try {
            var a = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            // uuid = (((JsonObject)new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            a.close();
        } catch (Exception e) {
            Main.logger.warn("Unable to get UUID of: " + name + "!");
            uuid = "error";
        }
        return uuid;
    }
}