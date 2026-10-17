package net.firefoxsalesman.dungeonslibs.capabilities.ancient;

import org.jetbrains.annotations.NotNull;

import net.firefoxsalesman.dungeonslibs.capabilities.LibCapabilities;
import net.firefoxsalesman.dungeonslibs.utils.ResourceLocationHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class AttacherAncient {

	private static class AncientProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

		public static final ResourceLocation IDENTIFIER = ResourceLocationHelper.modLoc("ancient");
		private final Ancient backend = new Ancient();
		private final LazyOptional<Ancient> optionalData = LazyOptional.of(() -> backend);

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
			return LibCapabilities.ANCIENT_CAPABILITY.orEmpty(cap, this.optionalData);
		}

		@Override
		public CompoundTag serializeNBT() {
			return this.backend.serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			this.backend.deserializeNBT(nbt);
		}
	}

	public static void attach(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			final AncientProvider provider = new AncientProvider();
			event.addCapability(AncientProvider.IDENTIFIER, provider);
		}
	}
}
