package tr4nt.autoplantcrops.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.event.PlaceBlock;

import java.util.ArrayList;
import java.util.function.Function;

import static tr4nt.autoplantcrops.Utils.Utils.tick;

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
                    PlaceBlock.placeSeed((MinecraftClient) list1.get(0), (BlockHitResult)list1.get(1));
                    removeQueue.add(list);
                }
            }
        );

        removeQueue.forEach((i)->{TaskList.remove(i);});

    }
}
