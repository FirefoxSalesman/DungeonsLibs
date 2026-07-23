package net.firefoxsalesman.dungeonslibs.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonslibs.DungeonsLibs.MOD_ID;

import net.firefoxsalesman.dungeonslibs.client.particles.SnowflakeParticle;
import net.firefoxsalesman.dungeonslibs.init.ParticleInit;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleEventHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onParticleFactory(RegisterParticleProvidersEvent event) {
		register(ParticleInit.SNOWFLAKE.get(), SnowflakeParticle.Factory::new);
	}

	private static void register(ParticleType<SimpleParticleType> particleType,
			ParticleEngine.SpriteParticleRegistration<SimpleParticleType> factory) {
		Minecraft.getInstance().particleEngine.register(particleType, factory);
	}
}
