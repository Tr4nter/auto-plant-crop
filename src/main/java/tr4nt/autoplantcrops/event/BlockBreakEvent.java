package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;

import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.minecraft.world.World;

import tr4nt.autoplantcrops.config.ConfigFile;

import static tr4nt.autoplantcrops.Utils.Utils.*;


public class BlockBreakEvent implements AttackBlockCallback {
    public static BlockPos taggedBlockPos;
    public static BlockState taggedBlockState;
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {

        if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn)return  ActionResult.PASS;
        taggedBlockPos = pos;
        taggedBlockState = world.getBlockState(pos);
        BlockState cropBlockState = taggedBlockState;
        Block block = cropBlockState.getBlock();
        if ( block instanceof CropBlock || block instanceof CocoaBlock || block instanceof NetherWartBlock) {
            IntProperty ageprop = getAge(block);
            int age = cropBlockState.get(ageprop);
            int max_age = getMaxAge(block);
            if (ConfigFile.getValue("cancelBreakUnlessAged").getAsBoolean() && !ConfigFile.getValue("plantDespiteAge").getAsBoolean() && age<max_age)
            {
               return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;

    }
}
