package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.Registry
import net.minecraft.data.BuiltinRegistries
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.*
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

object HNoiseGeneratorSettings : HRegistry<NoiseGeneratorSettings>(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY) {
    private val CASTLETON_NOISE_SETTINGS = NoiseSettings(0, 256, 1, 2)

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

    val RAINBOWLAND by register("rainbowland") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.RAINBOW_ROCK.stone.block.defaultBlockState(),
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

    val AUBRUM by register("aubrum") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.AUBRUM_ROCK.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
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

    val CANDYLAND by register("candyland") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.SUGARY_SOIL.soil.defaultBlockState(),
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