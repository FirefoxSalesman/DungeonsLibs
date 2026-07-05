package net.firefoxsalesman.dungeonslibs.integration.curios.client;

import static net.firefoxsalesman.dungeonslibs.integration.curios.client.CuriosKeyBindings.activateArtifact1;
import static net.firefoxsalesman.dungeonslibs.integration.curios.client.CuriosKeyBindings.activateArtifact2;
import static net.firefoxsalesman.dungeonslibs.integration.curios.client.CuriosKeyBindings.activateArtifact3;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosClientIntegration {
	@SubscribeEvent
	public static void setupCuriosKeybindings(RegisterKeyMappingsEvent event) {
		activateArtifact1.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(activateArtifact1);
		activateArtifact2.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(activateArtifact2);
		activateArtifact3.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(activateArtifact3);
	}
}
