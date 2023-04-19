package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
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

import java.util.Optional;

import static tr4nt.autoplantcrops.Utils.Utils.getStackName;
import static tr4nt.autoplantcrops.Utils.Utils.switchToSeed;

public class BlockBreakEvent implements AttackBlockCallback {

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        BlockState cropBlockState = world.getBlockState(pos);
        Block block = cropBlockState.getBlock();

        MinecraftClient client = MinecraftClient.getInstance();
        if ( block instanceof CropBlock || block instanceof StemBlock) {
            CropBlock cropBlock1 = (CropBlock) block;
            int age = cropBlockState.get(cropBlock1.getAgeProperty());
            if (age == cropBlock1.getMaxAge()) {
//                            client.player.();

                switchToSeed(client);
                HitResult hit = client.crosshairTarget;

                if (hit != null)
                {
                    BlockHitResult res = (BlockHitResult) hit;
                    res = new BlockHitResult(res.getPos().subtract(new Vec3d(0,1,0)), res.getSide(), res.getBlockPos().subtract(new Vec3i(0,1,0)), res.isInsideBlock());
//                    if (maybe.getBlockPos().equals(pos.down()))
//                    {
                    PlaceBlock.placeSeed(client, res);

//                    client.options.useKey.setPressed(true);

//                    }



                }
//                        client.player.setVelocityClient(0,0,0);
//                        client.options.attackKey.setPressed(true);


//                        PacketByteBuf buf = PacketByteBufs.create();
//                        buf.writeBlockPos(client.player.getBlockPos());
//                        ClientPlayNetworking.send(ModMessages.breakBlock, buf);
            }

         else
        {
            if ((block instanceof FarmlandBlock || block instanceof CropBlock || block instanceof StemBlock) && getStackName(client.player.getInventory().getStack(client.player.getInventory().selectedSlot)).contains("wheat_seeds"))
            {

                HitResult hit = client.crosshairTarget;
                if (hit != null)
                {
                    BlockHitResult res = (BlockHitResult) hit;
                    res = new BlockHitResult(res.getPos().subtract(new Vec3d(0,1,0)), res.getSide(), res.getBlockPos().subtract(new Vec3i(0,1,0)), res.isInsideBlock());
                    PlaceBlock.placeSeed(client, res);
                }
            }

        }
        }
        return ActionResult.PASS;
    }
}
