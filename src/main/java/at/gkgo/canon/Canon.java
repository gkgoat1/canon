package at.gkgo.canon;

import at.gkgo.canon.api.blocknbt.BlockComponent;
import at.gkgo.canon.api.trigger.EventBusEntrypoint;
import at.gkgo.canon.api.trigger.Trigger;
import at.gkgo.canon.impl.blocknbt.net.BNSyncPacket;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canon implements ModInitializer, EventBusEntrypoint {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("canon");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		BNSyncPacket.init();
		BlockComponent.init();
//		TriggeredPacks.need();
	}

	@Override
	public void attachEvents(Trigger trigger) {

	}
}