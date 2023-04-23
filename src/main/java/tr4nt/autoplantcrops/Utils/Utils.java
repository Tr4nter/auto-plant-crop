package tr4nt.autoplantcrops.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import tr4nt.autoplantcrops.AutoPlantCropsClient;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String getStackName(ItemStack stack) {
        return stack.toString().split(" ")[1].strip();
    }

    public static String getBlockName(Block block)
    {
        return block.getTranslationKey().split("[.]")[2].strip();
    }

    public static void swapSlots(MinecraftClient client, int sourceSlot, int destSlot) {
        int syncId = client.player.playerScreenHandler.syncId;

        client.interactionManager.clickSlot(syncId, sourceSlot, 0, SlotActionType.PICKUP, client.player);
        client.interactionManager.clickSlot(syncId, destSlot, 0, SlotActionType.PICKUP, client.player);

    }

    public static int switchToItem(MinecraftClient client, ItemStack item) {
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
                return i;
            }
        }
        return -1;
    }

    public static void switchToSlot(MinecraftClient client, int slot)
    {
        assert client.player != null;
        client.player.getInventory().selectedSlot = slot;

    }

    public static long tick()
    {
        return Date.from(Instant.now()).getTime();
    }

    public static BlockHitResult getHit(MinecraftClient client)
    {
        HitResult hit = client.crosshairTarget;
        if (hit != null)
        {
            return (BlockHitResult) hit;
        } else {
            return null;
        }
    }

    public static HashMap<String, String> newOption(String s1, String s2) {
        HashMap<String, String> temp = new HashMap<>();
        temp.put(s1,s2);
        return temp;
    }


}
