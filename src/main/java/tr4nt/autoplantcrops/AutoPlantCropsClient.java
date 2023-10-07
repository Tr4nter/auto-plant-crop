package tr4nt.autoplantcrops;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr4nt.autoplantcrops.commands.CommandMain;
import tr4nt.autoplantcrops.commands.ListCommands;
import tr4nt.autoplantcrops.commands.SetNumberCommand;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.BlockBreakEvent;
import tr4nt.autoplantcrops.event.ClientTickHandler;

import tr4nt.autoplantcrops.event.KeyInputHandler;
import tr4nt.autoplantcrops.scheduler.Ticker;

import java.util.ArrayList;
import java.util.Map;

import static tr4nt.autoplantcrops.Utils.Utils.isNumber;
import static tr4nt.autoplantcrops.Utils.Utils.newOption;


public class AutoPlantCropsClient implements ClientModInitializer {

//    public static final AutoCropsConf conf = AutoCropsConf.createAndLoad();


    public static final Logger LOGGER = LoggerFactory.getLogger("auto-plant-crops");



    @Override
    public void onInitializeClient() {
        ConfigFile.register("autofarmcropsconf");
        ArrayList list = new ArrayList();

        list.add(newOption("plantOnWalkOver", "true"));
        list.add(newOption("plantDespiteAge", "true"));
        list.add(newOption("autoBoneMeal", "true"));
        list.add(newOption("plantMultiple", "false"));
        list.add(newOption("autoFarmLand", "true"));
        list.add(newOption("switchBackToSlot", "true"));
        list.add(newOption("farmLandMultiple", "false"));
        list.add(newOption("boneMealMultiple", "false"));
        list.add(newOption("autoplantcrops", "true"));
        list.add(newOption("cancelBreakUnlessAged", "false"));
        list.add(newOption("autoplantcropsDelay", "100"));



        list.forEach((i)->
        {
            Map<String, String> iz = (Map<String,String>) i;
            ConfigFile.addValue(iz, false);
            String name = (String) iz.keySet().toArray()[0];
            String value = (String) iz.values().toArray()[0];
            if (value.equals("true") || value.equals("false"))
            {
                CommandMain com = new CommandMain(name);
                ClientCommandRegistrationCallback.EVENT.register(com::register);
            } else if (isNumber(value)) {
                ClientCommandRegistrationCallback.EVENT.register(new SetNumberCommand(name)::register);

            }

        });

        ClientCommandRegistrationCallback.EVENT.register(new ListCommands()::register);
        ClientTickEvents.START_CLIENT_TICK.register(new ClientTickHandler());
        Ticker ticka = new Ticker();
        ClientTickEvents.START_CLIENT_TICK.register(ticka);

        AttackBlockCallback.EVENT.register(new BlockBreakEvent());
        KeyInputHandler.register();

    }


}
