package com.tsl.tsl_smr_mci_fabric.commands;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tsl.tsl_smr_mci_fabric.githubinteraction.IllegalSiteChecker;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class Check {
	
	//defines command parameters, and calls submitCommandExecuted when command executed 
	public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher){
	    dispatcher.register(literal("check")
	        .then(argument("url", string())
	        	.executes(ctx -> checkCommandExecuted(ctx,getString(ctx, "url")))));
	}
	
	public static int checkCommandExecuted(CommandContext<ServerCommandSource> ctx, String url) throws CommandSyntaxException {
		final ServerCommandSource source = ctx.getSource();
	    final PlayerEntity player = source.getPlayer();
	    final boolean isOnList = IllegalSiteChecker.check(url);
	    if(isOnList) player.sendMessage(new LiteralText("✔ "+url+" is on our list"),false);
	    else player.sendMessage(new LiteralText("✗ "+url+" is not on out list"),false);
		return Command.SINGLE_SUCCESS; //return command executed successfully
	}

}
