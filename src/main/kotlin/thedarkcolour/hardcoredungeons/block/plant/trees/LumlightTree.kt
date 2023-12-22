package thedarkcolour.hardcoredungeons.block.plant.trees

import net.minecraft.resources.ResourceKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import thedarkcolour.hardcoredungeons.worldgen.HFeatures

object LumlightTree : AbstractTreeGrower() {
    override fun getConfiguredFeature(
        rand: RandomSource,
        hasBeehives: Boolean
    ): ResourceKey<ConfiguredFeature<*, *>> {
        return HFeatures.FANCY_LUMLIGHT_TREE.key!!
    }
}