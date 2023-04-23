package tr4nt.autoplantcrops.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.PlaceBlock;

import java.util.ArrayList;
import java.util.function.Function;

import static tr4nt.autoplantcrops.Utils.Utils.*;

public class Ticker implements ClientTickEvents.StartTick {
    public static ArrayList TaskList = new ArrayList();


    @Override
    public void onStartTick(MinecraftClient client) {
        ArrayList removeQueue = new ArrayList();
        TaskList.forEach((list) -> {
                ArrayList list1 = (ArrayList) list;
//            AutoPlantCropsClient.LOGGER.info(String.valueOf(Long.compare((tick()-(long) list1.get(2)),(long) list1.get(3))));
                if (Long.compare((tick()-(long) list1.get(2)),(long) list1.get(3))==1)
                {
                    if (getStackName(client.player.getInventory().getStack(client.player.getInventory().selectedSlot)).equals(getStackName((ItemStack) list1.get(5))))
                    {
                        PlaceBlock.placeSeed((MinecraftClient) list1.get(0), (BlockHitResult)list1.get(1));
                        if (ConfigFile.getValue("switchBackToSlot").getAsBoolean())
                        {
                            AutoPlantCropsClient.LOGGER.info(String.valueOf((int) list1.get(4)));
                            switchToSlot((MinecraftClient) list1.get(0),(int) list1.get(4));

                        }
                    }
                    removeQueue.add(list);

                }
            }
        );

        removeQueue.forEach((i)->{TaskList.remove(i);});

    }
}
