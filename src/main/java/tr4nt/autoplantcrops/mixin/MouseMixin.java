package tr4nt.autoplantcrops.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mouse.class)
public interface MouseMixin {
    @Accessor("x")
    public void setX(double mouseX);

    @Accessor("y")
    public void setY(double mouseY);


}