package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import static tr4nt.autoplantcrops.Utils.Utils.switchToSeed;

public class KeyInputHandler {
    public static final  String AutoCropCategory = "key.AutoCropCategory.test";
    public static final String Shout = "key.tr4nt.autoplantcrops.shout";
    public static KeyBinding testKey;

    public static void swapSlots(MinecraftClient client, int sourceSlot, int destSlot){
        int syncId = client.player.playerScreenHandler.syncId;

        client.interactionManager.clickSlot(syncId, sourceSlot, 0, SlotActionType.PICKUP, client.player);
        client.interactionManager.clickSlot(syncId, destSlot, 0, SlotActionType.PICKUP, client.player);

    }

    public static void registerKeyInput()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (testKey.wasPressed())
            {
                HitResult hit = client.crosshairTarget;
                if (hit != null)
                {
                    BlockHitResult res = (BlockHitResult) hit;
                    BlockState state = client.world.getBlockState(res.getBlockPos());
                    Block block = state.getBlock();
                    if (block instanceof FarmlandBlock)
                    {
                        switchToSeed(client);
//                        client.options.useKey.setPressed(true);
                        PlaceBlock.placeSeed(client, res);

                    }
                }


            }



        });
    }
    public static void register()
    {
        testKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Shout,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                AutoCropCategory
        ));
        registerKeyInput();
    }
}
