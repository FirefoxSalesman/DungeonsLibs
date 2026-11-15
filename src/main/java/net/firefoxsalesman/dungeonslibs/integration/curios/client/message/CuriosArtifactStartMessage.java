package net.firefoxsalesman.dungeonslibs.integration.curios.client.message;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactUseContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class CuriosArtifactStartMessage implements PacketPayload {
	private final int slot;
	private final BlockHitResult hitResult;
	public static final PacketPayload.Info<CuriosArtifactStartMessage> INFO = new PacketPayload.Info<>(
			CuriosArtifactStartMessage.class,
			CuriosArtifactStartMessage::encode, CuriosArtifactStartMessage::decode,
			CuriosArtifactStartMessage::handle);

	public CuriosArtifactStartMessage(int slot, BlockHitResult hitResult) {
		this.slot = slot;
		this.hitResult = hitResult;
	}

	private static void encode(CuriosArtifactStartMessage packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.slot);
		buf.writeBlockHitResult(packet.hitResult);
	}

	private static CuriosArtifactStartMessage decode(FriendlyByteBuf buf) {
		return new CuriosArtifactStartMessage(buf.readInt(), buf.readBlockHitResult());
	}

	private static void handle(Player player, CuriosArtifactStartMessage packet) {
		if (player != null) {
			CuriosApi.getCuriosHelper().getCuriosHandler(player)
					.ifPresent(iCuriosItemHandler -> {
						Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler
								.getStacksHandler("artifact");
						if (artifactStackHandler.isPresent()) {
							ItemStack artifact = artifactStackHandler
									.get().getStacks()
									.getStackInSlot(packet.slot);
							if (!artifact.isEmpty() && artifact
									.getItem() instanceof ArtifactItem) {
								ArtifactUseContext iuc = new ArtifactUseContext(
										player.level(),
										player,
										artifact,
										packet.hitResult);
								((ArtifactItem) artifact
										.getItem())
										.activateArtifact(iuc);
							}
						}
					});
		}
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
