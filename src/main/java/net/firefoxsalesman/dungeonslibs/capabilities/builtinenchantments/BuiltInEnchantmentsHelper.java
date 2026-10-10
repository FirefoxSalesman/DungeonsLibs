package net.firefoxsalesman.dungeonslibs.capabilities.builtinenchantments;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import static net.firefoxsalesman.dungeonslibs.capabilities.LibCapabilities.BUILT_IN_ENCHANTMENTS_CAPABILITY;

import java.util.List;
import java.util.Map;

public class BuiltInEnchantmentsHelper {
	public static BuiltInEnchantments getBuiltInEnchantmentsCapability(ItemStack itemStack) {
		return itemStack.getCapability(BUILT_IN_ENCHANTMENTS_CAPABILITY)
				.orElse(new BuiltInEnchantments(itemStack));
	}

	public static void addEnchantmentInstance(ItemStack itemStack, ResourceLocation source,
			EnchantmentInstance enchantmentInstance) {
		getBuiltInEnchantmentsCapability(itemStack).addBuiltInEnchantment(source, enchantmentInstance);
	}

	public static void removeBuiltInEnchantment(ItemStack itemStack, ResourceLocation source,
			Enchantment enchantment) {
		getBuiltInEnchantmentsCapability(itemStack).removeBuiltInEnchantment(source, enchantment);
	}

	public static boolean setBuiltInEnchantments(ItemStack itemStack, ResourceLocation source,
			List<EnchantmentInstance> enchantmentInstance) {
		return getBuiltInEnchantmentsCapability(itemStack).setBuiltInEnchantments(source, enchantmentInstance);
	}

	public static boolean clearAllBuiltInEnchantments(ItemStack itemStack, ResourceLocation source) {
		return getBuiltInEnchantmentsCapability(itemStack).clearAllBuiltInEnchantments(source);
	}

	public static List<EnchantmentInstance> getBuiltInEnchantments(ItemStack itemStack, ResourceLocation source) {
		return getBuiltInEnchantmentsCapability(itemStack).getBuiltInEnchantments(source);
	}

	public static List<EnchantmentInstance> getAllBuiltInEnchantmentInstances(ItemStack itemStack) {
		return getBuiltInEnchantmentsCapability(itemStack).getAllBuiltInEnchantmentInstances();
	}

	public static Map<ResourceLocation, List<EnchantmentInstance>> getAllBuiltInEnchantmentInstancesPerSource(
			ItemStack itemStack) {
		return getBuiltInEnchantmentsCapability(itemStack).getAllBuiltInEnchantmentInstancesPerSource();
	}

	public static boolean hasBuiltInEnchantment(ItemStack itemStack, ResourceLocation source) {
		return getBuiltInEnchantmentsCapability(itemStack).hasBuiltInEnchantment(source);
	}

	public static boolean hasBuiltInEnchantment(ItemStack itemStack) {
		return getBuiltInEnchantmentsCapability(itemStack).hasBuiltInEnchantment();
	}

	public static boolean hasBuiltInEnchantment(ItemStack itemStack, Enchantment enchantment) {
		return getBuiltInEnchantmentsCapability(itemStack).hasBuiltInEnchantment(enchantment);
	}

	public static int getBuiltInEnchantmentLevel(ItemStack itemStack, Enchantment enchantment) {
		return getBuiltInEnchantmentsCapability(itemStack).getBuiltInItemEnchantmentLevel(enchantment);
	}
}
