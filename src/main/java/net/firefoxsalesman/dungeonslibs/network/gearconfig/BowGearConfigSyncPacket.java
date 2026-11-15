package net.firefoxsalesman.dungeonslibs.network.gearconfig;

import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.BowGearConfig;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.BowGearConfigRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BowGearConfigSyncPacket implements PacketPayload {
	private static final Codec<Map<ResourceLocation, BowGearConfig>> MAPPER = Codec
			.unboundedMap(ResourceLocation.CODEC, BowGearConfig.CODEC);

	private final Map<ResourceLocation, BowGearConfig> data;

	public static PacketPayload.Info<BowGearConfigSyncPacket> INFO = new PacketPayload.Info<>(
			BowGearConfigSyncPacket.class, BowGearConfigSyncPacket::encode,
			BowGearConfigSyncPacket::decode, BowGearConfigSyncPacket::onPacketReceived);

	public BowGearConfigSyncPacket(Map<ResourceLocation, BowGearConfig> data) {
		this.data = data;
	}

	private static void encode(BowGearConfigSyncPacket packet, FriendlyByteBuf buffer) {
		buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
				.orElse(new CompoundTag())));
	}

	private static BowGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
		return new BowGearConfigSyncPacket(
				MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
	}

	private static void onPacketReceived(Player player, BowGearConfigSyncPacket packet) {
		BowGearConfigRegistry.BOW_GEAR_CONFIGS.setData(packet.data);
		reloadAllItems();
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
