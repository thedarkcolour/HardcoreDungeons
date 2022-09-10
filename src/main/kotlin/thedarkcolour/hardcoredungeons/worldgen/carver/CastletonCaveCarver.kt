package thedarkcolour.hardcoredungeons.worldgen.carver

import net.minecraft.util.RandomSource
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver

// Sole reason for existence is to have wider caves
// Lava replacement level should be specified as negative,
// there will be a tag for replacing castleton-only blocks.
class CastletonCaveCarver : CaveWorldCarver(CaveCarverConfiguration.CODEC) {
    override fun getThickness(random: RandomSource): Float {
        return super.getThickness(random) * 1.2f
    }

    override fun getYScale(): Double {
        return 3.0
    }
}