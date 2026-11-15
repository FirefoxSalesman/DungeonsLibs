package net.firefoxsalesman.dungeonslibs.network.gearconfig;

import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorGearConfig;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorGearConfigRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ArmorGearConfigSyncPacket implements PacketPayload {
	private static final Codec<Map<ResourceLocation, ArmorGearConfig>> MAPPER = Codec
			.unboundedMap(ResourceLocation.CODEC, ArmorGearConfig.CODEC);

	private final Map<ResourceLocation, ArmorGearConfig> data;

	public static PacketPayload.Info<ArmorGearConfigSyncPacket> INFO = new PacketPayload.Info<>(
			ArmorGearConfigSyncPacket.class, ArmorGearConfigSyncPacket::encode,
			ArmorGearConfigSyncPacket::decode, ArmorGearConfigSyncPacket::onPacketReceived);

	public ArmorGearConfigSyncPacket(Map<ResourceLocation, ArmorGearConfig> data) {
		this.data = data;
	}

	private static void encode(ArmorGearConfigSyncPacket packet, FriendlyByteBuf buffer) {
		buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
				.orElse(new CompoundTag())));
	}

	private static ArmorGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
		return new ArmorGearConfigSyncPacket(
				MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
	}

	private static void onPacketReceived(Player player, ArmorGearConfigSyncPacket packet) {
		ArmorGearConfigRegistry.ARMOR_GEAR_CONFIGS.setData(packet.data);
		reloadAllItems();
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
