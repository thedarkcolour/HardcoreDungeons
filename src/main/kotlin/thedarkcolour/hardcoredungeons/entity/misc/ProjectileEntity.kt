package thedarkcolour.hardcoredungeons.entity.misc

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.projectile.DamagingProjectileEntity
import net.minecraft.network.IPacket
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks
import thedarkcolour.hardcoredungeons.entity.HEntityType
import kotlin.math.sqrt

abstract class ProjectileEntity(
    type: EntityType<out ProjectileEntity>,
    worldIn: World,
) : DamagingProjectileEntity(type, worldIn), HEntityType.HEntity {

    var shootingEntity: LivingEntity?
        get() = func_234616_v_() as LivingEntity?
        set(shooter) = setShooter(shooter)

    fun shoot(shooter: LivingEntity, pos: Vector3d, acceleration: Vector3d) {
        this.shoot(shooter, pos.x, pos.y, pos.z, acceleration.x, acceleration.y, acceleration.z)
    }

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

    override fun onImpact(result: RayTraceResult) {
        super.onImpact(result)
    }

    override fun getAttributes(): AttributeModifierMap.MutableAttribute {
        return AttributeModifierMap.createMutableAttribute()
    }

    override fun createSpawnPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun isFireballFiery() = false
}