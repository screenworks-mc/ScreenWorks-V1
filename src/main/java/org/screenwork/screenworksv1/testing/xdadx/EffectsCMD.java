package org.screenwork.screenworksv1.testing.xdadx;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.potion.PotionType;

import java.util.List;

public class EffectsCMD extends Command {

    public EffectsCMD() {
        super("effect");

        setDefaultExecutor((sender, context) ->
                sender.sendMessage("Usage /effect <effect>"));


        List<String> effectname = new java.util.ArrayList<>(List.of(new String[]{}));

        for (PotionType value : PotionType.values()) {
            effectname.add(value.name());

        }

        var effectArg = ArgumentType.Word("effect").from(effectname.toArray(new String[0]));

        effectArg.setSuggestionCallback((sender, context, suggestion) -> {
            for (PotionType potion : PotionType.values()) {
                suggestion.addEntry(new SuggestionEntry(potion.name()));
            }
        });

        addSyntax((sender, context) -> {

            final String giveEffect = context.get(effectArg);


        });

    }
}
