package net.firefoxsalesman.dungeonslibs.capabilities.soulcaster;

import com.Polarice3.Goety.common.events.spell.ChangeSoulEnergyEvent;

import net.firefoxsalesman.dungeonslibs.event.PlayerSoulEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GoetyEvents {
	public void register() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onGainSouls(ChangeSoulEnergyEvent.Gain event) {
		MinecraftForge.EVENT_BUS
				.post(new PlayerSoulEvent.PickupSoul(event.getEntity(), null));
	}
}
