package net.firefoxsalesman.dungeonslibs.utils;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {
	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(DungeonsLibs.MOD_ID, path);
	}

	public static ResourceLocation forgeLoc(String path) {
		return new ResourceLocation("forge", path);
	}
}
