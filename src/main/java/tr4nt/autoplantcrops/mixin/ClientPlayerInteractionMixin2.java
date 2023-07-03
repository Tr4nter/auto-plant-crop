package tr4nt.autoplantcrops.mixin;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tr4nt.autoplantcrops.AutoPlantCrops;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.BlockBreakEvent;
import tr4nt.autoplantcrops.event.KeyInputHandler;

import static tr4nt.autoplantcrops.Utils.Utils.*;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionMixin2
{
    @Inject(method="breakBlock", at = @At("TAIL"))
    public void attackBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn)return ;

        MinecraftClient client = MinecraftClient.getInstance();
        if (!(client.world.getBlockState(BlockBreakEvent.taggedBlockPos).getBlock() == Blocks.AIR))
        {

            return;
        }

        BlockState cropBlockState = BlockBreakEvent.taggedBlockState;
        Block block = cropBlockState.getBlock();
        AutoPlantCrops.LOGGER.info(cropBlockState.toString());
        if ( block instanceof CropBlock || block instanceof CocoaBlock) {
            IntProperty ageprop = getAge(block);
            int age = cropBlockState.get(ageprop);
            int max_age = getMaxAge(block);
            if (ConfigFile.getValue("plantDespiteAge").getAsBoolean() || age == max_age) {
//                            client.player.();e

                HitResult hit = client.crosshairTarget;

                if (hit != null) {
                    int savedSlotValue = client.player.getInventory().selectedSlot;
                    ItemStack pickStack = block instanceof CropBlock ? ((CropBlock) block).getPickStack(client.world, pos, cropBlockState): new ItemStack(Items.COCOA_BEANS);
                    switchToItem(client, pickStack);
                    BlockHitResult res = (BlockHitResult) hit;

                    queuePlacement(client, res, savedSlotValue, pickStack, ConfigFile.getValue("plantMultiple").getAsBoolean());

//                    }
                }
            }

        }


    }

}
