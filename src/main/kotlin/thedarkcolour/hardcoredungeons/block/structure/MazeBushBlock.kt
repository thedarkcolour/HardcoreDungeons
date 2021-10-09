package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class MazeBushBlock(properties: HProperties) : HBlock(properties) {
    override fun stepOn(worldIn: World, pos: BlockPos, entityIn: Entity) {
        if (entityIn is LivingEntity) {
            entityIn.hurt(DamageSource.CACTUS, 1.0f)
        }
        super.stepOn(worldIn, pos, entityIn)
    }
}