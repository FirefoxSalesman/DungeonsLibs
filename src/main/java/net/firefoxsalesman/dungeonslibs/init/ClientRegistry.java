package net.firefoxsalesman.dungeonslibs.init;

import dev.obscuria.fragmentum.api.client.ClientRegistrar;
import dev.obscuria.fragmentum.api.client.FragmentumClientRegistry;
import dev.obscuria.fragmentum.api.registry.Deferred;
import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.client.renderer.SoulOrbRenderer;
import net.firefoxsalesman.dungeonslibs.client.renderer.SummonSpotRenderer;
import net.firefoxsalesman.dungeonslibs.client.renderer.water.CustomDrownedRenderer;
import net.firefoxsalesman.dungeonslibs.entities.LibEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;

public class ClientRegistry {
	public static void init() {
		ClientRegistrar registrar = FragmentumClientRegistry.INSTANCE.registrar(DungeonsLibs.MOD_ID);
		registrar.registerEntityRenderer(LibEntityTypes.SOUL_ORB, SoulOrbRenderer::new);
		registrar.registerEntityRenderer(LibEntityTypes.SUMMON_SPOT, SummonSpotRenderer::new);
		registrar.registerEntityRenderer(Deferred.Companion.create(() -> Holder.direct(EntityType.DROWNED)), CustomDrownedRenderer::new);
	}
}
