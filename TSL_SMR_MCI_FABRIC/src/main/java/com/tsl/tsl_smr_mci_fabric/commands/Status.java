package com.tsl.tsl_smr_mci_fabric.commands;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

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

public class Status {
	//defines command parameters, and calls statusCommandExecuted when command executed 
		public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher){
		    dispatcher.register(literal("status")
		        .then(argument("issue_number", integer())
		        	.executes(ctx -> statusCommandExecuted(ctx,getInteger(ctx, "issue_number")))));
		}
		
		public static int statusCommandExecuted(CommandContext<ServerCommandSource> ctx, int issueNum) throws CommandSyntaxException {
			final ServerCommandSource source = ctx.getSource();
		    final PlayerEntity player = source.getPlayer();
		    
		    //Gets the issue status
		    final String status = GithubIssue.getStatus(issueNum);
		    
		    if(!(status == null)) {
		    	final String url = GithubIssue.getUrl(issueNum);
		    	if(!(url == null)) {
		    		player.sendMessage(new LiteralText("Issue #"+issueNum+" is "+status+" Go to "+url+"to view the issue."),false);
		    	}
		    	//sends player a message if URL is empty
		    	else player.sendMessage(new LiteralText("There was an error creating a url for your issue. Your issues status is "+status),false);
		    }
		    
		    //sends player a message if status is empty
		    else player.sendMessage(new LiteralText("There was an error getting the issue. Check that you provided the correct issue number"),false);

		    return Command.SINGLE_SUCCESS; //return command executed successfully
		}
}
