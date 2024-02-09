package xyz.kamefrede.betterpiercing.mixin;

import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Arrow;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

	@Shadow protected void hurtArmor(DamageSource $$0, float $$1) {}

	@Inject(method = "getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", at = @At("HEAD"), cancellable = true)
	private void addExtraCondition(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Float> callbackInfo) {
		if (damageSource.getDirectEntity() instanceof Arrow arrow) {
			if (arrow.shotFromCrossbow() && arrow.getPierceLevel() > 0) {
				LivingEntity entity = (LivingEntity)(Object)this;
				this.hurtArmor(damageSource, damageAmount);
				float absorbAmount = CombatRules.getDamageAfterAbsorb(damageAmount, (float)entity.getArmorValue(), (float)entity.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
				damageAmount = (float) (damageAmount - absorbAmount * (1 - 0.25 * arrow.getPierceLevel()));

				callbackInfo.setReturnValue(damageAmount);
			}
		}
	}
}
