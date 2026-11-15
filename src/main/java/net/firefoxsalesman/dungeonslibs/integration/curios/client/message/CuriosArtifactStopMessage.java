package net.firefoxsalesman.dungeonslibs.integration.curios.client.message;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.capabilities.artifact.ArtifactUsage;
import net.firefoxsalesman.dungeonslibs.capabilities.artifact.ArtifactUsageHelper;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CuriosArtifactStopMessage implements PacketPayload {
	public static final PacketPayload.Info<CuriosArtifactStopMessage> INFO = new PacketPayload.Info<>(
			CuriosArtifactStopMessage.class,
			CuriosArtifactStopMessage::encode,
			CuriosArtifactStopMessage::decode,
			CuriosArtifactStopMessage::handle);

	public CuriosArtifactStopMessage() {
	}

	private static void encode(CuriosArtifactStopMessage packet, FriendlyByteBuf buf) {
		// No fields to encode
	}

	private static CuriosArtifactStopMessage decode(FriendlyByteBuf buf) {
		return new CuriosArtifactStopMessage();
	}

	private static void handle(Player player, CuriosArtifactStopMessage packet) {
		if (player != null) {
			ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
			ItemStack artifactStack = cap.getUsingArtifact();
			if (artifactStack != null && artifactStack
					.getItem() instanceof ArtifactItem artifactItem) {
				artifactItem.stopUsingArtifact(player);
				cap.stopUsingArtifact();
			}
		}
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
