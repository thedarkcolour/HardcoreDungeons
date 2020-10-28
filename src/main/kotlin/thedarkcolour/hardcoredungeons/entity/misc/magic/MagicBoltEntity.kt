package thedarkcolour.hardcoredungeons.entity.misc.magic

import net.minecraft.entity.EntityType
import net.minecraft.entity.IRendersAsItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.DamageSource
import net.minecraft.util.math.EntityRayTraceResult
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import thedarkcolour.hardcoredungeons.entity.misc.ProjectileEntity
import thedarkcolour.hardcoredungeons.registry.HParticles

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem::class)
class MagicBoltEntity(type: EntityType<out ProjectileEntity>, worldIn: World) : ProjectileEntity(type, worldIn), IRendersAsItem {

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
            val shooter = shootingEntity

            if (result is EntityRayTraceResult && result.entity != shooter) {
                val entity = result.entity
                val flag = entity.attackEntityFrom(DamageSource.MAGIC, 7.0f)

                if (flag && shooter != null) {
                    applyEnchantments(shooter, entity)
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

    override fun getItem(): ItemStack {
        return ICON
    }

    companion object {
        private val ICON = ItemStack(Items.MAGMA_CREAM)
    }
}