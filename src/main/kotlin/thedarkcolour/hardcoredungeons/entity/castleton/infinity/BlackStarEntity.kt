package thedarkcolour.hardcoredungeons.entity.castleton.infinity

import net.minecraft.entity.EntityType
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.entity.HEntityType
import thedarkcolour.hardcoredungeons.entity.projectile.ProjectileEntity
import thedarkcolour.hardcoredungeons.registry.HEntities

class BlackStarEntity(
    type: EntityType<out ProjectileEntity> = HEntities.BLACK_STAR,
    worldIn: World
) : ProjectileEntity(type, worldIn), HEntityType.HEntity {
    override fun onHit(result: RayTraceResult) {
        super.onHit(result)
    }
}