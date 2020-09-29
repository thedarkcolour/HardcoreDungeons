package thedarkcolour.hardcoredungeons.entity.misc.magic

import net.minecraft.entity.EntityType
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.DamageSource
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.entity.misc.ProjectileEntity
import thedarkcolour.hardcoredungeons.registry.HParticles

class MagicBoltEntity(type: EntityType<out ProjectileEntity>, worldIn: World) : ProjectileEntity(type, worldIn) {

    /**
     * Called to update the entity's position/logic.
     */
    override fun tick() {
        super.tick()

        if (ticksExisted == 40) {
            remove()
        }

        val d0 = rand.nextGaussian() * 0.02
        val d1 = rand.nextGaussian() * 0.02
        val d2 = rand.nextGaussian() * 0.02
        val pos = position

        for (i in 0..2) {
            world.addParticle(HParticles.SOUL_FRAY, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, d0, d1, d2)
        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    override fun onImpact(result: RayTraceResult) {
        super.onImpact(result)
        if (!world.isRemote) {
            if (result is EntityRayTraceResult && result.entity != shootingEntity) {
                val entity = result.entity
                val flag = entity.attackEntityFrom(DamageSource.MAGIC, 7.0f)

                if (flag) {
                    applyEnchantments(shootingEntity, entity)
                }
            } else if (result.type == RayTraceResult.Type.BLOCK) {
                remove()

                val d0 = rand.nextGaussian() * 0.02
                val d1 = rand.nextGaussian() * 0.02
                val d2 = rand.nextGaussian() * 0.02
                val pos = position

                for (i in 0..4) {
                    world.addParticle(HParticles.SOUL_FRAY, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, d0, d1, d2)
                }
            }
            setMotion(0.0, 0.0, 0.0)
        }
    }

    override fun getItem() = ICON

    companion object {
        private val ICON = ItemStack(Items.MAGMA_CREAM)
    }
}