package net.firefoxsalesman.dungeonslibs.network.gearconfig;

import com.mojang.serialization.Codec;
import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.items.artifacts.config.ArtifactGearConfigRegistry;
import net.firefoxsalesman.dungeonslibs.items.artifacts.config.ArtifactGearConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.Map;
import static net.firefoxsalesman.dungeonslibs.items.GearConfigReloadListener.reloadAllItems;

public class ArtifactGearConfigSyncPacket implements PacketPayload {
    private static final Codec<Map<ResourceLocation, ArtifactGearConfig>> MAPPER = Codec
            .unboundedMap(ResourceLocation.CODEC, ArtifactGearConfig.CODEC);

    private final Map<ResourceLocation, ArtifactGearConfig> data;

    public static PacketPayload.Info<ArtifactGearConfigSyncPacket> INFO = new PacketPayload.Info<>(
            ArtifactGearConfigSyncPacket.class, ArtifactGearConfigSyncPacket::encode,
            ArtifactGearConfigSyncPacket::decode, ArtifactGearConfigSyncPacket::onPacketReceived);

    public ArtifactGearConfigSyncPacket(Map<ResourceLocation, ArtifactGearConfig> data) {
        this.data = data;
    }

    private static void encode(ArtifactGearConfigSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) (MAPPER.encodeStart(NbtOps.INSTANCE, packet.data).result()
                .orElse(new CompoundTag())));
    }

    private static ArtifactGearConfigSyncPacket decode(FriendlyByteBuf buffer) {
        return new ArtifactGearConfigSyncPacket(
                MAPPER.parse(NbtOps.INSTANCE, buffer.readNbt()).result().orElse(new HashMap<>()));
    }

    private static void onPacketReceived(Player player, ArtifactGearConfigSyncPacket packet) {
        ArtifactGearConfigRegistry.ARTIFACT_GEAR_CONFIGS.setData(packet.data);
        reloadAllItems();
    }

    @Override
    public Info<?> info() {
        return INFO;
    }
}
