package net.firefoxsalesman.dungeonslibs.attribute;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static net.firefoxsalesman.dungeonslibs.DungeonsLibs.MOD_ID;

import dev.obscuria.fragmentum.api.registry.Deferred;

public class AttributeRegistry {

	/**
	 * @deprecated To be renamed FOLLOWER_COST_LIMIT in 1.20.0
	 */
	@Deprecated
	public static final Deferred<Attribute> SUMMON_CAP = mkAttribute("summon_cap", 0.0D);
	public static final Deferred<Attribute> FOLLOWER_COST_LIMIT = mkAttribute("follower_cost_limit", 24.0D);
	public static final Deferred<Attribute> SOUL_GATHERING = mkAttribute("soul_gathering", 0.0D);
	public static final Deferred<Attribute> SOUL_CAP = mkAttribute("soul_cap", 300.0D);
	public static final Deferred<Attribute> LIFE_STEAL = mkAttribute("life_steal", 1.0D);
	public static final Deferred<Attribute> RANGED_DAMAGE_MULTIPLIER = mkAttribute("ranged_damage_multiplier",
			1.0D);
	public static final Deferred<Attribute> MAGIC_DAMAGE_MULTIPLIER = mkAttribute("magic_damage_multiplier",
			0.0D);
	public static final Deferred<Attribute> ARTIFACT_COOLDOWN_MULTIPLIER = mkAttribute(
			"artifact_cooldown_multiplier",
			1.0D);

	/**
	 * Registers a new attribute with the given name & default value.
	 */
	private static Deferred<Attribute> mkAttribute(String name, double defaultValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("attribute.name.generic.");
		sb.append(MOD_ID);
		sb.append(".");
		sb.append(name);
		Attribute attribute = new RangedAttribute(sb.toString(), defaultValue, 0.0D, 1024.0D);
		return DungeonsLibs.REGISTRAR.register(Registries.ATTRIBUTE, ResourceLocationHelper.modLoc(name),
				() -> attribute);
	}
	
	public static void init() {}
}
