package net.firefoxsalesman.dungeonslibs.network;

import dev.obscuria.fragmentum.api.network.FragmentumNetworking;
import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.capabilities.soulcaster.SoulCaster;
import net.firefoxsalesman.dungeonslibs.capabilities.soulcaster.SoulCasterHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class UpdateSoulsMessage implements PacketPayload {
	private final float newAmount;
	public static final PacketPayload.Info<UpdateSoulsMessage> INFO = new PacketPayload.Info<>(
			UpdateSoulsMessage.class,
			UpdateSoulsMessage::encode, UpdateSoulsMessage::decode, UpdateSoulsMessage::handle);

	public UpdateSoulsMessage(Player player) {
		this(SoulCasterHelper.getSouls(player));
	}

	public UpdateSoulsMessage(float souls) {
		this.newAmount = souls;
	}

	public static void maybeSend(Entity entity) {
		if (entity instanceof ServerPlayer player)
			FragmentumNetworking.INSTANCE.sendTo(player,
					new UpdateSoulsMessage(SoulCasterHelper.getSouls(player)));
	}

	private static void encode(UpdateSoulsMessage packet, FriendlyByteBuf buf) {
		buf.writeFloat(packet.newAmount);
	}

	private static UpdateSoulsMessage decode(FriendlyByteBuf buf) {
		return new UpdateSoulsMessage(buf.readFloat());
	}

	private static void handle(Player player, UpdateSoulsMessage packet) {
		if (player != null) {
			SoulCaster soulCasterCap = SoulCasterHelper.getSoulCasterCapability(player);
			soulCasterCap.setSouls(packet.newAmount, player);
		}
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
