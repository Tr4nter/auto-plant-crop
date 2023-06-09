package tr4nt.autoplantcrops.Utils;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import tr4nt.autoplantcrops.mixin.CocoaBlockMixin;
import tr4nt.autoplantcrops.mixin.CropBlockMixin;
import tr4nt.autoplantcrops.scheduler.Ticker;

import java.sql.Date;
import java.time.Instant;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
    public static String getStackName(ItemStack stack) {
        return stack.toString().split(" ")[1].strip();
    }

    public static String getBlockName(Block block) {
        return block.getTranslationKey().split("[.]")[2].strip();
    }


    public static void swapSlots(MinecraftClient client, int sourceSlot, int destSlot) {

        client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, destSlot, sourceSlot, SlotActionType.SWAP, client.player);
//        client.interactionManager.clickSlot(syncId, destSlot, 0, SlotActionType.PICKUP, client.player);

    }

    public static Vec3d BlockPosToVector3d(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static void switchToItem(MinecraftClient client, ItemStack item) {

        for (int i = 0; i < 36; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (getStackName(stack).equals(getStackName(item))) {
                if (i <= 8) {
                    client.player.getInventory().selectedSlot = i;
                    return;
                } else {
                    int emptySlot = client.player.getInventory().getEmptySlot();
                    if (emptySlot == -1) {
                        emptySlot = 0;
                    }
                    if (emptySlot > 8) return;
                    if (client.isOnThread()) {
                        InventoryScreen newInven = new InventoryScreen(client.player);
                        client.setScreenAndRender(newInven);
                        client.interactionManager.clickSlot(0, i, 0, SlotActionType.QUICK_MOVE, client.player);

                        client.player.closeHandledScreen();
                        client.player.getInventory().selectedSlot = emptySlot;

                        return;
                    }


                }


            }

        }
    }


    public static void switchToSlot(MinecraftClient client, int slot) {
        assert client.player != null;
        client.player.getInventory().selectedSlot = slot;

    }

    public static long tick() {
        return Date.from(Instant.now()).getTime();
    }

    public static BlockHitResult getHit(MinecraftClient client) {
        HitResult hit = client.crosshairTarget;
        if (hit instanceof BlockHitResult) {
            return (BlockHitResult) hit;
        } else {
            return null;
        }
    }

    public static HashMap<String, String> newOption(String s1, String s2) {
        HashMap<String, String> temp = new HashMap<>();
        temp.put(s1, s2);
        return temp;
    }

    public static long getLatency(MinecraftClient client) {
        ServerInfo sinf = client.getCurrentServerEntry();
        if (sinf == null) return 0;
        return sinf.ping;
    }

    public static void queuePlacement(MinecraftClient client, BlockHitResult res, int savedSlotValue, ItemStack pickStack, boolean plantMutliple) {
        long latency = getLatency(client);
        ArrayList info = new ArrayList();
        info.add(client);
        info.add(res);
        if (latency > 0) {

            info.add(latency + (latency / 10)); // extra numbers for a higher chance of success

        } else {
            long x = 0;
            info.add(x);

        }
        info.add(tick());
        info.add(savedSlotValue);
        info.add(pickStack);
        info.add(plantMutliple);
        Ticker.TaskList.add(info);
    }

    public static boolean isNumber(String val) {
        try {
            Integer.parseInt(val);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static BlockHitResult compareBlockHit(Vec3d pos, Direction side, BlockPos blockPos, boolean insideBlock, BlockHitResult defaultBHR, Block blockToCompare) {
        MinecraftClient client = MinecraftClient.getInstance();
        Block block = client.world.getBlockState(blockPos).getBlock();
        if (block.equals(blockToCompare)) {
            return new BlockHitResult(pos, side, blockPos, insideBlock);
        }
        return defaultBHR;
    }

    public static IntProperty getAge(Block block)
    {
        return block instanceof CropBlock ? ((CropBlockMixin) block).invokeGetAgeProperty() : ((CocoaBlockMixin) block).getAGE();
    }

    public static int getMaxAge(Block block)
    {
        return block instanceof CropBlock ? ((CropBlock) block).getMaxAge() : ((CocoaBlockMixin) block).getMaxAge();
    }
}
