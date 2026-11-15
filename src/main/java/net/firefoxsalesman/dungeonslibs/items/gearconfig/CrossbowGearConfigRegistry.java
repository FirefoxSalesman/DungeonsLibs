package net.firefoxsalesman.dungeonslibs.items.gearconfig;

import java.util.Map;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.CrossbowGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class CrossbowGearConfigRegistry {
	public static final CodecJsonDataManager<BowGearConfig> CROSSBOW_GEAR_CONFIGS = new CodecJsonDataManager<>(
			"gearconfig/crossbow", BowGearConfig.CODEC);

	public static BowGearConfig getConfig(ResourceLocation resourceLocation) {
		return CROSSBOW_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, BowGearConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return CROSSBOW_GEAR_CONFIGS.getData().containsKey(resourceLocation);
	}

	public static CrossbowGearConfigSyncPacket toPacket(Map<ResourceLocation, BowGearConfig> map) {
		return new CrossbowGearConfigSyncPacket(map);
	}

	@SubscribeEvent
	public static void onSyncDatapacks(OnDatapackSyncEvent event) {
		NetworkHandler.sendDatapackSyncPacket(event.getPlayer(), toPacket(CROSSBOW_GEAR_CONFIGS.getData()));
	}
}
