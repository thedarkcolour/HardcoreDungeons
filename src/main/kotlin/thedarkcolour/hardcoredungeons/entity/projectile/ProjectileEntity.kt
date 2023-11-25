package thedarkcolour.hardcoredungeons.entity.projectile

import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
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
) : AbstractHurtingProjectile(type, level) {
    open fun shoot(
        shooter: LivingEntity,
        x: Double, y: Double, z: Double,
        vX: Double, vY: Double, vZ: Double,
    ) {
        moveTo(x, y, z, yRot, xRot)
        setPos(x, y, z)
        val d0 = sqrt(vX * vX + vY * vY + vZ * vZ)
        xPower = vX / d0 * 0.1
        yPower = vY / d0 * 0.1
        zPower = vZ / d0 * 0.1

        owner = shooter

        level().addFreshEntity(this)
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

    override fun getAddEntityPacket(): Packet<ClientGamePacketListener> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldBurn() = false
}