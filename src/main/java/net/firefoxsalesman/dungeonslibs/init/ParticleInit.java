package net.firefoxsalesman.dungeonslibs.init;

import dev.obscuria.fragmentum.api.registry.Deferred;
import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class ParticleInit {
	public static final Deferred<SimpleParticleType> ELECTRIC_SHOCK = makeParticle("electric_shock");

	public static final Deferred<SimpleParticleType> SNOWFLAKE = makeParticle("snowflake");

	private static Deferred<SimpleParticleType> makeParticle(String name) {
		return DungeonsLibs.REGISTRAR.register(Registries.PARTICLE_TYPE, ResourceLocationHelper.modLoc(name),
				() -> new SimpleParticleType(true)).as();
	}

	public static void init() {}
}
