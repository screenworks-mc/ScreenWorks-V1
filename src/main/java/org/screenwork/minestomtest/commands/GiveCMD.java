package org.screenwork.minestomtest.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.GameMode;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveCMD extends Command {
    public GiveCMD() {
        super("give", "i");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /give <item> <amount>");

        });

        //Cleaning up later
        List<String> itemswithnamespace = new java.util.ArrayList<>(List.of(new String[]{}));
        List<String> items = new java.util.ArrayList<>(List.of(new String[]{}));

        for (Material value : Material.values())
            itemswithnamespace.add(value.name());

        for (Material value : Material.values())
            items.add(value.name().replace("minecraft:", ""));

        items.addAll(itemswithnamespace);

        var giveArgument = ArgumentType.Word("give").from(items.toArray(new String[0]));
        var amountArgument = ArgumentType.Integer("amount").setDefaultValue(1);

        addSyntax((sender, context) -> {

            final String giveItem = context.get(giveArgument);
            final int amount = context.get(amountArgument);

            sender.asPlayer().getInventory().addItemStack(ItemStack.of(Material.fromNamespaceId(giveItem), amount));


            sender.sendMessage(Component.text("You've been given the item ").append(Component.text(giveItem, NamedTextColor.GREEN)));

        }, giveArgument, amountArgument);
    }

}
