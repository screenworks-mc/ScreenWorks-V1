package org.screenwork.screenworksv1.chase;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.instance.Instance;
import net.minestom.server.coordinate.Pos;

public class MSummonCMD extends Command {

    public MSummonCMD() {
        super("mSummon");

        setDefaultExecutor((sender, context) -> {
            Instance instance = sender.asPlayer().getInstance();
            Pos position = sender.asPlayer().getPosition();

            Meme meme = Meme.createNewMeme(instance, position);

            meme.setInstance(instance);

            sender.sendMessage(Component.text("Summoned a meme!", NamedTextColor.GREEN));
        });
    }
}
