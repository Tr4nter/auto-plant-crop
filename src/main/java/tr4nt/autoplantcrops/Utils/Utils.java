package tr4nt.autoplantcrops.Utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class Utils {
    public static String getStackName(ItemStack stack) {
        return stack.toString().split(" ")[1].strip();
    }

    public static void swapSlots(MinecraftClient client, int sourceSlot, int destSlot) {
        int syncId = client.player.playerScreenHandler.syncId;

        client.interactionManager.clickSlot(syncId, sourceSlot, 0, SlotActionType.PICKUP, client.player);
        client.interactionManager.clickSlot(syncId, destSlot, 0, SlotActionType.PICKUP, client.player);

    }

    public static void switchToSeed(MinecraftClient client) {
        for (int i = 0; i <= 35; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);

            if (getStackName(stack).equals("wheat_seeds")) {
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
}
