package org.screenwork.minestomtest.commands.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.GameMode;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.List;

public class GiveCMD extends Command {


    public GiveCMD() {
        super("give");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /give <item>");

        });

        //Cleaning up later
        List<String> items = new java.util.ArrayList<>(List.of(new String[]{}));

        for (Material value : Material.values()) {
            items.add(value.name());
        }

        var giveArgument = ArgumentType.Word("give").from(items.toArray(new String[0]));

        giveArgument.setSuggestionCallback((sender, context, suggestion) -> {
            for (GameMode gameMode : GameMode.values()) {
                suggestion.addEntry(new SuggestionEntry(gameMode.name()));
            }
        });

        addSyntax((sender, context) -> {

            final String giveItem = context.get(giveArgument);

            sender.asPlayer().getInventory().addItemStack(ItemStack.of(Material.fromNamespaceId(giveItem), 1));

            sender.sendMessage(Component.text("You've been given the item ").append(Component.text(giveItem, NamedTextColor.GREEN)));

        }, giveArgument);
    }
}
