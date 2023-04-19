package tr4nt.autoplantcrops.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import oshi.hardware.platform.mac.MacNetworkIF;

public class PlaceBlock {

    public static void placeSeed(MinecraftClient client, HitResult hit)
    {
        BlockHitResult temp = (BlockHitResult) hit;
        BlockHitResult res1 = new BlockHitResult(temp.getPos().add(0,0,1), temp.getSide(), temp.getBlockPos().add(new Vec3i(0,0,1)), temp.isInsideBlock());
        BlockHitResult res2 = new BlockHitResult(temp.getPos().subtract(0,0,1), temp.getSide(), temp.getBlockPos().subtract(new Vec3i(0,0,1)), temp.isInsideBlock());
        BlockHitResult res3 = new BlockHitResult(temp.getPos().add(1,0,0), temp.getSide(), temp.getBlockPos().add(new Vec3i(1,0,0)), temp.isInsideBlock());
        BlockHitResult res4 = new BlockHitResult(temp.getPos().subtract(1,0,0), temp.getSide(), temp.getBlockPos().subtract(new Vec3i(1,0,0)), temp.isInsideBlock());
        client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res1);
        client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res2);
        client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res3);
        client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),res4);

        client.interactionManager.interactBlock(client.player,client.player.getActiveHand(),temp);

    }

}
