package tr4nt.autoplantcrops.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.KeyInputHandler;
import tr4nt.autoplantcrops.event.PlaceBlock;

import java.util.ArrayList;
import java.util.function.Function;

import static tr4nt.autoplantcrops.Utils.Utils.*;

public class Ticker implements ClientTickEvents.StartTick {
    public static ArrayList TaskList = new ArrayList();


    @Override
    public void onStartTick(MinecraftClient client) {
//        if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn) on = false ;
        ArrayList removeQueue = new ArrayList();
        TaskList.forEach((list) -> {
                ArrayList list1 = (ArrayList) list;
                MinecraftClient clientt = (MinecraftClient) list1.get(0);
                BlockHitResult res = (BlockHitResult) list1.get(1);
                long latency = (long) list1.get(2);
                long tick = (long) list1.get(3);
                int savedSlotValue = (int) list1.get(4);
                ItemStack pickStack = (ItemStack) list1.get(5);
                boolean plantMultiple = (boolean) list1.get(6);

//            AutoPlantCropsClient.LOGGER.info(String.valueOf(Long.compare((tick()-(long) list1.get(2)),(long) list1.get(3))));
                if (Long.compare((tick()-tick),latency)==1)
                {
                        switchToItem(client, pickStack);
                    if (getStackName(client.player.getInventory().getStack(client.player.getInventory().selectedSlot)).equals(getStackName(pickStack)))
                    {
                        PlaceBlock.placeSeed(clientt, res, plantMultiple);
                        if (ConfigFile.getValue("switchBackToSlot").getAsBoolean())
                        {
                            switchToSlot(client,savedSlotValue);

                        }
                    }
                    removeQueue.add(list);

                }
            }
        );

        removeQueue.forEach((i)->{TaskList.remove(i);});

    }
}
