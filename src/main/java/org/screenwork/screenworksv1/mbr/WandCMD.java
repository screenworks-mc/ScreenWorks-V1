package org.screenwork.screenworksv1.mbr;

import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class WandCMD extends Command {

    public WandCMD() {
        super("wand");

        setDefaultExecutor((sender, context) -> {
            ItemStack wand = ItemStack.builder(Material.WOODEN_AXE)
                    .displayName(Component.text("Wand"))
                    .build();
            sender.asPlayer().getInventory().addItemStack(wand);
        });
    }
}
