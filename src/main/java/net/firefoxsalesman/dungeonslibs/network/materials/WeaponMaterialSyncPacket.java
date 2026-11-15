package net.firefoxsalesman.dungeonslibs.network.materials;

import com.mojang.serialization.Codec;
import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.materials.weapon.DungeonsWeaponMaterial;
import net.firefoxsalesman.dungeonslibs.items.materials.weapon.WeaponMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

public class WeaponMaterialSyncPacket implements PacketPayload {
    private static final Codec<Map<ResourceLocation, Tier>> MAPPER = Codec.unboundedMap(ResourceLocation.CODEC,
            DungeonsWeaponMaterial.CODEC);

    private final Map<ResourceLocation, Tier> data;

    public static PacketPayload.Info<WeaponMaterialSyncPacket> INFO = new PacketPayload.Info<>(
            WeaponMaterialSyncPacket.class, WeaponMaterialSyncPacket::encode,
            WeaponMaterialSyncPacket::decode, WeaponMaterialSyncPacket::onPacketReceived);

    public WeaponMaterialSyncPacket(Map<ResourceLocation, Tier> data) {
        this.data = data.entrySet().stream().filter(entry -> entry.getValue() instanceof DungeonsWeaponMaterial)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static void encode(WeaponMaterialSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
                .orElse(new CompoundTag())));
    }

    private static WeaponMaterialSyncPacket decode(FriendlyByteBuf buffer) {
        return new WeaponMaterialSyncPacket(
                MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    private static void onPacketReceived(Player player, WeaponMaterialSyncPacket packet) {
        WeaponMaterials.WEAPON_MATERIALS.setData(packet.data);
        reloadAllItems();
    }

    @Override
    public Info<?> info() {
        return INFO;
    }
}
