package thedarkcolour.hardcoredungeons.entity.projectile.magic

import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ItemSupplier
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.registry.HParticles
import thedarkcolour.hardcoredungeons.registry.items.CASTLE_GEM_ITEM

class MagicBoltEntity(type: EntityType<out ProjectileEntity>, worldIn: Level) : ProjectileEntity(type, worldIn), ItemSupplier {
    override fun getTrailParticle(): ParticleOptions {
        return HParticles.SOUL_FRAY
    }

    override fun onHitTarget(result: EntityHitResult, shooter: LivingEntity?, target: Entity) {
        if (!level().isClientSide) {
            if (target.hurt(level().damageSources().magic(), 7.0f) && shooter != null) {
                doEnchantDamageEffects(shooter, target)
            }
            discard()
        }
    }

    override fun onHitBlock(result: BlockHitResult) {
        super.onHitBlock(result)
        remove(RemovalReason.KILLED)

        val dx = random.nextGaussian() * 0.02
        val dy = random.nextGaussian() * 0.02
        val dz = random.nextGaussian() * 0.02
        val pos = position()

        for (i in 0..4) {
            level().addParticle(ParticleTypes.SMOKE, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, dx, dy, dz)
        }
    }

    override fun getItem(): ItemStack {
        return ICON
    }

    companion object {
        private val ICON = ItemStack(CASTLE_GEM_ITEM)
    }
}