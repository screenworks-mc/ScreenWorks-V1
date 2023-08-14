package org.screenwork.screenworksv1.commands.worldmanager;

import net.minestom.server.command.builder.Command;

public class WorldManagerCMD extends Command {

    public WorldManagerCMD() {

        super("worldmanager", "wm");

        addSubcommand(new WorldCreateCMD());
        addSubcommand(new WorldDeleteCMD());
        addSubcommand(new WorldListCMD());
        addSubcommand(new WorldInfo());

    }

}
