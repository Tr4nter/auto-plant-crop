package tr4nt.autoplantcrops.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.mixin.event.interaction.ServerPlayerInteractionManagerMixin;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;

public class C2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                          PacketByteBuf buf, PacketSender resp)
    {
        BlockPos target = buf.readBlockPos();
        ServerPlayerInteractionManager spi = server.getPlayerInteractionManager(player);
        spi.tryBreakBlock(target);
    }
}
