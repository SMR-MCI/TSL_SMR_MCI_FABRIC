package com.tsl.tsl_smr_mci_fabric;

import com.tsl.tsl_smr_mci_fabric.commands.Check;
import com.tsl.tsl_smr_mci_fabric.commands.Status;
import com.tsl.tsl_smr_mci_fabric.commands.Submit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModMain implements ModInitializer {
	
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			Submit.registerCommand(dispatcher);
			Check.registerCommand(dispatcher);
			Status.registerCommand(dispatcher);
		});
		ModLogger.LOGGER.info("Mod Initialized");
	}
}
