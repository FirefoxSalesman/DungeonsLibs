package net.firefoxsalesman.dungeonslibs.items.artifacts.config;

import java.util.Map;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.ArtifactGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class ArtifactGearConfigRegistry {
	public static final ResourceLocation GEAR_CONFIG_BUILTIN_RESOURCELOCATION = ResourceLocationHelper
			.modLoc("gear_config");

	public static final CodecJsonDataManager<ArtifactGearConfig> ARTIFACT_GEAR_CONFIGS = new CodecJsonDataManager<>(
			"gearconfig/artifact", ArtifactGearConfig.CODEC);

	public static ArtifactGearConfig getConfig(ResourceLocation resourceLocation) {
		return ARTIFACT_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, ArtifactGearConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return ARTIFACT_GEAR_CONFIGS.getData().containsKey(resourceLocation);
	}

	public static ArtifactGearConfigSyncPacket toPacket(Map<ResourceLocation, ArtifactGearConfig> map) {
		return new ArtifactGearConfigSyncPacket(map);
	}

	@SubscribeEvent
	public static void onSyncDatapacks(OnDatapackSyncEvent event) {
		NetworkHandler.sendDatapackSyncPacket(event.getPlayer(), toPacket(ARTIFACT_GEAR_CONFIGS.getData()));
	}
}
