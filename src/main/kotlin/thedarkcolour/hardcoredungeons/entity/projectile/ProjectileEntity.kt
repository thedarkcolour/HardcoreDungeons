package thedarkcolour.hardcoredungeons.entity.projectile

import com.mojang.math.Vector3d
import net.minecraft.network.protocol.Packet
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraftforge.network.NetworkHooks
import kotlin.math.sqrt

abstract class ProjectileEntity(
    type: EntityType<out ProjectileEntity>,
    level: Level,
) : AbstractHurtingProjectile(type, level)
{
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

        owner = shooter

        level.addFreshEntity(this)
    }

    final override fun onHit(result: HitResult) {
        super.onHit(result)
    }

    override fun onHitEntity(result: EntityHitResult) {
        val target = result.entity

        if (target != owner) {
            onHitTarget(result, owner as LivingEntity?, target)
        }
    }

    protected open fun onHitTarget(result: EntityHitResult, shooter: LivingEntity?, target: Entity) {

    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldBurn() = false
}