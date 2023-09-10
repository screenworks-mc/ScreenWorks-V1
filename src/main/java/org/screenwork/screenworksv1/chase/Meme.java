package org.screenwork.screenworksv1.chase;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.ai.goal.FollowTargetGoal;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.time.TimeUnit;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

public class Meme extends EntityCreature {

    public Meme() {
        super(EntityType.ZOMBIE);
        addAIGroup(
                List.of(new FollowTargetGoal(this, Duration.of(1, TimeUnit.SERVER_TICK)), new MeleeAttackGoal(this, 1.6, 20, TimeUnit.SERVER_TICK), new RandomStrollGoal(this, 20)),
                List.of(new LastEntityDamagerTarget(this, 32),  new ClosestEntityTarget(this, 32, entity -> entity instanceof Player))
        );
    }
}
