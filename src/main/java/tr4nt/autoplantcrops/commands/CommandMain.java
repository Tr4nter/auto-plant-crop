package tr4nt.autoplantcrops.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import tr4nt.autoplantcrops.config.ConfigFile;

import java.util.ArrayList;
import java.util.HashMap;

import static tr4nt.autoplantcrops.commands.ListCommands.commands;

public class CommandMain {
    private String commandNameBig;


    public CommandMain(String commandName) {
        this.commandNameBig = commandName;
        commands.add(commandName);

    }

    public void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
        {
            fabricClientCommandSourceCommandDispatcher.register(ClientCommandManager.literal(this.commandNameBig).executes(this::run));

        }

    }

    private int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext)
    {
        boolean reversed = !ConfigFile.getValue(this.commandNameBig).getAsBoolean();
        HashMap<String, String> map = new HashMap<>();
        map.put(this.commandNameBig, String.valueOf(reversed));

        ConfigFile.addValue(map, true);
        fabricClientCommandSourceCommandContext.getSource().sendFeedback(Text.literal(this.commandNameBig + " has been switched to " + String.valueOf(reversed)));
        return 1;
    }

}




//    private int run(CommandContext<FabricClientCommandSource> the) {
//
//    }


