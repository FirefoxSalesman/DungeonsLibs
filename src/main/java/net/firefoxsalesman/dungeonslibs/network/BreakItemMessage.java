package net.firefoxsalesman.dungeonslibs.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import dev.obscuria.fragmentum.api.network.PacketPayload;

public class BreakItemMessage implements PacketPayload {
	private final ItemStack stack;
	private final int entityID;
	public static final PacketPayload.Info<BreakItemMessage> INFO = new PacketPayload.Info<>(BreakItemMessage.class,
			BreakItemMessage::encode, BreakItemMessage::decode, BreakItemMessage::handle);

	public BreakItemMessage(
			int entityID, ItemStack stack) {
		this.stack = stack;
		this.entityID = entityID;
	}

	private static void encode(BreakItemMessage packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.entityID);
		buf.writeItem(packet.stack);
	}

	private static BreakItemMessage decode(FriendlyByteBuf buf) {
		return new BreakItemMessage(buf.readInt(), buf.readItem());
	}

	private static void handle(Player player, BreakItemMessage packet) {
		if (packet != null && player.level().isClientSide) { // TODO These checks are probably useless
			ClientLevel world = Minecraft.getInstance().level;
			Entity target = null;
			if (world != null)
				target = world.getEntity(packet.entityID);
			if (target instanceof LivingEntity livingEntity) {
				livingEntity.breakItem(packet.stack);
			}
		}
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
