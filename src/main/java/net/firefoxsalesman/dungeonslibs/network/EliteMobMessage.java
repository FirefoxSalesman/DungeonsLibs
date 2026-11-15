package net.firefoxsalesman.dungeonslibs.network;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.capabilities.elite.EliteMob;
import net.firefoxsalesman.dungeonslibs.capabilities.elite.EliteMobHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EliteMobMessage implements PacketPayload {
	private final int entityId;
	private final boolean isElite;
	private final ResourceLocation texture;

	public static final PacketPayload.Info<EliteMobMessage> INFO = new PacketPayload.Info<>(
			EliteMobMessage.class,
			EliteMobMessage::encode, EliteMobMessage::decode, EliteMobMessage::handle);

	public EliteMobMessage(int entityId, boolean isElite, ResourceLocation texture) {
		this.entityId = entityId;
		this.isElite = isElite;
		this.texture = texture;
	}

	private static void encode(EliteMobMessage packet, FriendlyByteBuf buffer) {
		buffer.writeInt(packet.entityId);
		buffer.writeBoolean(packet.isElite);
		buffer.writeResourceLocation(packet.texture);
	}

	private static EliteMobMessage decode(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();
		boolean isElite = buffer.readBoolean();
		ResourceLocation texture = buffer.readResourceLocation();
		return new EliteMobMessage(entityId, isElite, texture);
	}

	private static void handle(Player player, EliteMobMessage packet) {
		Entity entity = player.level().getEntity(packet.getEntityId());
		if (entity instanceof LivingEntity) {
			EliteMob cap = EliteMobHelper.getEliteMobCapability(entity);
			cap.setElite(packet.isElite());
			cap.setTexture(packet.getTexture());
			if (cap.isElite()) {
				entity.refreshDimensions();
			}
		}
	}

	public int getEntityId() {
		return entityId;
	}

	public boolean isElite() {
		return isElite;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
