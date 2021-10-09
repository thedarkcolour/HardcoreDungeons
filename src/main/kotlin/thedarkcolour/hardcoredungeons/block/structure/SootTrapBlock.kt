package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class SootTrapBlock(properties: HProperties) : HBlock(properties) {
    override fun stepOn(p_176199_1_: World?, p_176199_2_: BlockPos?, entity: Entity) {
        if (entity is LivingEntity) {
            entity.hurt(DamageSource.GENERIC, 5.0f)
        }
    }
}