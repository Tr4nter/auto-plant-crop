package tr4nt.autoplantcrops.mixin;

import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockMixin {

    @Invoker("getAgeProperty")
    public IntProperty invokeGetAgeProperty();
}
