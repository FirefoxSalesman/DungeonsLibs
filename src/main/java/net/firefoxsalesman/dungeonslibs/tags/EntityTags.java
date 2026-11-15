package net.firefoxsalesman.dungeonslibs.tags;

import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTags {
	public static final TagKey<EntityType<?>> HOSTILE_EFFECT_WHITELIST = tag("hostile_effect_whitelist");
	public static final TagKey<EntityType<?>> HOSTILE_EFFECT_BLACKLIST = tag("hostile_effect_blacklist");

	private static TagKey<EntityType<?>> tag(String name) {
		return TagKey.create(Registries.ENTITY_TYPE, ResourceLocationHelper.modLoc(name));
	}
}
