package net.firefoxsalesman.dungeonslibs.client;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.client.artifactBar.ArtifactsBarRenderer;
import net.firefoxsalesman.dungeonslibs.client.gui.BossBarRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
	/**
	 * Borrowed from Goety
	 */
	@SubscribeEvent
	public static void registerGUI(final RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "soul_energy_hud", SoulEnergyGui.OVERLAY);
		event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "artifact_hud", ArtifactsBarRenderer.OVERLAY);
	}

	@SubscribeEvent
	public static void clientInit(FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.addListener(BossBarRenderer::renderBossBar);
	}
}
