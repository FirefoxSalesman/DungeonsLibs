package net.firefoxsalesman.dungeonslibs.network.materials;

import com.mojang.serialization.Codec;
import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.DungeonsArmorMaterial;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.DungeonsArmorMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

public class ArmorMaterialSyncPacket implements PacketPayload {
    private static final Codec<Map<ResourceLocation, ArmorMaterial>> MAPPER = Codec
            .unboundedMap(ResourceLocation.CODEC, DungeonsArmorMaterial.CODEC);

    private final Map<ResourceLocation, ArmorMaterial> data;

    public static PacketPayload.Info<ArmorMaterialSyncPacket> INFO = new PacketPayload.Info<>(
            ArmorMaterialSyncPacket.class, ArmorMaterialSyncPacket::encode,
            ArmorMaterialSyncPacket::decode, ArmorMaterialSyncPacket::onPacketReceived);

    public ArmorMaterialSyncPacket(Map<ResourceLocation, ArmorMaterial> data) {
        this.data = data.entrySet().stream().filter(entry -> entry.getValue() instanceof DungeonsArmorMaterial)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static void encode(ArmorMaterialSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
                .orElse(new CompoundTag())));
    }

    private static ArmorMaterialSyncPacket decode(FriendlyByteBuf buffer) {
        return new ArmorMaterialSyncPacket(
                MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    private static void onPacketReceived(Player player, ArmorMaterialSyncPacket packet) {
        DungeonsArmorMaterials.ARMOR_MATERIALS.setData(packet.data);
        reloadAllItems();
    }

    @Override
    public Info<?> info() {
        return INFO;
    }
}
