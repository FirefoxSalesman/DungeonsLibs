package net.firefoxsalesman.dungeonslibs.init;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import static net.minecraftforge.api.distmarker.Dist.CLIENT;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.client.renderer.SoulOrbRenderer;
import net.firefoxsalesman.dungeonslibs.client.renderer.SummonSpotRenderer;
import net.firefoxsalesman.dungeonslibs.entities.LibEntityTypes;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(LibEntityTypes.SOUL_ORB.get(), SoulOrbRenderer::new);
		event.registerEntityRenderer(LibEntityTypes.SUMMON_SPOT.get(), SummonSpotRenderer::new);
	}

	// @SubscribeEvent
	// public static void registerArmorRenderers(final
	// EntityRenderersEvent.AddLayers event) {
	// GeoArmorRenderer.registerArmorRenderer(ArmorGear.class,
	// ArmorGearRenderer::new);
	// }
}
