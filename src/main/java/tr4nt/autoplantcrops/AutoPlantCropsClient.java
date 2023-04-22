package tr4nt.autoplantcrops;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.BlockBreakEvent;
import tr4nt.autoplantcrops.event.ClientTickHandler;

import tr4nt.autoplantcrops.scheduler.Ticker;

import java.util.HashMap;
import java.util.Map;


public class AutoPlantCropsClient implements ClientModInitializer {

//    public static final AutoCropsConf conf = AutoCropsConf.createAndLoad();


    public static final Logger LOGGER = LoggerFactory.getLogger("auto-plant-crops");

    @Override
    public void onInitializeClient() {

        ConfigFile.register("autofarmcropsconf");


        Map<String, String>newVal = new HashMap<String, String>();
        newVal.put("plantMultiple", "false");


        ConfigFile.addValue(newVal, false);
        Map<String, String>newVal1 = new HashMap<String, String>();

        newVal1.put("plantOnWalkOver", "true");
        ConfigFile.addValue(newVal1, false);


        Map<String, String>newVal2 = new HashMap<String, String>();

        newVal2.put("plantDespiteAge", "true");
        ConfigFile.addValue(newVal2, false);

        Map<String, String>newVal3 = new HashMap<String, String>();

        newVal3.put("autoBoneMeal", "true");
        ConfigFile.addValue(newVal3, false);


        ClientTickEvents.START_CLIENT_TICK.register(new ClientTickHandler());
        Ticker ticka = new Ticker();
        ClientTickEvents.START_CLIENT_TICK.register(ticka);

        AttackBlockCallback.EVENT.register(new BlockBreakEvent());
    }


}
