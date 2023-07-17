package org.screenwork.minestomtest.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.time.TimeUnit;

import java.util.List;

public class SummonCMD extends Command {

    public SummonCMD() {
        super("summon", "s");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /summon <mob>");

        });

        List<String> entityname = new java.util.ArrayList<>(List.of(new String[]{}));

        for (EntityType value : EntityType.values()) {
            entityname.add(value.name());
        }

        var summonArg = ArgumentType.Word("summon").from(entityname.toArray(new String[0]));

        summonArg.setSuggestionCallback((sender, context, suggestion) -> {
            for (EntityType entity : EntityType.values()) {
                suggestion.addEntry(new SuggestionEntry(entity.name()));
            }
        });

        addSyntax((sender, context) -> {

            final String summonMob = context.get(summonArg);

            Instance instance = sender.asPlayer().getInstance();
            Pos spawnPosition = sender.asPlayer().getPosition();
            EntityCreature entity = new EntityCreature(EntityType.fromNamespaceId(context.get(summonArg)));

            //TODO: delete this part later
            entity.addAIGroup(List.of(
                            new MeleeAttackGoal(entity, 1.6, 20, TimeUnit.SERVER_TICK), // Attack the target
                            new RandomStrollGoal(entity, 20)),
                    List.of(
                            new LastEntityDamagerTarget(entity, 32), // First target the last entity which attacked you
                            new ClosestEntityTarget(entity, 32, entity2 -> entity2 instanceof Player)));
            entity.setInstance(instance, spawnPosition);


            sender.sendMessage(Component.text("You've summoned ").append(Component.text(summonMob, NamedTextColor.GREEN)));

        }, summonArg);



    }


}
