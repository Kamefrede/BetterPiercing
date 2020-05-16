package xyz.kamefrede.betterpiercing;

import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;


import static xyz.kamefrede.betterpiercing.BetterPiercing.MOD_ID;

@Mod(MOD_ID)
public class BetterPiercing {

	public static final String MOD_ID = "betterpiercing";

	public BetterPiercing(){
		MinecraftForge.EVENT_BUS.addListener(this::setAdditionalPiercingDamage);
	}


	public void setAdditionalPiercingDamage(LivingHurtEvent event){
		DamageSource source = event.getSource();
		if(source.getImmediateSource() instanceof AbstractArrowEntity && ((AbstractArrowEntity) source.getImmediateSource()).getPierceLevel() > 0){
			event.getSource().setDamageIsAbsolute();
		}
	}
}
