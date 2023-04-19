package tr4nt.autoplantcrops;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.spongepowered.asm.mixin.Mixin;
import tr4nt.autoplantcrops.event.BlockBreakEvent;
import tr4nt.autoplantcrops.event.ClientTickHandler;
import tr4nt.autoplantcrops.event.KeyInputHandler;
import tr4nt.autoplantcrops.event.PlaceBlockEvent;
import tr4nt.autoplantcrops.networking.ModMessages;

public class AutoPlantCropsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
//        ClientTickEvents.START_CLIENT_TICK.register(new ClientTickHandler());

        ModMessages.registerS2C();
        KeyInputHandler.register();
        AttackBlockCallback.EVENT.register(new BlockBreakEvent());
        UseBlockCallback.EVENT.register(new PlaceBlockEvent());
    }
}
