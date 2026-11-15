package net.firefoxsalesman.dungeonslibs.capabilities;

import static net.firefoxsalesman.dungeonslibs.capabilities.minionmaster.FollowerLeaderHelper.getLeaderCapability;
import static net.firefoxsalesman.dungeonslibs.capabilities.playerrewards.PlayerRewardsHelper.getPlayerRewardsCapability;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.capabilities.minionmaster.Leader;
import net.firefoxsalesman.dungeonslibs.capabilities.playerrewards.PlayerRewards;
import net.firefoxsalesman.dungeonslibs.capabilities.soulcaster.SoulCaster;
import net.firefoxsalesman.dungeonslibs.capabilities.soulcaster.SoulCasterHelper;
import net.firefoxsalesman.dungeonslibs.config.DungeonsLibrariesConfig;
import net.firefoxsalesman.dungeonslibs.network.UpdateSoulsMessage;
//? forge {
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//? } elif neoforge {
/*
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
*///? }

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class CapabilityEvents {

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		UpdateSoulsMessage.maybeSend(event.getEntity());
	}

	@SubscribeEvent
	public static void clonePlayerCaps(PlayerEvent.Clone event) {
		Leader oldLeaderCap = getLeaderCapability(event.getOriginal());
		Leader newLeaderCap = getLeaderCapability(event.getEntity());
		newLeaderCap.copyFrom(oldLeaderCap);
		if (!event.isWasDeath() || DungeonsLibrariesConfig.ENABLE_KEEP_SOULS_ON_DEATH.get()) {
			SoulCaster newSoulsCap = SoulCasterHelper.getSoulCasterCapability(event.getEntity());
			newSoulsCap.setSouls(SoulCasterHelper.getSouls(event.getOriginal()), event.getEntity());
		}
		PlayerRewards oldPlayerRewardsCap = getPlayerRewardsCapability(event.getOriginal());
		PlayerRewards newPlayerRewardsCap = getPlayerRewardsCapability(event.getEntity());
		newPlayerRewardsCap.setPlayerRewards(oldPlayerRewardsCap.getAllPlayerRewards());
	}
}
