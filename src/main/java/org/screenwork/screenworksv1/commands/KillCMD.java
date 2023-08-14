package org.screenwork.screenworksv1.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.instance.Instance;

public class KillCMD extends Command {

    public KillCMD() {
        super("kill");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Component.text("Usage: /kill <player>, /kill <entitytype>"));
        });

        var arg1 = ArgumentType.Word("player/entitytype");

        addSyntax((sender, context) -> {
            final String target = context.get(arg1);

            if (!target.isEmpty()) {
                if (target.equalsIgnoreCase("players")) {
                    Instance instance = sender.asPlayer().getInstance();
                    for (Player player : instance.getPlayers()) {
                        killEntity(player);
                    }
                    sender.sendMessage(Component.text("All players were just absolutely ANNIHILATED.", NamedTextColor.DARK_RED));
                } else {
                    Player player = MinecraftServer.getConnectionManager().findPlayer(target);
                    if (player != null) {
                        killEntity(player);
                        sender.sendMessage(Component.text(target + " was just absolutely ANNIHILATED.", NamedTextColor.DARK_RED));
                    } else {
                        EntityType entityType = EntityType.fromNamespaceId(target);
                        if (entityType != null) {
                            Instance instance = sender.asPlayer().getInstance();
                            for (Entity entity : instance.getEntities()) {
                                if (entity.getEntityType() == entityType) {
                                    killEntity((LivingEntity) entity);
                                }
                            }
                            sender.sendMessage(Component.text("All " + entityType.name() + " entities have been absolutely ANNIHILATED.", NamedTextColor.DARK_RED));
                        } else {
                            sender.sendMessage(Component.text("That's not a valid entity or player, silly! What were you thinking typing: " + target, NamedTextColor.DARK_RED));
                        }
                    }
                }
            } else {
                sender.sendMessage(Component.text("Usage: /kill <player>, /kill <entitytype>"));
            }
        }, arg1);
    }

    private void killEntity(LivingEntity entity) {
        entity.damage(DamageType.VOID, entity.getMaxHealth());
    }
}
