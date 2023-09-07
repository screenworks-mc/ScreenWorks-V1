package org.screenwork.screenworksv1.chase;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.ai.target.LastEntityDamagerTarget;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.entity.pathfinding.NavigableEntity;
import net.minestom.server.entity.pathfinding.Navigator;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import org.screenwork.screenworksv1.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MSummonCMD extends Command {

    public ArrayList<Entity> memeList = new ArrayList<>();

    public MSummonCMD() {

        super("mSummon");

        int min = 0;
        int max = 5;

        addSyntax((sender, context) -> {
            int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
            Entity chaser = new Entity(EntityType.ZOMBIE);
            chaser.setNoGravity(true);
            chaser.getEntityMeta().setInvisible(true);
            chaser.setInstance(sender.asPlayer().getInstance());
            chaser.teleport(new Pos(sender.asPlayer().getPosition().x(), sender.asPlayer().getPosition().y() + 0.1, sender.asPlayer().getPosition().z()));
            ItemStack meme = ItemStack.builder(Material.PAPER).meta(metaBuilder -> {
                metaBuilder.customModelData(randomNum);
                metaBuilder.displayName(Component.text("Meme"));
            }).build();
            Entity chairItemDisplay = new Entity(EntityType.ITEM_DISPLAY);
            ItemDisplayMeta displayMeta = (ItemDisplayMeta) chairItemDisplay.getEntityMeta();
            displayMeta.setItemStack(meme);
            displayMeta.setCustomName(Component.text("Display Item"));
            chaser.addPassenger(chairItemDisplay);
            memeList.add(chaser);
            Navigator runner = new Navigator(chaser);
            runner.setPathTo(sender.asPlayer().getPosition());
        });

    }
}
