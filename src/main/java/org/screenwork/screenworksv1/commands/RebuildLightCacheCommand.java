package org.screenwork.screenworksv1.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.LightingChunk;
import org.jetbrains.annotations.NotNull;

public class RebuildLightCacheCommand extends Command {

    //Props to MasterBroNetwork for the fix
    public RebuildLightCacheCommand() {
        super("rebuildlightcache", "relight");
        addSyntax(this::execute);
        setCondition(Conditions::playerOnly);
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        Player player = (Player) commandSender;
        Instance currentPlayerInstance = player.getInstance();

        player.sendMessage(Component.text("Light cache has been rebuilt.", NamedTextColor.YELLOW));

        for (Chunk chunk : currentPlayerInstance.getChunks()) {
            if (chunk instanceof LightingChunk) {
                LightingChunk lightingChunk = (LightingChunk) chunk;
                lightingChunk.sendLighting();
            }
        }

        currentPlayerInstance.setChunkSupplier(LightingChunk::new);
        System.out.println(player.getUsername() + " rebuilt the light cache.");
    }
}