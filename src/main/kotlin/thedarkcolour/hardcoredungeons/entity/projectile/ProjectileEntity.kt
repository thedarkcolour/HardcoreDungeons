package thedarkcolour.hardcoredungeons.entity.projectile

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.projectile.DamagingProjectileEntity
import net.minecraft.network.IPacket
import net.minecraft.util.math.EntityRayTraceResult
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
        get() = getOwner() as LivingEntity?
        set(shooter) = setOwner(shooter)

    fun shoot(shooter: LivingEntity, pos: Vector3d, acceleration: Vector3d) {
        this.shoot(
            shooter,
            pos.x,
            pos.y,
            pos.z,
            acceleration.x,
            acceleration.y,
            acceleration.z
        )
    }

    open fun shoot(
        shooter: LivingEntity, x: Double,
        y: Double, z: Double, mX: Double,
        mY: Double, mZ: Double
    ) {
        moveTo(x, y, z, yRot, xRot)
        setPos(x, y, z)
        val d0 = sqrt(mX * mX + mY * mY + mZ * mZ)
        xPower = mX / d0 * 0.1
        yPower = mY / d0 * 0.1
        zPower = mZ / d0 * 0.1

        shootingEntity = shooter

        level.addFreshEntity(this)
    }

    final override fun onHit(result: RayTraceResult) {
        super.onHit(result)
    }

    override fun onHitEntity(result: EntityRayTraceResult) {
        if (result.entity != shootingEntity) {
            onHitTarget(result, shootingEntity, result.entity)
        }
    }

    protected open fun onHitTarget(result: EntityRayTraceResult, shooter: LivingEntity?, target: Entity) {

    }

    override fun getAttributes(): AttributeModifierMap.MutableAttribute {
        return AttributeModifierMap.builder()
    }

    override fun getAddEntityPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldBurn() = false
}