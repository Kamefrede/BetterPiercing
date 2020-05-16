package xyz.kamefrede.betterpiercing.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(DamageSource.class)
public class MixinDamageSource {

	@Inject(method = "arrow(Lnet/minecraft/entity/projectile/ProjectileEntity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/entity/damage/DamageSource;", at = @At(value = "RETURN"), cancellable = true)
	private static void changeCrossbowPiercingSource(ProjectileEntity projectile, Entity attacker, CallbackInfoReturnable<DamageSource> callbackInfoReturnable){
			DamageSource source = callbackInfoReturnable.getReturnValue();
			ProjectileEntity arrow = ((ProjectileEntity) source.getSource());
			if(arrow != null && arrow.isShotFromCrossbow() && arrow.getPierceLevel() > 0){
				DamageSourceAccessor accessor = (DamageSourceAccessor) source;
				accessor.setBypassesArmor(true);
				accessor.setExhaustion(0.0F);
				accessor.setUnblockable(true);
				callbackInfoReturnable.setReturnValue(source);
			}
	}
}
