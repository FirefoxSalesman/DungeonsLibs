package net.firefoxsalesman.dungeonslibs.summon;

import static net.firefoxsalesman.dungeonslibs.capabilities.minionmaster.FollowerLeaderHelper.getFollowerCapability;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.capabilities.minionmaster.Follower;
import net.firefoxsalesman.dungeonslibs.capabilities.minionmaster.FollowerLeaderHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class SummonEvents {

	@SubscribeEvent
	public static void onSummonedMobAttemptsToAttack(LivingChangeTargetEvent event) {
		if (event.getNewTarget() == null)
			return;
		if (FollowerLeaderHelper.isFollower(event.getEntity())) {
			LivingEntity followerAttacker = event.getEntity();
			Follower attackerFollowerCapability = getFollowerCapability(followerAttacker);
			if (attackerFollowerCapability.getLeader() != null) {
				LivingEntity attackersOwner = attackerFollowerCapability.getLeader();
				if (FollowerLeaderHelper.isFollower(event.getNewTarget())) {
					LivingEntity summonableTarget = event.getNewTarget();
					Follower targetFollowerCapability = getFollowerCapability(summonableTarget);
					if (targetFollowerCapability.getLeader() != null) {
						LivingEntity targetsOwner = targetFollowerCapability.getLeader();
						if (targetsOwner.equals(attackersOwner)) {
							event.setCanceled(true);
							preventAttackForSummonableMob(followerAttacker);
						}
					}
				}
			}
			if (attackerFollowerCapability.getLeader() == event.getNewTarget()) {
				event.setCanceled(true);
				preventAttackForSummonableMob(followerAttacker);
			}
		}
	}

	private static void preventAttackForSummonableMob(LivingEntity followerAttacker) {
		if (followerAttacker instanceof NeutralMob) {
			((NeutralMob) followerAttacker).stopBeingAngry();
		}
	}
}
