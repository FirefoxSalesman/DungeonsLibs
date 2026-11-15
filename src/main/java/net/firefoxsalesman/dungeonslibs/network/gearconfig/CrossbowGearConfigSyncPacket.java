package net.firefoxsalesman.dungeonslibs.network.gearconfig;

import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.BowGearConfig;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.CrossbowGearConfigRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CrossbowGearConfigSyncPacket implements PacketPayload {
	private static final Codec<Map<ResourceLocation, BowGearConfig>> MAPPER = Codec
			.unboundedMap(ResourceLocation.CODEC, BowGearConfig.CODEC);

	private final Map<ResourceLocation, BowGearConfig> data;

	public static PacketPayload.Info<CrossbowGearConfigSyncPacket> INFO = new PacketPayload.Info<>(
			CrossbowGearConfigSyncPacket.class, CrossbowGearConfigSyncPacket::encode,
			CrossbowGearConfigSyncPacket::decode, CrossbowGearConfigSyncPacket::onPacketReceived);

	public CrossbowGearConfigSyncPacket(Map<ResourceLocation, BowGearConfig> data) {
		this.data = data;
	}

	private static void encode(CrossbowGearConfigSyncPacket packet, FriendlyByteBuf buffer) {
		buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
				.orElse(new CompoundTag())));
	}

	private static CrossbowGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
		return new CrossbowGearConfigSyncPacket(
				MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
	}

	private static void onPacketReceived(Player player, CrossbowGearConfigSyncPacket packet) {
		CrossbowGearConfigRegistry.CROSSBOW_GEAR_CONFIGS.setData(packet.data);
		reloadAllItems();
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
