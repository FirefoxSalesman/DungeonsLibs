package net.firefoxsalesman.dungeonslibs.network.message;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.capabilities.ancient.Ancient;
import net.firefoxsalesman.dungeonslibs.capabilities.ancient.AncientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AncientMessage implements PacketPayload {
	private final int entityId;
	private final boolean ancient;

	public static final PacketPayload.Info<AncientMessage> INFO = new PacketPayload.Info<>(
		AncientMessage.class,
		AncientMessage::encode,
		AncientMessage::decode,
		AncientMessage::handle
	);

	public AncientMessage(int entityId, boolean ancient) {
		this.entityId = entityId;
		this.ancient = ancient;
	}

	private static void encode(AncientMessage packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.entityId);
		buf.writeBoolean(packet.ancient);
	}

	private static AncientMessage decode(FriendlyByteBuf buf) {
		int entityId = buf.readInt();
		boolean ancient = buf.readBoolean();
		return new AncientMessage(entityId, ancient);
	}

	private static void handle(Player player, AncientMessage packet) {
		if (player != null) {
			Entity entity = player.level().getEntity(packet.entityId);
			if (entity instanceof LivingEntity) {
				Ancient cap = AncientHelper.getAncientCapability(entity);
				cap.setAncient(packet.ancient);
				entity.refreshDimensions();
			}
		}
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
