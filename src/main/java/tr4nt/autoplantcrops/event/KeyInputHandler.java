package tr4nt.autoplantcrops.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static boolean isOn = true;
    public static final String AutoPlantCrops_KEY_CATEGORY = "key.category.autoplantcrops";
    public static final String AutoPlantCrops_KEY_DISABLE = "key.category.autoplantcrops.disablemod";

    public static KeyBinding disableModKey;

    public static void registerKeyInput()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client->
                isOn = !disableModKey.isPressed());
    }

    public static void register()
    {
        disableModKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                AutoPlantCrops_KEY_DISABLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                AutoPlantCrops_KEY_CATEGORY
        ));
        registerKeyInput();
    }

}
