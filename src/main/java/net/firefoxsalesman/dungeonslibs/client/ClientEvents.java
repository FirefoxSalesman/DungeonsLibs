package net.firefoxsalesman.dungeonslibs.client;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.client.artifactBar.ArtifactsBarRender;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
	/**
	 * Borrowed from Goety
	 */
	@SubscribeEvent
	public static void registerGUI(final RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "artifacts_bar", ArtifactsBarRender.OVERLAY);
		event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "soul_energy_hud", SoulEnergyGui.OVERLAY);
	}
}
