package net.firefoxsalesman.dungeonslibs;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.firefoxsalesman.dungeonslibs.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonslibs.capabilities.LibCapabilities;
import net.firefoxsalesman.dungeonslibs.config.DungeonsLibrariesConfig;
import net.firefoxsalesman.dungeonslibs.entities.LibEntityTypes;
import net.firefoxsalesman.dungeonslibs.init.ParticleInit;
import net.firefoxsalesman.dungeonslibs.items.ItemTagWrappers;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.items.RangedItemModelProperties;
import net.firefoxsalesman.dungeonslibs.items.artifacts.config.ArtifactGearConfigRegistry;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorGearConfigRegistry;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.BowGearConfigRegistry;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.CrossbowGearConfigRegistry;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.MeleeGearConfigRegistry;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.DungeonsArmorMaterials;
import net.firefoxsalesman.dungeonslibs.items.materials.weapon.WeaponMaterials;
import net.firefoxsalesman.dungeonslibs.loot.ModLootModifiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DungeonsLibs.MOD_ID)
public class DungeonsLibs {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "dungeonslibs";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();

	public DungeonsLibs() {
		ModLoadingContext.get().registerConfig(Type.COMMON, DungeonsLibrariesConfig.COMMON_SPEC,
				"dungeons-lib-common.toml");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ItemTagWrappers.init();
		AttributeRegistry.register(modEventBus);
		LibEntityTypes.register(modEventBus);
		DungeonsArmorMaterials.setupVanillaMaterials();
		WeaponMaterials.setupVanillaMaterials();

		ArmorGearConfigRegistry.subscribe();
		MeleeGearConfigRegistry.subscribe();
		BowGearConfigRegistry.subscribe();
		CrossbowGearConfigRegistry.subscribe();
		WeaponMaterials.subscribe();
		DungeonsArmorMaterials.subscribe();
		ArtifactGearConfigRegistry.subscribe();

		LibCapabilities.setupCapabilities();

		ModLootModifiers.register(modEventBus);
		ParticleInit.register(modEventBus);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(NetworkHandler::init);
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		event.enqueueWork(RangedItemModelProperties::init);
	}

	// You can use EventBusSubscriber to automatically register all static methods
	// in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
		}
	}
}
