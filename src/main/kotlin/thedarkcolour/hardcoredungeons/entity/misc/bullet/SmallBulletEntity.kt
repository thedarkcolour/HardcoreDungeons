package thedarkcolour.hardcoredungeons.entity.misc.bullet

import net.minecraft.entity.EntityType
import net.minecraft.entity.IRendersAsItem
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.IndirectEntityDamageSource
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import thedarkcolour.hardcoredungeons.entity.misc.ProjectileEntity
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

        accelerationY -= drop

        if (ticksExisted == 100) {
            remove()
        }
    }

    override fun shoot(shooter: LivingEntity, x: Double, y: Double, z: Double, mX: Double, mY: Double, mZ: Double) {
        val gun = shooter.activeItemStack.item

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

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    override fun onImpact(result: RayTraceResult) {
        super.onImpact(result)

        if (!world.isRemote) {
            if (result.type == RayTraceResult.Type.ENTITY) {
                val shooter = shootingEntity
                val entity = (result as EntityRayTraceResult).entity
                val flag = entity.attackEntityFrom(IndirectEntityDamageSource("arrow", shooter, this), damage)

                if (flag && shooter != null) {
                    applyEnchantments(shooter, entity)
                }
                remove()
            } else if (result.type == RayTraceResult.Type.BLOCK) {
                // todo bounce
                remove()
                return
/*

                // bounce off of the surface and weaken damage
                damage *= 0.8f

                accelerationX = invertMotion(accelerationX)
                accelerationX = invertMotion(accelerationX)
                accelerationX = invertMotion(accelerationX) */
            }
        }
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