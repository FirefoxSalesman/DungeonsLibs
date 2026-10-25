package net.firefoxsalesman.dungeonslibs.utils;

import net.minecraftforge.fml.ModList;

/**
 * Allows you to run code based on when a mod is loaded.
 * Recent changes were inspired by Fragmentum
 */
public class ModHelper {
	public static ModHelper GOETY = new ModHelper("goety");
	public static ModHelper ENCHANTWITHMOB = new ModHelper("enchantwithmob");
	public static ModHelper QUARK = new ModHelper("quark");
	private String modId;

	public ModHelper(String modId) {
		this.modId = modId;
	}

	/**
	 * @return true if the mod is loaded
	 */
	public boolean hasMod() {
		return ModList.get().isLoaded(modId);
	}

	public void runWithMod(Runnable fn) {
		if (hasMod())
			fn.run();
	}
}
