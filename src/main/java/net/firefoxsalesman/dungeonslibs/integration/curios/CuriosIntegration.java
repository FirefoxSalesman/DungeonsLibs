package net.firefoxsalesman.dungeonslibs.integration.curios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosIntegration {

	public static final String ARTIFACT_IDENTIFIER = "artifact";

	public static List<ItemStack> getArtifacts(LivingEntity livingEntity) {
		LazyOptional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosInventory(livingEntity);
		if (curiosHandler.isPresent()) {
			Optional<ICurioStacksHandler> artifactStackHandler = curiosHandler.resolve().get()
					.getStacksHandler(ARTIFACT_IDENTIFIER);
			if (artifactStackHandler.isPresent()) {
				IDynamicStackHandler stacks = artifactStackHandler.get().getStacks();
				List<ItemStack> artifacts = new ArrayList<>();
				for (int i = 0; i < stacks.getSlots(); i++) {
					artifacts.add(stacks.getStackInSlot(i));
				}
				return artifacts;
			}
		}
		return Collections.emptyList();
	}

}
