package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.core.BlockPos
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class SootTrapBlock(properties: HProperties) : HBlock(properties) {
    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (entity is LivingEntity) {
            entity.hurt(DamageSource.GENERIC, 5.0f)
        }
    }
}