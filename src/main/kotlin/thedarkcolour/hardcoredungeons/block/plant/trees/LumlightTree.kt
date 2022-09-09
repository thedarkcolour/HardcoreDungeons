package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.core.Holder
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature

object LumlightTree : AbstractTreeGrower() {
    override fun getConfiguredFeature(
        rand: RandomSource,
        hasBeehives: Boolean
    ): Holder<out ConfiguredFeature<*, *>>? {
        TODO("not implemented")
    }
}