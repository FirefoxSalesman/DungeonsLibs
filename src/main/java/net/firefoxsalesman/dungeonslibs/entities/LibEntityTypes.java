package net.firefoxsalesman.dungeonslibs.entities;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import static net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper.modLoc;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class LibEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, DungeonsLibs.MOD_ID);

	public static final RegistryObject<EntityType<SoulOrbEntity>> SOUL_ORB = ENTITY_TYPES.register("soul_orb",
			() -> EntityType.Builder.<SoulOrbEntity>of(SoulOrbEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(6).updateInterval(20)
					.build(modLoc("soul_orb").toString()));
	public static final RegistryObject<EntityType<SummonSpotEntity>> SUMMON_SPOT = ENTITY_TYPES.register(
			"summon_spot",
			() -> EntityType.Builder.<SummonSpotEntity>of(SummonSpotEntity::new, MobCategory.MISC)
					.fireImmune()
					.sized(1.0F, 2.0F)
					.clientTrackingRange(10)
					.build(modLoc("summon_spot").toString()));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
