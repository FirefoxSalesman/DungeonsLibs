package net.firefoxsalesman.dungeonslibs.items.gearconfig;

import java.util.Map;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.MeleeGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class MeleeGearConfigRegistry {
	public static final ResourceLocation GEAR_CONFIG_BUILTIN_RESOURCELOCATION = ResourceLocationHelper
			.modLoc("gear_config");

	public static final CodecJsonDataManager<MeleeGearConfig> MELEE_GEAR_CONFIGS = new CodecJsonDataManager<>(
			"gearconfig/melee", MeleeGearConfig.CODEC);

	public static MeleeGearConfig getConfig(ResourceLocation resourceLocation) {
		return MELEE_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, MeleeGearConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return MELEE_GEAR_CONFIGS.getData().containsKey(resourceLocation);
	}

	public static MeleeGearConfigSyncPacket toPacket(Map<ResourceLocation, MeleeGearConfig> map) {
		return new MeleeGearConfigSyncPacket(map);
	}

	@SubscribeEvent
	public static void onSyncDatapacks(OnDatapackSyncEvent event) {
		NetworkHandler.sendDatapackSyncPacket(event.getPlayer(), toPacket(MELEE_GEAR_CONFIGS.getData()));
	}
}
