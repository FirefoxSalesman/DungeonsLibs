package net.firefoxsalesman.dungeonslibs.items.gearconfig;

import java.util.Map;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.ArmorGearConfigSyncPacket;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class ArmorGearConfigRegistry {
	public static final ResourceLocation GEAR_CONFIG_BUILTIN_RESOURCELOCATION = ResourceLocationHelper
			.modLoc("gear_config");

	public static final CodecJsonDataManager<ArmorGearConfig> ARMOR_GEAR_CONFIGS = new CodecJsonDataManager<>(
			"gearconfig/armor", ArmorGearConfig.CODEC);

	public static ArmorGearConfig getConfig(ResourceLocation resourceLocation) {
		return ARMOR_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, ArmorGearConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return ARMOR_GEAR_CONFIGS.getData().containsKey(resourceLocation);
	}

	public static ArmorGearConfigSyncPacket toPacket(Map<ResourceLocation, ArmorGearConfig> map) {
		return new ArmorGearConfigSyncPacket(map);
	}

	@SubscribeEvent
	public static void onSyncDatapacks(OnDatapackSyncEvent event) {
		NetworkHandler.sendDatapackSyncPacket(event.getPlayer(), toPacket(ARMOR_GEAR_CONFIGS.getData()));
	}
}
