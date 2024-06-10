package tr4nt.autoplantcrops.mixin;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.event.BlockBreakEvent;
import tr4nt.autoplantcrops.event.KeyInputHandler;

import static tr4nt.autoplantcrops.Utils.Utils.*;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionMixin2
{
    @Shadow @Final private static Logger LOGGER;

    @Shadow @Final private MinecraftClient client;

    @Inject(method="breakBlock", at = @At("TAIL"))
    public void attackBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {

        if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn)return ;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        if (!((client.world.getBlockState(BlockBreakEvent.taggedBlockPos).getBlock() == Blocks.AIR)))
        {

            return ;
        }

        BlockState cropBlockState = BlockBreakEvent.taggedBlockState;
        if (cropBlockState == null) return;
        Block block = cropBlockState.getBlock();
        if ( block instanceof CropBlock || block instanceof CocoaBlock || block instanceof NetherWartBlock) {
            IntProperty ageprop = getAge(block);
            int age = cropBlockState.get(ageprop);
            int max_age = getMaxAge(block);
            if ((ConfigFile.getValue("plantDespiteAge").getAsBoolean() && !ConfigFile.getValue("cancelBreakUnlessAged").getAsBoolean()) || age == max_age) {
//                            client.player.();e

//                HitResult hit = client.crosshairTarget;

//                if (hit != null) {
                int savedSlotValue = client.player.getInventory().selectedSlot;
                ItemStack pickStack = null;
                if (block instanceof CropBlock crop)
                {
                    ItemConvertible fuckthisshit = ((CropBlockMixin) crop).invokeGetSeedsitem();
                    pickStack = new ItemStack(fuckthisshit);
                } else if (block instanceof CocoaBlock)
                {
                    pickStack = new ItemStack(Items.COCOA_BEANS);
                } else if (block instanceof NetherWartBlock)
                {
                    pickStack = new ItemStack(Items.NETHER_WART);
                }
//                switchToItem(client, pickStack);
                if (pickStack == null) return;
                BlockHitResult res = new BlockHitResult(BlockPosToVector3d(pos), Direction.UP,pos, false);

                queuePlacement(client, res, savedSlotValue, pickStack, false, false);

            }

        }
    }


}
