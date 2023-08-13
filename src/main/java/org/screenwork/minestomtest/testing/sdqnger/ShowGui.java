package org.screenwork.minestomtest.testing.sdqnger;

import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;

public class ShowGui extends Command {

    public ShowGui() {
        super("showgui");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("This command is not yet implemented.");
        });

        addSyntax((sender, context) -> {

            ScreenGui gui = TestGui.init();
            sender.asPlayer().setInstance(gui.instance());
            sender.asPlayer().teleport(new Pos(0, 1, 0));
            gui.show(sender.asPlayer());
        });
    }
}
