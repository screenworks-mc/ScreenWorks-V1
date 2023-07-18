package org.screenwork.minestomtest.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.command.builder.suggestion.Suggestion;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetBlockCMD extends Command {

    public SetBlockCMD() {
        super("setblock");

        //Sets a block at the specified coordinates
        //Usage: /setblock <x> <y> <z> <block>

        var blockArg = ArgumentType.String("block");

        var xArg = ArgumentType.Integer("x");
        var yArg = ArgumentType.Integer("y");
        var zArg = ArgumentType.Integer("z");

        xArg.setSuggestionCallback((sender, context, suggestion) -> {
            Player player = (Player) sender;
            int x = player.getTargetBlockPosition(5).blockX();
            suggestion.addEntry(new SuggestionEntry(String.valueOf(x)));});

        yArg.setSuggestionCallback((sender, context, suggestion) -> {
            Player player = (Player) sender;
            var x = player.getTargetBlockPosition(5).blockY();
            suggestion.addEntry(new SuggestionEntry(String.valueOf(x)));});

        zArg.setSuggestionCallback((sender, context, suggestion) -> {
            Player player = (Player) sender;
            var x = player.getTargetBlockPosition(5).blockZ();
            suggestion.addEntry(new SuggestionEntry(String.valueOf(x)));});

        addSyntax(this::executer, xArg, yArg, zArg, blockArg);
        setCondition(Conditions::playerOnly);
    }

    private void as(@NotNull CommandSender commandSender, @NotNull CommandContext context, @NotNull Suggestion suggestion) {
    }

    private void executer(@NotNull CommandSender commandSender, @NotNull CommandContext context) {



    }

}
