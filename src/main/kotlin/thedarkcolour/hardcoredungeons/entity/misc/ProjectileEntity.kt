package thedarkcolour.hardcoredungeons.entity.misc

import net.minecraft.entity.EntityType
import net.minecraft.entity.IRendersAsItem
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.DamagingProjectileEntity
import net.minecraft.network.IPacket
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.NetworkHooks
import kotlin.math.sqrt

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem::class)
abstract class ProjectileEntity(
    type: EntityType<out ProjectileEntity>,
    worldIn: World,
) : DamagingProjectileEntity(type, worldIn), IRendersAsItem {

    open fun shoot(shooter: LivingEntity, x: Double, y: Double, z: Double, mX: Double, mY: Double, mZ: Double) {
        setLocationAndAngles(x, y, z, rotationYaw, rotationPitch)
        setPosition(x, y, z)
        val d0 = sqrt(mX * mX + mY * mY + mZ * mZ)
        accelerationX = mX / d0 * 0.1
        accelerationY = mY / d0 * 0.1
        accelerationZ = mZ / d0 * 0.1

        shootingEntity = shooter

        world.addEntity(this)
    }

    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun isFireballFiery() = false
}