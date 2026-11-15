package net.firefoxsalesman.dungeonslibs.network.gearconfig;

import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.MeleeGearConfig;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.MeleeGearConfigRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MeleeGearConfigSyncPacket implements PacketPayload {
	public static PacketPayload.Info<MeleeGearConfigSyncPacket> INFO = new PacketPayload.Info<>(
			MeleeGearConfigSyncPacket.class, MeleeGearConfigSyncPacket::encode,
			MeleeGearConfigSyncPacket::decode, MeleeGearConfigSyncPacket::onPacketReceived);
	private static final Codec<Map<ResourceLocation, MeleeGearConfig>> MAPPER = Codec
			.unboundedMap(ResourceLocation.CODEC, MeleeGearConfig.CODEC);

	private final Map<ResourceLocation, MeleeGearConfig> data;

	public MeleeGearConfigSyncPacket(Map<ResourceLocation, MeleeGearConfig> data) {
		this.data = data;
	}

	private static void encode(MeleeGearConfigSyncPacket packet, FriendlyByteBuf buffer) {
		buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
				.orElse(new CompoundTag())));
	}

	private static MeleeGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
		return new MeleeGearConfigSyncPacket(
				MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
	}

	private static void onPacketReceived(Player player, MeleeGearConfigSyncPacket packet) {
		MeleeGearConfigRegistry.MELEE_GEAR_CONFIGS.setData(packet.data);
		reloadAllItems();
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
