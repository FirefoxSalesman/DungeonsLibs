package net.firefoxsalesman.dungeonslibs.capabilities.timers;

import net.firefoxsalesman.dungeonslibs.capabilities.LibCapabilities;
import net.minecraft.world.entity.Entity;

public class TimersHelper {

	public static Timers getTimersCapability(Entity entity) {
		return entity.getCapability(LibCapabilities.TIMERS_CAPABILITY).orElse(new Timers());
	}
}
