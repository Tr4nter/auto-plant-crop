package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;
import tr4nt.autoplantcrops.mixin.CropBlockMixin;
import tr4nt.autoplantcrops.scheduler.Ticker;

import java.util.ArrayList;
import java.util.Optional;

import static tr4nt.autoplantcrops.Utils.Utils.*;


public class BlockBreakEvent implements AttackBlockCallback {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {

         if (!ConfigFile.getValue("autoplantcrops").getAsBoolean())return ActionResult.PASS;


        BlockState cropBlockState = world.getBlockState(pos);
        Block block = cropBlockState.getBlock();
        MinecraftClient client = MinecraftClient.getInstance();

        if ( block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock) {
            CropBlock cropBlock1 = (CropBlock) block;
            IntProperty ageprop = ((CropBlockMixin) cropBlock1).invokeGetAgeProperty();
            int age = cropBlockState.get(ageprop);
            if (ConfigFile.getValue("plantDespiteAge").getAsBoolean() || age == cropBlock1.getMaxAge()) {
//                            client.player.();

                HitResult hit = client.crosshairTarget;

                if (hit != null)
                {
                    int savedSlotValue = client.player.getInventory().selectedSlot;
                    ItemStack pickStack =  cropBlock1.getPickStack(world, pos, cropBlockState);
                    switchToItem(client,pickStack);
                    BlockHitResult res = (BlockHitResult) hit;
//                    if (maybe.getBlockPos().equals(pos.down()))
//

                    queuePlacement(client, res, savedSlotValue, pickStack, ConfigFile.getValue("plantMultiple").getAsBoolean());



//                    client.options.useKey.setPressed(true);

//                    }



                }
//                        client.player.setVelocityClient(0,0,0);
//                        client.options.attackKey.setPressed(true);


//                        PacketByteBuf buf = PacketByteBufs.create();
//                        buf.writeBlockPos(client.player.getBlockPos());
//                        ClientPlayNetworking.send(ModMessages.breakBlock, buf);
            }

        }
        return ActionResult.PASS;
    }
}
