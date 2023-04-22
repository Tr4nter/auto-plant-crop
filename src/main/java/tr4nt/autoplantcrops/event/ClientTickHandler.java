package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;


import java.time.Instant;
import java.util.Date;

import static tr4nt.autoplantcrops.Utils.Utils.getHit;
import static tr4nt.autoplantcrops.Utils.Utils.getStackName;

public class ClientTickHandler implements ClientTickEvents.StartTick {


    @Override
    public void onStartTick(MinecraftClient client) {

        if (client.player != null) {
           BlockState state = client.player.getSteppingBlockState();
           Block block = state.getBlock();

           PlayerInventory inv = client.player.getInventory();
           ItemStack item = inv.getStack(inv.selectedSlot);
           Block itemBlock = Block.getBlockFromItem(item.getItem());

           BlockPos upPos = client.player.getSteppingPos().up();

           BlockState aboveBlockState = client.world.getBlockState(upPos);
           Block blockAbove = aboveBlockState.getBlock();

           boolean onCropBlock = (blockAbove instanceof CropBlock || blockAbove instanceof StemBlock || blockAbove instanceof AttachedStemBlock);

           HitResult hit = client.crosshairTarget;

           boolean allowPlace = false;
           BlockHitResult res = null;
           if (block instanceof FarmlandBlock && !onCropBlock)
            {
                if ((ConfigFile.getValue("plantOnWalkOver").getAsBoolean() && itemBlock instanceof CropBlock || itemBlock instanceof StemBlock || itemBlock instanceof AttachedStemBlock) )
                {
                    BlockHitResult tempres = getHit(client);
                    if (tempres != null)
                    {
                        res = new BlockHitResult(client.player.getSteppingPos().toCenterPos(),Direction.UP, client.player.getSteppingPos(), tempres.isInsideBlock());
                        allowPlace = true;
                    }

                }
            } else if (ConfigFile.getValue("autoBoneMeal").getAsBoolean() && onCropBlock && getStackName(item).equals("bone_meal") )
            {
                BlockHitResult tempres = getHit(client);
                if (tempres != null)
                {
                    res = new BlockHitResult(upPos.toCenterPos(),Direction.UP, upPos, tempres.isInsideBlock());

                }
                allowPlace = true;
            }
            if (allowPlace && res != null)
            {
                client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res);
            }

        }

    }


}
