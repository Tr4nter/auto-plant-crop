package tr4nt.autoplantcrops.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import tr4nt.autoplantcrops.AutoPlantCropsClient;

import java.sql.Date;
import java.time.Instant;

public class Utils {
    public static String getStackName(ItemStack stack) {
        return stack.toString().split(" ")[1].strip();
    }

    public static String getBlockName(Block block) {return block.getName().toString().toLowerCase();}
    public static void swapSlots(MinecraftClient client, int sourceSlot, int destSlot) {
        int syncId = client.player.playerScreenHandler.syncId;

        client.interactionManager.clickSlot(syncId, sourceSlot, 0, SlotActionType.PICKUP, client.player);
        client.interactionManager.clickSlot(syncId, destSlot, 0, SlotActionType.PICKUP, client.player);

    }

    public static void switchToItem(MinecraftClient client, ItemStack item) {
        for (int i = 0; i <= 35; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (getStackName(stack).equals(getStackName(item))) {
                if (i <= 8) {
                    client.player.getInventory().selectedSlot = i;
                }
//                } else
//                {
//                    int emptySlot = client.player.getInventory().getEmptySlot();
//                    if (emptySlot == -1 )
//                    {
//                        emptySlot = 0;
//                    }
//
//                    swapSlots(client, i , emptySlot);
//
//                }

                break;
            }
        }
    }

    public static long tick()
    {
        return Date.from(Instant.now()).getTime();
    }

}
