package net.firefoxsalesman.dungeonslibs.utils;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.init.ModDamageSources;
import net.firefoxsalesman.dungeonslibs.init.ParticleInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AreaOfEffectHelper {
	public static void applyToNearbyEntities(LivingEntity origin, float distance,
			Predicate<LivingEntity> applicablePredicate, Consumer<LivingEntity> entityConsumer) {
		Level world = origin.getCommandSenderWorld();
		applyToNearbyEntities(origin, world, distance, applicablePredicate, entityConsumer);
	}

	public static void applyToNearbyEntities(Entity origin, Level world, float distance,
			Predicate<LivingEntity> applicablePredicate, Consumer<LivingEntity> entityConsumer) {
		List<LivingEntity> nearbyEntities = getNearbyEnemies(origin, distance, world, applicablePredicate);
		if (nearbyEntities.isEmpty())
			return;
		for (LivingEntity nearbyEntity : nearbyEntities) {
			entityConsumer.accept(nearbyEntity);
		}
	}

	public static void applyToNearbyEntities(LivingEntity origin, float distance, int limit,
			Predicate<LivingEntity> applicablePredicate, Consumer<LivingEntity> entityConsumer) {
		Level world = origin.getCommandSenderWorld();
		applyToNearbyEntities(origin, world, distance, limit, applicablePredicate, entityConsumer);
	}

	public static void applyToNearbyEntities(Entity origin, Level world, float distance, int limit,
			Predicate<LivingEntity> applicablePredicate, Consumer<LivingEntity> entityConsumer) {
		List<LivingEntity> nearbyEntities = getNearbyEnemies(origin, distance, world, applicablePredicate);
		if (nearbyEntities.isEmpty())
			return;
		if (limit > nearbyEntities.size())
			limit = nearbyEntities.size();
		for (int i = 0; i < limit; i++) {
			if (nearbyEntities.size() >= i + 1) {
				LivingEntity nearbyEntity = nearbyEntities.get(i);
				entityConsumer.accept(nearbyEntity);
			}
		}
	}

	public static List<LivingEntity> getNearbyEnemies(Entity origin, float distance, Level world,
			Predicate<LivingEntity> applicablePredicate) {
		return world.getEntitiesOfClass(LivingEntity.class,
				origin.getBoundingBox().inflate(distance),
				applicablePredicate);
	}

	public static void applyToNearbyEntitiesAtPos(BlockPos origin, Level world, float distance,
			Predicate<LivingEntity> applicablePredicate, Consumer<LivingEntity> entityConsumer) {
		List<LivingEntity> nearbyEntities = getNearbyEnemies(origin, distance, world, applicablePredicate);
		if (nearbyEntities.isEmpty())
			return;
		for (LivingEntity nearbyEntity : nearbyEntities) {
			entityConsumer.accept(nearbyEntity);
		}
	}

	public static void applyToNearbyEntitiesAtPos(BlockPos origin, Level world, float distance, int limit,
			Predicate<LivingEntity> applicablePredicate, Consumer<LivingEntity> entityConsumer) {
		List<LivingEntity> nearbyEntities = getNearbyEnemies(origin, distance, world, applicablePredicate);
		if (nearbyEntities.isEmpty())
			return;
		if (limit > nearbyEntities.size())
			limit = nearbyEntities.size();
		for (int i = 0; i < limit; i++) {
			if (nearbyEntities.size() >= i + 1) {
				LivingEntity nearbyEntity = nearbyEntities.get(i);
				entityConsumer.accept(nearbyEntity);
			}
		}
	}

	public static List<LivingEntity> getNearbyEnemies(BlockPos origin, float distance, Level world,
			Predicate<LivingEntity> applicablePredicate) {
		return world.getEntitiesOfClass(LivingEntity.class,
				new AABB(origin).inflate(distance),
				applicablePredicate);
	}

	public static Predicate<LivingEntity> getCanApplyToSecondEnemyPredicate(LivingEntity attacker,
			LivingEntity target) {
		return (nearbyEntity) -> AbilityHelper.canApplyToSecondEnemy(attacker, target, nearbyEntity);
	}

	public static Predicate<LivingEntity> getCanApplyToEnemyPredicate(LivingEntity attacker) {
		return (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity);
	}

	public static Predicate<LivingEntity> getCanHealPredicate(LivingEntity healer) {
		return (nearbyEntity) -> AbilityHelper.canHealEntity(healer, nearbyEntity);
	}

	public static void createVisualLightningBoltOnEntity(Entity target) {
		Level world = target.getCommandSenderWorld();
		LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
		if (lightningboltentity != null) {
			lightningboltentity.moveTo(target.getX(), target.getY(), target.getZ());
			lightningboltentity.setVisualOnly(true);
			world.addFreshEntity(lightningboltentity);
		}
	}

	public static void electrify(LivingEntity attacker, LivingEntity victim, float damageAmount) {
		createVisualLightningBoltOnEntity(victim);
		DungeonsLibs.PROXY.spawnParticles(victim, ParticleInit.ELECTRIC_SHOCK.get());
		victim.hurt(ModDamageSources.source(attacker.level(), ModDamageSources.ELECTRIC_SHOCK, attacker, null),
				damageAmount);
	}

	public static void spawnCritCloud(LivingEntity attacker, LivingEntity victim, float radius) {
		AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(victim.level(), victim.getX(),
				victim.getY(),
				victim.getZ());
		areaeffectcloudentity.setOwner(attacker);
		areaeffectcloudentity.setParticle(ParticleTypes.CRIT);
		areaeffectcloudentity.setRadius(radius);
		areaeffectcloudentity.setDuration(0);
		attacker.level().addFreshEntity(areaeffectcloudentity);
	}
}
