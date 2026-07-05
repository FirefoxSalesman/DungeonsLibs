package net.firefoxsalesman.dungeonslibs.capabilities.elite;

import net.minecraft.world.entity.Entity;

import static net.firefoxsalesman.dungeonslibs.capabilities.LibCapabilities.ELITE_MOB_CAPABILITY;

public class EliteMobHelper {

	public static EliteMob getEliteMobCapability(Entity entity) {
		return entity.getCapability(ELITE_MOB_CAPABILITY).orElse(new EliteMob());
	}
}
