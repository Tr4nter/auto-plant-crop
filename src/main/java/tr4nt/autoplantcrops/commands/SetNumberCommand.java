package tr4nt.autoplantcrops.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import tr4nt.autoplantcrops.config.ConfigFile;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static tr4nt.autoplantcrops.commands.ListCommands.commands;

public class SetNumberCommand {
    private String commandNameBig;


    public SetNumberCommand(String commandName) {
        this.commandNameBig = commandName;
        commands.add(commandName);

    }

    public void register(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess) {
        {
            fabricClientCommandSourceCommandDispatcher.register(literal(this.commandNameBig).then(argument("delayInTicks", integer()).executes(ctx -> run(ctx, getInteger(ctx, "delayInTicks")))));

        }

    }

    private int run(CommandContext<FabricClientCommandSource> fabricClientCommandSourceCommandContext, int value)
    {
        if (value >= 0)
        {
            HashMap<String, String> map = new HashMap<>();
            map.put(this.commandNameBig, String.valueOf(value));

            ConfigFile.addValue(map, true);
            String feedBack;
            if (value > 0)
            {
                feedBack = "Successfully set delay to "+String.valueOf(value)+"ms";
            } else
            {
                feedBack = "Successfully set delay to depend on latency";
            }
            fabricClientCommandSourceCommandContext.getSource().sendFeedback(Text.literal(feedBack));

        } else
        {
            fabricClientCommandSourceCommandContext.getSource().sendFeedback(Text.literal("Please enter a positive number"));

        }

        return 1;
    }

}




//    private int run(CommandContext<FabricClientCommandSource> the) {
//
//    }


