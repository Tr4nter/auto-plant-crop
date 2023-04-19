package tr4nt.autoplantcrops.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import tr4nt.autoplantcrops.networking.packet.C2SPacket;

public class ModMessages {
    public static final Identifier breakBlock = new Identifier("break");

    public static void registerC2S()
    {
        ServerPlayNetworking.registerGlobalReceiver(breakBlock, C2SPacket::receive);
    }
    public static void registerS2C()
    {

    }
}
