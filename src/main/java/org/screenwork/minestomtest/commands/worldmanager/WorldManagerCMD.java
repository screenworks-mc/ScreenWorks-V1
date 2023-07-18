package org.screenwork.minestomtest.commands.worldmanager;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.screenwork.minestomtest.commands.WorldTP;

public class WorldManagerCMD extends Command {

    public WorldManagerCMD() {

        super("worldmanager", "wm");

      //  var createArg = ArgumentType.Command("create");
     //   var deleteArg = ArgumentType.Command("delete");
      //  var listArg = ArgumentType.Command("list");
       // var tpArg = ArgumentType.Command("tp");

        addSubcommand(new CreateWorldCMD());
        addSubcommand(new DeleteWorldCMD());
        addSubcommand(new WorldListCMD());

    }

}
