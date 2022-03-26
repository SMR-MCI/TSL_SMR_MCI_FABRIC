package com.tsl.tsl_smr_mci_fabric;

import com.tsl.tsl_smr_mci_fabric.commands.Submit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModMain implements ModInitializer {
	
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			Submit.submitCommand(dispatcher);
		});
		ModLogger.LOGGER.info("Mod Initialized");
	}
}
