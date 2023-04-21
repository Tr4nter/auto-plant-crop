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
import net.minecraft.util.math.Direction;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;


import java.time.Instant;
import java.util.Date;

import static tr4nt.autoplantcrops.Utils.Utils.getStackName;

public class ClientTickHandler implements ClientTickEvents.StartTick {


    @Override
    public void onStartTick(MinecraftClient client) {

        if (client.player != null) {
           BlockState state = client.player.getSteppingBlockState();
           Block block = state.getBlock();
           if (block instanceof FarmlandBlock && ConfigFile.getValue("plantOnWalkOver").getAsBoolean())
           {
               PlayerInventory inv = client.player.getInventory();

               ItemStack item = inv.getStack(inv.selectedSlot);
               Block itemBlock = Block.getBlockFromItem(item.getItem());

               if (itemBlock instanceof CropBlock || itemBlock instanceof StemBlock || itemBlock instanceof AttachedStemBlock)
               {

                   HitResult hit = client.crosshairTarget;
                   if (hit != null)
                   {
                       BlockHitResult res = (BlockHitResult) hit;
                       res = new BlockHitResult(client.player.getSteppingPos().toCenterPos(),Direction.UP, client.player.getSteppingPos(), res.isInsideBlock());

                       client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res);
                   }

               }
           }

        }

    }


}
