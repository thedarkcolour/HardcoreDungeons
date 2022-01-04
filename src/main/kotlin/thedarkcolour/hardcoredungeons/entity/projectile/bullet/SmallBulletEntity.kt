package thedarkcolour.hardcoredungeons.entity.projectile.bullet

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.IRendersAsItem
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.IndirectEntityDamageSource
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.item.misc.GunItem
import thedarkcolour.hardcoredungeons.registry.HItems

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem::class)
class SmallBulletEntity(type: EntityType<out ProjectileEntity>, worldIn: World) : ProjectileEntity(type, worldIn), IRendersAsItem {
    private var drop = 0.0f
    private var damage = 0.0f

    /**
     * Called to update the entity's position/logic.
     */
    override fun tick() {
        super.tick()

        yPower -= drop

        if (tickCount == 100) {
            remove()
        }
    }

    override fun shoot(shooter: LivingEntity, x: Double, y: Double, z: Double, mX: Double, mY: Double, mZ: Double) {
        val gun = shooter.useItem.item

        if (gun is GunItem) {
            applyProperties(gun)

            super.shoot(shooter, x, y, z, mX * gun.velocity, mY * gun.velocity, mZ * gun.velocity)
        } else {
            super.shoot(shooter, x, y, z, mX, mY, mZ)
        }
    }

    private fun applyProperties(gun: GunItem) {
        drop = gun.drop
        damage = gun.bulletDamage
    }

    override fun onHitTarget(result: EntityRayTraceResult, shooter: LivingEntity?, target: Entity) {
        super.onHitTarget(result, shooter, target)

        if (!level.isClientSide) {
            // todo custom damage source + lang
            if (target.hurt(IndirectEntityDamageSource("arrow", shooter, this), damage) && shooter != null) {
                doEnchantDamageEffects(shooter, entity)
            }

            if (remainingFireTicks > 0) {
                target.setSecondsOnFire(remainingFireTicks)
            }

            remove()
        }
    }

    override fun onHitBlock(p_230299_1_: BlockRayTraceResult) {
        super.onHitBlock(p_230299_1_)
        remove()
        /* todo ricochet

                // bounce off of the surface and weaken damage
                damage *= 0.8f

                accelerationX = invertMotion(accelerationX)
                accelerationX = invertMotion(accelerationX)
                accelerationX = invertMotion(accelerationX) */
    }

    private fun invertMotion(motion: Double): Double {
        return -1.0 / motion
    }

    override fun getItem(): ItemStack {
        return ICON
    }

    companion object {
        private val ICON = ItemStack(HItems.BULLET)
    }
}