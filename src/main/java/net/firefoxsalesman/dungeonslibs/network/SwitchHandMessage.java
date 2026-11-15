package net.firefoxsalesman.dungeonslibs.network;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.combat.DualWieldHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SwitchHandMessage implements PacketPayload {

    public static final PacketPayload.Info<SwitchHandMessage> INFO = new PacketPayload.Info<>(
        SwitchHandMessage.class,
        SwitchHandMessage::encode,
        SwitchHandMessage::decode,
        SwitchHandMessage::handle
    );

    public SwitchHandMessage() {}

    private static void encode(SwitchHandMessage packet, FriendlyByteBuf buf) {
    }

    private static SwitchHandMessage decode(FriendlyByteBuf buf) {
        return new SwitchHandMessage();
    }

    private static void handle(Player player, SwitchHandMessage packet) {
        if (player != null && player instanceof ServerPlayer p) {
            DualWieldHandler.switchHand(p);
        }
    }

    @Override
    public Info<?> info() {
        return INFO;
    }
}

