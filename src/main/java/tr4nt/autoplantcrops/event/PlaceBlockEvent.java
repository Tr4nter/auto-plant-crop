package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class PlaceBlockEvent implements UseBlockCallback {
    public String getStackName(ItemStack stack)
    {
        return stack.toString().split(" ")[1].strip();
    }


    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (hitResult != null)
        {
            BlockState state = world.getBlockState(hitResult.getBlockPos());
            Block block = state.getBlock();

            PlayerInventory inv = player.getInventory();
            if (getStackName(inv.getStack(inv.selectedSlot)).equals("wheat_seeds"))
            {

                if (block instanceof FarmlandBlock || block instanceof CropBlock || block instanceof StemBlock)
                {

                    MinecraftClient.getInstance().options.useKey.setPressed(false);

                }
            }

        }
        return ActionResult.PASS;
    }
}
