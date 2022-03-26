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
import com.tsl.tsl_smr_mci_fabric.githubinteraction.IllegalSiteChecker;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class Submit {
	
	//defines command parameters, and calls submitCommandExecuted when command executed 
	public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher){
	    dispatcher.register(literal("submit")
	        .then(argument("url", string())
	        	.then(argument("description", greedyString())
	        		.executes(ctx -> submitCommandExecuted(ctx,getString(ctx, "url"),getString(ctx, "description"))))));
	}
	
	
	//Calls when command executed
	public static int submitCommandExecuted(CommandContext<ServerCommandSource> ctx, String url, String description) throws CommandSyntaxException {
		final ServerCommandSource source = ctx.getSource();
	    final PlayerEntity player = source.getPlayer();
	    
	    if(IllegalSiteChecker.check(url)) {player.sendMessage(new LiteralText("This site is already on our lists!"),false);return Command.SINGLE_SUCCESS;}
	    
	    //create issue and store id
	    int issueNumber = GithubIssue.create("New site to add: "+url, description+" *Automated Issue - submitted by "+player.getDisplayName().asString()+" [UUID:"+player.getUuidAsString()+"]*", "addition");
	    
	    //sends player a message
	    if(issueNumber!=-1) player.sendMessage(new LiteralText("Your request has been added to Github! Track it with /status "+issueNumber),false);
	    else player.sendMessage(new LiteralText("Failed to add your request to github. Try again or contact mod author (tsl.smr.mci@gmail.com) if issue still occuring"),false);

	    return Command.SINGLE_SUCCESS; //return command executed successfully
	}
}
