package thedarkcolour.hardcoredungeons.registry

import net.minecraft.core.Registry
import net.minecraft.data.BuiltinRegistries
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.NoiseRouterData
import net.minecraft.world.level.levelgen.NoiseSettings
import net.minecraft.world.level.levelgen.SurfaceRules
import thedarkcolour.hardcoredungeons.block.combo.GroundCombo
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

object HNoiseGeneratorSettings : HRegistry<NoiseGeneratorSettings>(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY) {
    private val CASTLETON_NOISE_SETTINGS = NoiseSettings(0, 256, 1, 2)

    val CASTLETON by register("castleton") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.CASTLETON_STONE.stone.block.defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            NoiseRouterData.overworld(BuiltinRegistries.DENSITY_FUNCTION, false, false),
            genericSurface(HBlocks.CASTLETON_SOIL),
            emptyList(),
            63,
            false,
            false,
            false,
            false
        )
    }

    val RAINBOWLAND by register("rainbowland") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.RAINBOW_ROCK.stone.block.defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            NoiseRouterData.overworld(BuiltinRegistries.DENSITY_FUNCTION, false, false),
            genericSurface(HBlocks.RAINBOW_SOIL),
            emptyList(),
            63,
            false,
            false,
            false,
            false
        )
    }

    val AUBRUM by register("aubrum") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.AUBRUM_ROCK.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            NoiseRouterData.overworld(BuiltinRegistries.DENSITY_FUNCTION, false, false),
            genericSurface(HBlocks.AURISOIL),
            emptyList(),
            63,
            false,
            false,
            false,
            false
        )
    }

    val CANDYLAND by register("candyland") {
        NoiseGeneratorSettings(
            CASTLETON_NOISE_SETTINGS,
            HBlocks.SUGARY_SOIL.soil.defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            NoiseRouterData.overworld(BuiltinRegistries.DENSITY_FUNCTION, false, false),
            genericSurface(HBlocks.SUGARY_SOIL),
            emptyList(),
            63,
            false,
            false,
            false,
            false
        )
    }

    private fun genericSurface(groundCombo: GroundCombo): SurfaceRules.RuleSource {
        // https://github.com/TheForsakenFurby/Surface-Rules-Guide-Minecraft-JE-1.18/blob/main/Guide.md#examples-7
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.ON_FLOOR,
                SurfaceRules.sequence(
                    SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(0, 0), SurfaceRules.state(groundCombo.grass.defaultBlockState())),
                    SurfaceRules.state(groundCombo.soil.defaultBlockState())
                )
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR,
                SurfaceRules.state(groundCombo.soil.defaultBlockState())
            )
        )
    }
}