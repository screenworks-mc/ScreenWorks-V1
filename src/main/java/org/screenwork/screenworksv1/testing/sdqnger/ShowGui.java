package org.screenwork.screenworksv1.testing.sdqnger;

import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

public class ShowGui extends Command {

    public ShowGui() {
        super("showgui");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("This command is not yet implemented.");
        });

        addSyntax((sender, context) -> {

            Player player = (Player) sender;

            ScreenGui gui = StartMenuGui.init();

            player.setInstance(gui.instance());
            player.teleport(new Pos(0, 1, 0));

            gui.show(sender.asPlayer());

        });
    }
}
