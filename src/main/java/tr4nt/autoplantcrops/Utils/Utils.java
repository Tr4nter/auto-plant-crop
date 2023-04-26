package tr4nt.autoplantcrops.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.scheduler.Ticker;

import java.sql.Date;
import java.time.Instant;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (hit instanceof BlockHitResult)
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

    public static long getLatency(MinecraftClient client)
    {
        ServerInfo sinf = client.getCurrentServerEntry();
        if (sinf == null) return 0;
        return sinf.ping;
    }
    public static void queuePlacement(MinecraftClient client, BlockHitResult res, int savedSlotValue, ItemStack pickStack, boolean plantMutliple)
    {
        long latency = getLatency(client);
        ArrayList info = new ArrayList();
        info.add(client);
        info.add(res);
        if (latency > 0)
        {

            info.add(latency+(latency/10)); // extra numbers for a higher chance of success

        }else
        {
            long x = 0;
            info.add(x);

        }
        info.add(tick());
        info.add(savedSlotValue);
        info.add(pickStack);
        info.add(plantMutliple);
        Ticker.TaskList.add(info);
    }

    public static boolean isNumber(String val)
    {
        try {
            Integer.parseInt(val);
            return true;

        } catch(NumberFormatException e)
        {
            return false;
        }
    }

    public static BlockHitResult compareBlockHit(Vec3d pos, Direction side, BlockPos blockPos, boolean insideBlock, BlockHitResult defaultBHR, Block blockToCompare)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        Block block = client.world.getBlockState(blockPos).getBlock();
        if (block.equals(blockToCompare))
        {
            return new BlockHitResult(pos, side, blockPos, insideBlock);
        }
        return defaultBHR;
    }

}
