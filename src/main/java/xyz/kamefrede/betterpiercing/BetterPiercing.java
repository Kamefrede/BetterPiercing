package xyz.kamefrede.betterpiercing;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterPiercing implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("betterpiercing");
	@Override
	public void onInitialize() {
		LOGGER.info("Time to make Crossbows great!");
	}
}
