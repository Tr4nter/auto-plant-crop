package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import tr4nt.autoplantcrops.AutoPlantCropsClient;
import tr4nt.autoplantcrops.config.ConfigFile;

import static tr4nt.autoplantcrops.Utils.Utils.*;

public class ClientTickHandler implements ClientTickEvents.StartTick {
    private static long boneMealTick = tick();


    @Override
    public void onStartTick(MinecraftClient client) {

        if (client.player != null) {
            if (!ConfigFile.getValue("autoplantcrops").getAsBoolean() || !KeyInputHandler.isOn)return ;

            BlockState state = client.player.getSteppingBlockState();
           Block block = state.getBlock();

           PlayerInventory inv = client.player.getInventory();
           ItemStack item = inv.getStack(inv.selectedSlot);
           Block itemBlock = Block.getBlockFromItem(item.getItem());

           BlockPos upPos = client.player.getSteppingPos().up();

           BlockState aboveBlockState = client.world.getBlockState(upPos);
           Block blockAbove = aboveBlockState.getBlock();

           boolean onCropBlock = (blockAbove instanceof CropBlock);

           HitResult hit = client.crosshairTarget;

           boolean allowPlace = false;
           boolean hoeing = false;
           boolean bonemealing = false;
           BlockHitResult res = null;
           if (block instanceof FarmlandBlock && !onCropBlock)
            {
                if ((ConfigFile.getValue("plantOnWalkOver").getAsBoolean() && itemBlock instanceof CropBlock) )
                {
                    BlockHitResult tempres = getHit(client);
                    if (tempres != null)
                    {
                        res = new BlockHitResult(BlockPosToVector3d(client.player.getSteppingPos()),Direction.UP, client.player.getSteppingPos(), tempres.isInsideBlock());
                        allowPlace = true;
                    }

                }
            } else if (ConfigFile.getValue("autoBoneMeal").getAsBoolean() && onCropBlock && getStackName(item).equals("bone_meal") )
            {
                BlockHitResult tempres = getHit(client);
                if (tempres != null)
                {
                    res = new BlockHitResult(BlockPosToVector3d(upPos),Direction.UP, upPos, tempres.isInsideBlock());
                    allowPlace = true;
                    bonemealing = true;

                }
            }
//            AutoPlantCropsClient.LOGGER.info(getStackName(item));
            if (ConfigFile.getValue("autoFarmLand").getAsBoolean() && (block instanceof GrassBlock || getBlockName(block).equals("dirt")) && getStackName(item).contains("hoe"))
            {
                BlockHitResult tempres = getHit(client);
                if (tempres != null)
                {
                    res = new BlockHitResult(BlockPosToVector3d(client.player.getSteppingPos()),Direction.UP, client.player.getSteppingPos(), tempres.isInsideBlock());
                    allowPlace = true;
                    hoeing = true;
                }

            }
            if (allowPlace && res != null)
            {
                if (bonemealing || hoeing)
                {
                    int delay = ConfigFile.getValue("boneMealDelay").getAsInt();
                    boolean finishedWaitForDelay = delay > 0 ? (tick() - boneMealTick) >= delay : (tick() - boneMealTick) >= getLatency(client);
                    if (hoeing || finishedWaitForDelay )
                    {
                        boolean multiple = hoeing ? ConfigFile.getValue("farmLandMultiple").getAsBoolean() : ConfigFile.getValue("boneMealMultiple").getAsBoolean();
                        queuePlacement(client, res, client.player.getInventory().selectedSlot, client.player.getInventory().getStack(client.player.getInventory().selectedSlot), multiple);
                        if (!hoeing)  boneMealTick = tick();
                    }

                } else
                {
                    queuePlacement(client, res, client.player.getInventory().selectedSlot, client.player.getInventory().getStack(client.player.getInventory().selectedSlot), ConfigFile.getValue("plantMultiple").getAsBoolean());
                }
            }

        }

    }


}
