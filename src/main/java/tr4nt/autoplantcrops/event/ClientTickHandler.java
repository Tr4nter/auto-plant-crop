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
import tr4nt.autoplantcrops.networking.ModMessages;

import java.awt.*;
import java.awt.event.InputEvent;

public class ClientTickHandler implements ClientTickEvents.StartTick {
    private ClientPlayerInteractionManager cpi;

    public String getStackName(ItemStack stack)
    {
        return stack.toString().split(" ")[1].strip();
    }

    @Override
    public void onStartTick(MinecraftClient client) {

        if (client.player != null) {
            HitResult hit = client.crosshairTarget;
            if (hit != null)
            {
                try{
                    BlockHitResult res = (BlockHitResult) hit;
                    res = new BlockHitResult(res.getPos().subtract(new Vec3d(0,1,0)), res.getSide(), res.getBlockPos().subtract(new Vec3i(0,1,0)), res.isInsideBlock());
                    BlockHitResult blockHitResult = (BlockHitResult) hit;
                    BlockState state = client.world.getBlockState(blockHitResult.getBlockPos());
                    Block block = state.getBlock();
                    if ( block instanceof CropBlock || block instanceof StemBlock ) {

                        CropBlock cropBlock1 = (CropBlock) block;

                        int age = state.get(cropBlock1.getAgeProperty());
                        if (age == cropBlock1.getMaxAge()) {

                            PlaceBlock.placeSeed(client, hit);


//                        client.player.setVelocityClient(0,0,0);
//                        client.options.attackKey.setPressed(true);


//                        PacketByteBuf buf = PacketByteBufs.create();
//                        buf.writeBlockPos(client.player.getBlockPos());
//                        ClientPlayNetworking.send(ModMessages.breakBlock, buf);
                        }
                    }

                }catch(ClassCastException e){}




            }

        }

    }


}
