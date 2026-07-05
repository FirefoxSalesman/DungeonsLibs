package net.firefoxsalesman.dungeonslibs.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.firefoxsalesman.dungeonslibs.DungeonsLibs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsLibs.MOD_ID)
public class CommandEvents {
	@SubscribeEvent
	public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
		SoulsCommand.register(commandDispatcher);
		SummonEliteCommand.register(commandDispatcher, event.getBuildContext());
	}
}
