package xyz.kamefrede.betterpiercing;

import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static xyz.kamefrede.betterpiercing.BetterPiercing.MOD_ID;

@Mod(MOD_ID)
public class BetterPiercing {

	public static final String MOD_ID = "betterpiercing";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public BetterPiercing(){
	}

	private void commonSetup(FMLCommonSetupEvent commonSetupEvent){
		LOGGER.info("Time to make crossbows great");
	}

	public static DamageSource changeCrossbowDamageSource(DamageSource source, AbstractArrowEntity entity){
		if(entity.getShotFromCrossbow() && entity.getPierceLevel() > 0){
			source.setDamageIsAbsolute();
			source.setDamageBypassesArmor();
		}
		return source;
	}

}
