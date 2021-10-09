package thedarkcolour.hardcoredungeons.entity.projectile.magic

import net.minecraft.entity.EntityType
import net.minecraft.entity.IRendersAsItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.DamageSource
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.registry.HParticles

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem::class)
class MagicBoltEntity(type: EntityType<out ProjectileEntity>, worldIn: World) : ProjectileEntity(type, worldIn), IRendersAsItem {

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
    override fun onHit(result: RayTraceResult) {
        super.onHit(result)
        if (!level.isClientSide) {
            val shooter = shootingEntity

            if (result is EntityRayTraceResult && result.entity != shooter) {
                val entity = result.entity
                val flag = entity.hurt(DamageSource.MAGIC, 7.0f)

                if (flag && shooter != null) {
                    doEnchantDamageEffects(shooter, entity)
                }
            } else if (result.type == RayTraceResult.Type.BLOCK) {
                remove()

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