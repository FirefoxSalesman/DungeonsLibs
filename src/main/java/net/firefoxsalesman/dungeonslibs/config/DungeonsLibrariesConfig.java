package net.firefoxsalesman.dungeonslibs.config;

import java.util.Set;

import dev.obscuria.fragmentum.api.config.ConfigBuilder;
import dev.obscuria.fragmentum.api.config.Configurable;
import net.firefoxsalesman.dungeonslibs.DungeonsLibs;

public class DungeonsLibrariesConfig {
	public static Configurable<Boolean> ENABLE_AREA_OF_EFFECT_ON_OTHER_PLAYERS;
	public static Configurable<Integer> SOUL_BAR_VERTICAL_OFFSET;
	public static Configurable<Integer> SOUL_BAR_HORIZONTAL_OFFSET;
	public static Configurable<Integer> ARTIFACT_BAR_VERTICAL_OFFSET;
	public static Configurable<Integer> ARTIFACT_BAR_HORIZONTAL_OFFSET;
	public static Configurable<Boolean> ENABLE_KEEP_SOULS_ON_DEATH;
	public static Configurable<Boolean> ENABLE_DUAL_WIELDING;
	public static Configurable<Boolean> ENABLE_TWO_HANDED_WEAPON;
	public static Configurable<Boolean> ENABLE_ELITE_MOBS;
	public static Configurable<Double> ELITE_MOBS_BASE_CHANCE;
	public static Configurable<Boolean> ENABLE_TARGETS_BASED_ON_GOALS;
	public static Set<Configurable<?>> VALUES;

	static {
		ConfigBuilder builder = ConfigBuilder.Companion.create("dungeons-lib-common.toml");
		builder.comment("Combat Configuration");
		ENABLE_AREA_OF_EFFECT_ON_OTHER_PLAYERS = builder
				.comment("Enable area of effects also being applied to players. \n" +
						"If you do not want area of effects being applied to other players, disable this feature. [true / false]")
				.define("enableAreaOfEffectOnOtherPlayers", false);
		ENABLE_TARGETS_BASED_ON_GOALS = builder
				.comment("Enable limiting area of effects of mobs to only mobs they can normally target. \n"
						+
						"Disabling this feature will cause mobs to hit eachother with AoE effects, but can fix unintended issues. [true / false]")
				.define("enableTargetsBasedOnGoals", true);
		// combat configuration
		builder.comment("Combat Configuration");
		ENABLE_DUAL_WIELDING = builder
				.comment("Enable dual wielding. \n" +
						"If you do not want dual wielding, disable this feature. [true / false]")
				.define("enableDualWielding", true);
		ENABLE_TWO_HANDED_WEAPON = builder
				.comment("Enable two handed weapon. \n" +
						"If you do not want two handed weapon, disable this feature. [true / false]")
				.define("enableTwoHandedWeapon", true);

		builder.comment("Souls Configuration");
		ENABLE_KEEP_SOULS_ON_DEATH = builder
				.comment("Enables keeping of souls upon death, disabled by default. [true / false]")
				.define("enableKeepSoulsOnDeath", false);
		SOUL_BAR_HORIZONTAL_OFFSET = builder
				.comment("Horizontal offset of the soul bar. Negative values move the bar to the left. Positive values move the bar to the right.")
				.define("soulBarHorizontalOffset", 0);

		SOUL_BAR_VERTICAL_OFFSET = builder
				.comment("Vertical offset of the soul bar. Negative values move the bar down. Positive values move the bar up.")
				.define("soulBarVerticalOffset", 0);
		ARTIFACT_BAR_HORIZONTAL_OFFSET = builder
				.comment("Horizontal offset of the artifact bar. Negative values move the bar to the left. Positive values move the bar to the right.")
				.define("artifactBarHorizontalOffset", 0);

		ARTIFACT_BAR_VERTICAL_OFFSET = builder
				.comment("Vertical offset of the artifact bar. Negative values move the bar down. Positive values move the bar up.")
				.define("artifactBarVerticalOffset", 0);
		builder.comment("Elite Mob Configuration");
		ENABLE_ELITE_MOBS = builder
				.comment("Enables elite mobs, enabled by default. [true / false]")
				.define("enableEliteMobs", true);
		ELITE_MOBS_BASE_CHANCE = builder
				.comment("Base chance of an elite mob spawning. [0.0 - 1.0] \n" +
						"Calculation: chance * difficulty.getSpecialMultiplier() \n" +
						"Base chance for vanilla armor spawning is 0.15. Default is 0.15")
				.defineDouble("eliteMobsBaseChance", 0.15, 0.0, 1.0);
		VALUES = builder.buildCommon(DungeonsLibs.MOD_ID);
	}

	public static void init() {
	}
}
