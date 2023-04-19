package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.mixin.event.interaction.ServerPlayerInteractionManagerMixin;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.networking.ModMessages;

import java.awt.*;
import java.awt.event.InputEvent;

import static tr4nt.autoplantcrops.Utils.Utils.getStackName;
import static tr4nt.autoplantcrops.event.PlaceBlock.placeSeed;

public class ClientTickHandler implements ClientTickEvents.StartTick {


    @Override
    public void onStartTick(MinecraftClient client) {

        if (client.player != null) {
           BlockState state = client.player.getSteppingBlockState();
           Block block = state.getBlock();
           if (block instanceof FarmlandBlock && AutoPlantCropsClient.conf.plantWhenWalkedOver())
           {
               PlayerInventory inv = client.player.getInventory();
               if (getStackName(inv.getStack(inv.selectedSlot)).equals("wheat_seeds"))
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
