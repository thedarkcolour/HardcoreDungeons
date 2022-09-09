package thedarkcolour.hardcoredungeons.worldgen.dimension

import net.minecraft.core.Registry
import net.minecraft.data.BuiltinRegistries
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.*
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HRegistry

object HNoiseGeneratorSettings : HRegistry<NoiseGeneratorSettings>(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY) {
    private val CASTLETON_NOISE_SETTINGS = NoiseSettings(-64, 384, 1, 2)

    val CASTLETON by register("castleton") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.CASTLETON_STONE.stone.block.defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            NoiseRouterData.overworld(BuiltinRegistries.DENSITY_FUNCTION, false, false),
            castletonSurface(),
            emptyList(),
            63,
            false,
            false,
            true,
            false
        )
    }

    private fun castletonSurface(): SurfaceRules.RuleSource {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(3), 0),
                SurfaceRules.ifTrue(
                    SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(4), 0)),
                    SurfaceRules.state(HBlocks.CASTLETON_SOIL.soil.defaultBlockState())
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(0), 0),
                SurfaceRules.ifTrue(
                    SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(1), 0)),
                    SurfaceRules.state(HBlocks.CASTLETON_SOIL.soil.defaultBlockState())
                )
            )
        )
    }
}