package tr4nt.autoplantcrops.event;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import tr4nt.autoplantcrops.config.ConfigFile;

import static tr4nt.autoplantcrops.Utils.Utils.compareBlockHit;

public class PlaceBlock {

    public static void placeSeed(MinecraftClient client, BlockHitResult hit, boolean plantMultiple)
    {

        if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn)return ;
        BlockHitResult tempThe = new BlockHitResult(hit.getPos(), Direction.UP, hit.getBlockPos(), hit.isInsideBlock());
        Block block = client.world.getBlockState(tempThe.getBlockPos()).getBlock();

        client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,tempThe);

        if (plantMultiple) {
            // sorry not sorry
            BlockHitResult res1 =  compareBlockHit(tempThe.getPos().add(0,0,1), Direction.UP, tempThe.getBlockPos().add(new Vec3i(0,0,1)), tempThe.isInsideBlock(), tempThe, block);
            BlockHitResult res2 =  compareBlockHit(tempThe.getPos().subtract(0,0,1), Direction.UP, tempThe.getBlockPos().subtract(new Vec3i(0,0,1)), tempThe.isInsideBlock(), tempThe, block);
            BlockHitResult res3 =  compareBlockHit(tempThe.getPos().add(1,0,0), Direction.UP, tempThe.getBlockPos().add(new Vec3i(1,0,0)), tempThe.isInsideBlock(), tempThe, block);
            BlockHitResult res4 =  compareBlockHit(tempThe.getPos().subtract(1,0,0), Direction.UP, tempThe.getBlockPos().subtract(new Vec3i(1,0,0)), tempThe.isInsideBlock(), tempThe, block);
            BlockHitResult res5 =  compareBlockHit(tempThe.getPos().subtract(1,0,1), Direction.UP, tempThe.getBlockPos().subtract(new Vec3i(1,0,1)), tempThe.isInsideBlock(), tempThe, block);
            BlockHitResult res6 =  compareBlockHit(res5.getPos().add(2,0,0), Direction.UP, res5.getBlockPos().add(new Vec3i(2,0,0)), res5.isInsideBlock(), tempThe, block);
            BlockHitResult res7 =  compareBlockHit(tempThe.getPos().add(1,0,1), Direction.UP, tempThe.getBlockPos().add(new Vec3i(1,0,1)), tempThe.isInsideBlock(), tempThe, block);
            BlockHitResult res8 =  compareBlockHit(res7.getPos().subtract(2,0,0), Direction.UP, res7.getBlockPos().subtract(new Vec3i(2,0,0)), res7.isInsideBlock(), tempThe, block);


            client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND,res1);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res2);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res3);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res4);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res5);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res6);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res7);
            client.interactionManager.interactBlock(client.player,Hand.MAIN_HAND,res8);



        }



    }

}
