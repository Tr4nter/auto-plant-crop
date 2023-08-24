package tr4nt.autoplantcrops.mixin;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.item.Item;
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
import tr4nt.autoplantcrops.event.PlaceBlock;

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
        if ( block instanceof CropBlock || block instanceof CocoaBlock || block instanceof NetherWartBlock) {
            IntProperty ageprop = getAge(block);
            int age = cropBlockState.get(ageprop);
            int max_age = getMaxAge(block);
            if (ConfigFile.getValue("plantDespiteAge").getAsBoolean() || age == max_age) {
//                            client.player.();e

                HitResult hit = client.crosshairTarget;

                if (hit != null) {
                    int savedSlotValue = client.player.getInventory().selectedSlot;
                    ItemStack pickStack = null;
                    if (block instanceof CropBlock)
                    {
                        pickStack = ((CropBlock) block).getPickStack(client.world, pos, cropBlockState);
                    } else if (block instanceof CocoaBlock)
                    {
                        pickStack = new ItemStack(Items.COCOA_BEANS);
                    } else if (block instanceof NetherWartBlock)
                    {
                        pickStack = new ItemStack(Items.NETHER_WART);
                    }
                    switchToItem(client, pickStack);
                    BlockHitResult res = (BlockHitResult) hit;

                    queuePlacement(client, res, savedSlotValue, pickStack, ConfigFile.getValue("plantMultiple").getAsBoolean());

//                    }
                }
            }

        }


    }

}