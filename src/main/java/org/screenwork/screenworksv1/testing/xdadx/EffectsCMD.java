package org.screenwork.screenworksv1.testing.xdadx;

import net.minestom.server.command.builder.Command;

import java.util.List;

public class EffectsCMD extends Command {

    public EffectsCMD() {
        super("effect");

        setDefaultExecutor((sender, context) ->
                sender.sendMessage("Usage /effect <effect>"));


        List<String> effectname = new java.util.ArrayList<>(List.of(new String[]{}));


    }
}
