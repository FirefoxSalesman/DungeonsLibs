package net.firefoxsalesman.dungeonslibs.network;

import dev.obscuria.fragmentum.api.network.FragmentumNetworking;
import dev.obscuria.fragmentum.api.network.PacketPayload;
import dev.obscuria.fragmentum.api.network.PayloadRegistrar;
import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

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
		PayloadRegistrar registrar = FragmentumNetworking.INSTANCE.registrar(DungeonsLibs.MOD_ID);
		registrar.registerClientbound(BreakItemMessage.INFO);
		registrar.registerClientbound(UpdateSoulsMessage.INFO);
		registrar.registerClientbound(ArmorGearConfigSyncPacket.INFO);
		registrar.registerClientbound(MeleeGearConfigSyncPacket.INFO);
		registrar.registerClientbound(BowGearConfigSyncPacket.INFO);
		registrar.registerClientbound(CrossbowGearConfigSyncPacket.INFO);
		registrar.registerClientbound(ArmorMaterialSyncPacket.INFO);
		registrar.registerClientbound(WeaponMaterialSyncPacket.INFO);
		registrar.registerClientbound(ArtifactGearConfigSyncPacket.INFO);
		registrar.registerServerbound(CuriosArtifactStartMessage.INFO);
		registrar.registerClientbound(CuriosArtifactStopMessage.INFO);
		registrar.registerClientbound(EliteMobMessage.INFO);
		registrar.registerServerbound(SwitchHandMessage.INFO);
		registrar.registerClientbound(AncientMessage.INFO);
		registrar.registerClientbound(BossBarMessage.INFO);
	}

	/**
	 * This is meant to be used in event bus subscribers for datapack reloaders.
	 */
	public static void sendDatapackSyncPacket(ServerPlayer player, PacketPayload packet) {
		if (player != null)
			FragmentumNetworking.INSTANCE.sendTo(player, packet);
		else
			FragmentumNetworking.INSTANCE.sendToAll(ServerLifecycleHooks.getCurrentServer(),
					packet);
	}
}
