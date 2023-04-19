package tr4nt.autoplantcrops;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.BlockBreakEvent;
import tr4nt.autoplantcrops.event.ClientTickHandler;
import tr4nt.autoplantcrops.event.KeyInputHandler;
import tr4nt.autoplantcrops.event.PlaceBlockEvent;
import tr4nt.autoplantcrops.networking.ModMessages;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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





        ClientTickEvents.START_CLIENT_TICK.register(new ClientTickHandler());
        ModMessages.registerS2C();
        KeyInputHandler.register();
        AttackBlockCallback.EVENT.register(new BlockBreakEvent());
        UseBlockCallback.EVENT.register(new PlaceBlockEvent());
    }


}
