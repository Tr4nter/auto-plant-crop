package tr4nt.autoplantcrops.mixin;

import net.minecraft.block.NetherWartBlock;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NetherWartBlock.class)
public interface NetherWartBlockMixin
{
    @Accessor("AGE")
    public IntProperty getAGE();


}
