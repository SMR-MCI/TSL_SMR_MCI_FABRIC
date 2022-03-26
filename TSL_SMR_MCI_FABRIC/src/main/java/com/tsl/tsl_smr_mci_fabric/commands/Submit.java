package com.tsl.tsl_smr_mci_fabric.commands;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tsl.tsl_smr_mci_fabric.githubinteraction.GithubIssue;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class Submit {
	
	//defines command params, and calls submitCommandExecuted when command executed 
	public static void submitCommand(CommandDispatcher<ServerCommandSource> dispatcher){
	    dispatcher.register(literal("submit")
	        .then(argument("url", string())
	        	.then(argument("description", greedyString())
	        		.executes(ctx -> submitCommandExecuted(ctx,getString(ctx, "url"),getString(ctx, "description")))))); // You can deal with the arguments out here and pipe them into the command.
	}
	
	
	//Calls when command executed
	public static int submitCommandExecuted(CommandContext<ServerCommandSource> ctx, String url, String description) throws CommandSyntaxException {
		final ServerCommandSource source = ctx.getSource();
	    final PlayerEntity player = source.getPlayer();
	    //create issue and store id
	    long id = GithubIssue.create("New site to add: "+url, description+"*Automated Issue - submitted by "+player.getDisplayName().asString()+" [UUID:"+player.getUuidAsString()+"]*", "addition");
	    //sends player a message
	    player.sendMessage(new LiteralText("Your, request has been added to Github! Track it with /status "+id),false);

	    return Command.SINGLE_SUCCESS; //return command executed successfully
	}
}
