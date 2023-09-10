package org.screenwork.screenworksv1.chase;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.ai.goal.*;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.entity.EntityFinder;
import net.minestom.server.utils.math.IntRange;
import net.minestom.server.utils.time.TimeUnit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Meme extends EntityCreature {

    public Meme() {
        super(EntityType.ZOMBIE);
        initializeAI();
    }

    private void initializeAI() {
        addAIGroup(
                List.of(
                        new FollowTargetGoal(this, Duration.of(1, TimeUnit.SERVER_TICK)),
                        new MeleeAttackGoal(this, 1.6, 20, TimeUnit.SERVER_TICK),
                        new RandomStrollGoal(this, 20)
                ),
                List.of(
                        new LastEntityDamagerTarget(this, 32),
                        new ClosestEntityTarget(this, 32, entity -> entity instanceof Player)
                )
        );
    }

    public static Meme createNewMeme(Instance instance, Pos position) {
        Meme meme = new Meme();
        meme.setInstance(instance);
        meme.teleport(position);
        return meme;
    }

    public static List<Pos> findMemesInRadius(Instance instance, Pos center, double radius) {
        EntityFinder entityFinder = new EntityFinder()
                .setTargetSelector(EntityFinder.TargetSelector.ALL_ENTITIES)
                .setEntitySort(EntityFinder.EntitySort.NEAREST)
                .setStartPosition(center)
                .setDistance(new IntRange(0, (int) radius))
                .setEntity(EntityType.ZOMBIE, EntityFinder.ToggleableType.INCLUDE);

        List<Pos> memeLocations = new ArrayList<>();
        entityFinder.find(instance, null).forEach(entity -> memeLocations.add(entity.getPosition()));
        return memeLocations;
    }


    public void killMeme() {
        this.remove();
    }
}
