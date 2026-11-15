package net.firefoxsalesman.dungeonslibs.entities;

import static net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper.modLoc;

import dev.obscuria.fragmentum.api.registry.Deferred;
import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class LibEntityTypes {

	public static final Deferred<EntityType<SoulOrbEntity>> SOUL_ORB = makeEntity("soul_orb",
			EntityType.Builder.<SoulOrbEntity>of(SoulOrbEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(6).updateInterval(20));
	public static final Deferred<EntityType<SummonSpotEntity>> SUMMON_SPOT = makeEntity(
			"summon_spot",
			EntityType.Builder.<SummonSpotEntity>of(SummonSpotEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.0F)
					.clientTrackingRange(10));

	private static <T extends Entity> Deferred<EntityType<T>> makeEntity(String name,
			EntityType.Builder<T> builder) {
		return DungeonsLibs.REGISTRAR.register(Registries.ENTITY_TYPE, modLoc(name),
				() -> builder.build(modLoc(name).toString())).as();
	}
}
