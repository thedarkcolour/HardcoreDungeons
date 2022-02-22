package thedarkcolour.hardcoredungeons.entity.projectile.bullet

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.IRendersAsItem
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IndirectEntityDamageSource
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.item.misc.GunItem
import thedarkcolour.hardcoredungeons.registry.HItems

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem::class)
class SmallBulletEntity(type: EntityType<out ProjectileEntity>, worldIn: World) : ProjectileEntity(type, worldIn), IRendersAsItem, IEntityAdditionalSpawnData {
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
            remove()
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Add ammoType argument")
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

    override fun onHitTarget(result: EntityRayTraceResult, shooter: LivingEntity?, target: Entity) {
        super.onHitTarget(result, shooter, target)

        if (!level.isClientSide) {
            // todo custom damage source + lang
            if (target.hurt(IndirectEntityDamageSource("arrow", shooter, this), damage) && shooter != null) {
                doEnchantDamageEffects(shooter, entity)
            }

            if (ammoType == INCENDIARY_BULLET) {
                target.setSecondsOnFire(10)
            }

            remove()
        }
    }

    override fun onHitBlock(p_230299_1_: BlockRayTraceResult) {
        super.onHitBlock(p_230299_1_)
        remove()
    }

    override fun readAdditionalSaveData(nbt: CompoundNBT) {
        super.readAdditionalSaveData(nbt)

        ammoType = nbt.getInt("AmmoType")
    }

    override fun addAdditionalSaveData(nbt: CompoundNBT) {
        super.addAdditionalSaveData(nbt)

        nbt.putInt("AmmoType", ammoType)
    }

    override fun writeSpawnData(buffer: PacketBuffer) {
        buffer.writeVarInt(ammoType)
    }

    override fun readSpawnData(buffer: PacketBuffer) {
        ammoType = buffer.readVarInt()
    }

    override fun getItem(): ItemStack {
        return when (ammoType) {
            INCENDIARY_BULLET -> INCENDIARY_BULLET_ICON
            else -> BULLET_ICON
        }
    }

    companion object {
        private val BULLET_ICON = ItemStack(HItems.BULLET)
        private val INCENDIARY_BULLET_ICON = ItemStack(HItems.INCENDIARY_BULLET)

        const val BULLET = 0
        const val INCENDIARY_BULLET = 1
    }
}