package net.firefoxsalesman.dungeonslibs.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.firefoxsalesman.dungeonslibs.integration.curios.client.message.CuriosArtifactStartMessage;
import net.firefoxsalesman.dungeonslibs.integration.curios.client.message.CuriosArtifactStopMessage;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.ArmorGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.ArtifactGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.BowGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.CrossbowGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.MeleeGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.materials.ArmorMaterialSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.materials.WeaponMaterialSyncPacket;
import net.firefoxsalesman.dungeonslibs.network.message.AncientMessage;
import net.firefoxsalesman.dungeonslibs.network.message.BossBarMessage;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
			ResourceLocationHelper.modLoc("network"))
			.clientAcceptedVersions("1"::equals)
			.serverAcceptedVersions("1"::equals)
			.networkProtocolVersion(() -> "1")
			.simpleChannel();

	protected static int PACKET_COUNTER = 0;

	public NetworkHandler() {
	}

	public static void init() {
		buildMessage(UpdateSoulsMessage.class, UpdateSoulsMessage::encode,
				UpdateSoulsMessage::decode, UpdateSoulsMessage.UpdateSoulsHandler::handle);
		buildMessage(ArmorGearConfigSyncPacket.class, ArmorGearConfigSyncPacket::encode,
				ArmorGearConfigSyncPacket::decode, ArmorGearConfigSyncPacket::onPacketReceived);
		buildMessage(MeleeGearConfigSyncPacket.class, MeleeGearConfigSyncPacket::encode,
				MeleeGearConfigSyncPacket::decode, MeleeGearConfigSyncPacket::onPacketReceived);
		buildMessage(BowGearConfigSyncPacket.class, BowGearConfigSyncPacket::encode,
				BowGearConfigSyncPacket::decode, BowGearConfigSyncPacket::onPacketReceived);
		buildMessage(CrossbowGearConfigSyncPacket.class, CrossbowGearConfigSyncPacket::encode,
				CrossbowGearConfigSyncPacket::decode, CrossbowGearConfigSyncPacket::onPacketReceived);
		buildMessage(ArmorMaterialSyncPacket.class, ArmorMaterialSyncPacket::encode,
				ArmorMaterialSyncPacket::decode, ArmorMaterialSyncPacket::onPacketReceived);
		buildMessage(WeaponMaterialSyncPacket.class, WeaponMaterialSyncPacket::encode,
				WeaponMaterialSyncPacket::decode, WeaponMaterialSyncPacket::onPacketReceived);
		buildMessage(CuriosArtifactStartMessage.class, CuriosArtifactStartMessage::encode,
				CuriosArtifactStartMessage::decode,
				CuriosArtifactStartMessage.CuriosArtifactHandler::handle);
		buildMessage(CuriosArtifactStopMessage.class, CuriosArtifactStopMessage::encode,
				CuriosArtifactStopMessage::decode, CuriosArtifactStopMessage::handle);
		buildMessage(EliteMobMessage.class, EliteMobMessage::encode, EliteMobMessage::decode,
				EliteMobMessage::handle);
		buildMessage(BreakItemMessage.class, BreakItemMessage::encode, BreakItemMessage::decode,
				BreakItemMessage.BreakItemHandler::handle);
		buildMessage(SwitchHandMessage.class, SwitchHandMessage::encode, SwitchHandMessage::decode,
				SwitchHandMessage.SwitchHandHandler::handle);
		buildMessage(ArtifactGearConfigSyncPacket.class, ArtifactGearConfigSyncPacket::encode,
				ArtifactGearConfigSyncPacket::decode,
				ArtifactGearConfigSyncPacket::onPacketReceived);
		buildMessage(AncientMessage.class, AncientMessage::encode, AncientMessage::decode,
				AncientMessage::onPacketReceived);
		buildMessage(BossBarMessage.class, BossBarMessage::encode, BossBarMessage::decode,
				BossBarMessage::onPacketReceived);
	}

	private static <T> void buildMessage(Class<T> kind, BiConsumer<T, FriendlyByteBuf> encoder,
			Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, Supplier<NetworkEvent.Context>> consumerMainThread) {
		INSTANCE.messageBuilder(kind, PACKET_COUNTER++)
				.encoder(encoder).decoder(decoder)
				.consumerMainThread(consumerMainThread)
				.add();
	}
}
