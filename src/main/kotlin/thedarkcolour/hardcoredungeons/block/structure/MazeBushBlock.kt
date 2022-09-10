package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.core.BlockPos
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class MazeBushBlock(properties: HProperties) : HBlock(properties) {
    override fun stepOn(worldIn: Level, pos: BlockPos, state: BlockState, entityIn: Entity) {
        if (entityIn is LivingEntity) {
            entityIn.hurt(DamageSource.CACTUS, 1.0f)
        }
        super.stepOn(worldIn, pos, state, entityIn)
    }
}