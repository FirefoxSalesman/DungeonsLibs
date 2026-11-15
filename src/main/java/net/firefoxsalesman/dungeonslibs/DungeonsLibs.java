package net.firefoxsalesman.dungeonslibs;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import dev.obscuria.fragmentum.api.registry.FragmentumRegistry;
import dev.obscuria.fragmentum.api.registry.Registrar;
import net.firefoxsalesman.dungeonslibs.init.ParticleInit;
import net.firefoxsalesman.dungeonslibs.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonslibs.capabilities.LibCapabilities;
import net.firefoxsalesman.dungeonslibs.capabilities.soulcaster.GoetyEvents;
import net.firefoxsalesman.dungeonslibs.client.ClientProxy;
import net.firefoxsalesman.dungeonslibs.config.DungeonsLibrariesConfig;
import net.firefoxsalesman.dungeonslibs.items.ItemTagWrappers;
import net.firefoxsalesman.dungeonslibs.items.RangedItemModelProperties;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.DungeonsArmorMaterials;
import net.firefoxsalesman.dungeonslibs.items.materials.weapon.WeaponMaterials;
import net.firefoxsalesman.dungeonslibs.loot.ModLootModifiers;
import net.firefoxsalesman.dungeonslibs.network.CommonProxy;
import net.firefoxsalesman.dungeonslibs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.init.ClientRegistry;
import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
//? forge {
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//?}
//? neoforge {
/*import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
*///?}

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DungeonsLibs.MOD_ID)
public class DungeonsLibs {
    public static final String MOD_ID = "dungeonslibs";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CommonProxy PROXY;
    public static Registrar REGISTRAR = FragmentumRegistry.INSTANCE.registrar(MOD_ID);

    //? forge
    public DungeonsLibs() {
	//? neoforge
	// public DungeonsLibs(IEventBus modEventBus, ModContainer modContainer) {
	DungeonsLibrariesConfig.init();
	PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	//? forge {
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
	//?}
	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	//? neoforge
	//modEventBus.addListener(this::doClientStuff);
	ClientRegistry.init();
	ParticleInit.init();
	AttributeRegistry.init();
	ItemTagWrappers.init();
	DungeonsArmorMaterials.setupVanillaMaterials();
	WeaponMaterials.setupVanillaMaterials();
	//? forge
	LibCapabilities.setupCapabilities();
	//? neoforge {
	/*LibCapabilities.setupCapabilities(modEventBus);
	NetworkHandler.init(modEventBus);
	 *///?}
	ModLootModifiers.register(modEventBus);
	ModHelper.GOETY.runIfLoaded(() -> new GoetyEvents()::register);
    }

    //?forge {
    private void setup(final FMLCommonSetupEvent event) {
	event.enqueueWork(NetworkHandler::init);
    }
    //?}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    private void doClientStuff(final FMLClientSetupEvent event) {
	event.enqueueWork(RangedItemModelProperties::init);
    }
}
