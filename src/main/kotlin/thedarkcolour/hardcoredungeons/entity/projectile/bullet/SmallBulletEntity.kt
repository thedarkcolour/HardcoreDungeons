package thedarkcolour.hardcoredungeons.entity.projectile.bullet

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.damagesource.IndirectEntityDamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ItemSupplier
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraftforge.entity.IEntityAdditionalSpawnData
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.item.GunItem
import thedarkcolour.hardcoredungeons.registry.items.BULLET_ITEM
import thedarkcolour.hardcoredungeons.registry.items.INCENDIARY_BULLET_ITEM

class SmallBulletEntity(type: EntityType<out ProjectileEntity>, level: Level) : ProjectileEntity(type, level), ItemSupplier, IEntityAdditionalSpawnData {
    private var drop = 0.0f
    private var damage = 0.0f
    private var ammoType = 0

    /**
     * Called to update the entity's position/logic.
     */
    override fun tick() {
        super.tick()

        yPower -= drop

        if (tickCount == 100) {
            kill()
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Add ammoType argument")
    override fun shoot(
        shooter: LivingEntity,
        x: Double, y: Double, z: Double,
        mX: Double, mY: Double, mZ: Double,
    ) {
        throw IllegalStateException("Don't call this")
    }

    fun shoot(
        shooter: LivingEntity, ammoType: Int,
        x: Double, y: Double, z: Double,
        mX: Double, mY: Double, mZ: Double,
    ) {
        val gun = shooter.useItem.item

        this.ammoType = ammoType

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

    override fun onHitTarget(result: EntityHitResult, shooter: LivingEntity?, target: Entity) {
        super.onHitTarget(result, shooter, target)

        if (!level.isClientSide) {
            // todo custom damage source + lang
            if (target.hurt(IndirectEntityDamageSource("arrow", shooter, this), damage) && shooter != null) {
                doEnchantDamageEffects(shooter, target)
            }

            if (ammoType == INCENDIARY_BULLET) {
                target.setSecondsOnFire(10)
            }

            kill()
        }
    }

    override fun onHitBlock(p_230299_1_: BlockHitResult) {
        super.onHitBlock(p_230299_1_)
        kill()
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        super.readAdditionalSaveData(nbt)

        ammoType = nbt.getInt("AmmoType")
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        super.addAdditionalSaveData(nbt)

        nbt.putInt("AmmoType", ammoType)
    }

    override fun writeSpawnData(buffer: FriendlyByteBuf) {
        buffer.writeVarInt(ammoType)
    }

    override fun readSpawnData(buffer: FriendlyByteBuf) {
        ammoType = buffer.readVarInt()
    }

    override fun getItem(): ItemStack {
        return when (ammoType) {
            INCENDIARY_BULLET -> INCENDIARY_BULLET_ICON
            else -> BULLET_ICON
        }
    }

    companion object {
        private val BULLET_ICON = ItemStack(BULLET_ITEM)
        private val INCENDIARY_BULLET_ICON = ItemStack(INCENDIARY_BULLET_ITEM)

        const val BULLET = 0
        const val INCENDIARY_BULLET = 1
    }
}