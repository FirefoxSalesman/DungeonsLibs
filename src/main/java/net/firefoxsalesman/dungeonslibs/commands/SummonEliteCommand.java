package net.firefoxsalesman.dungeonslibs.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import net.firefoxsalesman.dungeonslibs.entities.elite.EliteMobEvents;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class SummonEliteCommand {

	private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(
			Component.translatable("commands.summonelite.failed"));
	private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(
			Component.translatable("commands.summonelite.failed.uuid"));
	private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(
			Component.translatable("commands.summonelite.invalidPosition"));

	public static void register(CommandDispatcher<CommandSourceStack> sourceStack,
			CommandBuildContext buildContext) {
		SummonCommandHelper.register(sourceStack, buildContext, ERROR_FAILED,
				ERROR_DUPLICATE_UUID, INVALID_POSITION, "summonelite",
				EliteMobEvents::makeElite);
	}
}
