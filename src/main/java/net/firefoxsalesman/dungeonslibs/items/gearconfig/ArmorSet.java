package net.firefoxsalesman.dungeonslibs.items.gearconfig;

import dev.obscuria.fragmentum.api.registry.Deferred;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ArmorSet {
	private final ResourceLocation registryName;
	private final Deferred<Item> head;
	private final Deferred<Item> chest;
	private final Deferred<Item> legs;
	private final Deferred<Item> feet;

	public ArmorSet(ResourceLocation registryName, Deferred<Item> head, Deferred<Item> chest,
			Deferred<Item> legs, Deferred<Item> feet) {
		this.registryName = registryName;
		this.head = head;
		this.chest = chest;
		this.legs = legs;
		this.feet = feet;
	}

	public ResourceLocation getRegistryName() {
		return registryName;
	}

	public Deferred<Item> getHead() {
		return head;
	}

	public Deferred<Item> getChest() {
		return chest;
	}

	public Deferred<Item> getLegs() {
		return legs;
	}

	public Deferred<Item> getFeet() {
		return feet;
	}
}
