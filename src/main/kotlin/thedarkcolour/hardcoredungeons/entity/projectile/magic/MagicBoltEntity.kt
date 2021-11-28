package thedarkcolour.hardcoredungeons.entity.projectile.magic

import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.projectile.ItemSupplier
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.registry.HParticles

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier::class)
class MagicBoltEntity(type: EntityType<out ProjectileEntity>, level: Level) : ProjectileEntity(type, level), ItemSupplier {

    /**
     * Called to update the entity's position/logic.
     */
    override fun tick() {
        super.tick()

        if (tickCount == 40) {
            remove()
        }

        val d0 = random.nextGaussian() * 0.02
        val d1 = random.nextGaussian() * 0.02
        val d2 = random.nextGaussian() * 0.02
        val pos = position()

        for (i in 0..2) {
            level.addParticle(HParticles.SOUL_FRAY, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, d0, d1, d2)
        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    override fun onHit(result: HitResult) {
        super.onHit(result)
        if (!level.isClientSide) {
            val shooter = shootingEntity

            if (result is EntityHitResult && result.entity != shooter) {
                val entity = result.entity
                val flag = entity.hurt(DamageSource.MAGIC, 7.0f)

                if (flag && shooter != null) {
                    doEnchantDamageEffects(shooter, entity)
                }
            } else if (result.type == HitResult.Type.BLOCK) {
                remove(RemovalReason.DISCARDED)

                val d0 = random.nextGaussian() * 0.02
                val d1 = random.nextGaussian() * 0.02
                val d2 = random.nextGaussian() * 0.02
                val pos = position()

                for (i in 0..4) {
                    level.addParticle(HParticles.SOUL_FRAY, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, d0, d1, d2)
                }
            }
            setDeltaMovement(0.0, 0.0, 0.0)
        }
    }

    override fun getItem(): ItemStack {
        return ICON
    }

    companion object {
        private val ICON = ItemStack(Items.MAGMA_CREAM)
    }
}