package net.firefoxsalesman.dungeonslibs.items.gearconfig;

import java.util.Map;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.data.util.CodecJsonDataManager;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.network.gearconfig.BowGearConfigSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class BowGearConfigRegistry {

	public static final CodecJsonDataManager<BowGearConfig> BOW_GEAR_CONFIGS = new CodecJsonDataManager<>(
			"gearconfig/bow", BowGearConfig.CODEC);

	public static BowGearConfig getConfig(ResourceLocation resourceLocation) {
		return BOW_GEAR_CONFIGS.getData().getOrDefault(resourceLocation, BowGearConfig.DEFAULT);
	}

	public static boolean gearConfigExists(ResourceLocation resourceLocation) {
		return BOW_GEAR_CONFIGS.getData().containsKey(resourceLocation);
	}

	public static BowGearConfigSyncPacket toPacket(Map<ResourceLocation, BowGearConfig> map) {
		return new BowGearConfigSyncPacket(map);
	}

	@SubscribeEvent
	public static void onSyncDatapacks(OnDatapackSyncEvent event) {
		NetworkHandler.sendDatapackSyncPacket(event.getPlayer(), toPacket(BOW_GEAR_CONFIGS.getData()));
	}
}
