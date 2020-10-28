package thedarkcolour.hardcoredungeons.registry

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3i
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.gen.FlatGenerationSettings
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.gen.feature.structure.IStructurePieceType
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.settings.DimensionStructuresSettings
import net.minecraft.world.gen.settings.StructureSeparationSettings
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.feature.structure.SimpleStructure
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
object HStructures {
    val RAINBOW_FACTORY_PIECE_0 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_0")
    val RAINBOW_FACTORY_PIECE_1 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_1")

    val SIMPLE_STRUCTURE_PIECE: IStructurePieceType = IStructurePieceType.register(
        SimpleStructure::Piece,
        "hardcoredungeons:simple_structure_piece"
    )

    val LUMLIGHT_CABIN = structure(
        SimpleStructure.single("lumlight_cabin"),
        StructureSeparationSettings(16, 4, 5234532),
        true
    )
    val MUSHROOM_HUT = structure(
        SimpleStructure.single("mushroom_hut"),
        StructureSeparationSettings(32, 8, 5223332),
        true
    )
    val RAINBOW_FACTORY = structure(SimpleStructure("rainbow_factory") { map ->
        map[Vector3i(0, 0, 0)] = RAINBOW_FACTORY_PIECE_0
        map[Vector3i(0, 0, 1)] = RAINBOW_FACTORY_PIECE_1
    }, StructureSeparationSettings(32, 8, 523332), true)

    val MUSHROOM_HUT_FEATURE: StructureFeature<*, *> = register("mushroom_hut_feature", MUSHROOM_HUT.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG))
    val LUMLIGHT_CABIN_FEATURE: StructureFeature<*, *> = register("lumlight_cabin_feature", LUMLIGHT_CABIN.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG))
    val RAINBOW_FACTORY_FEATURE: StructureFeature<*, *> = register("rainbow_factory_feature", RAINBOW_FACTORY.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG))

    fun <C : IFeatureConfig> structure(
        structure: Structure<C>,
        separationSettings: StructureSeparationSettings,
        addLand: Boolean
    ): Structure<C> {
        Structure.field_236365_a_[structure.registryName.toString().toLowerCase(Locale.ROOT)] = structure

        if (addLand) {
            Structure.field_236384_t_ =
                ImmutableList.Builder<Structure<*>>()
                    .addAll(Structure.field_236384_t_)
                    .add(structure)
                    .build()
        }

        DimensionStructuresSettings.field_236191_b_ =
            ImmutableMap.builder<Structure<*>, StructureSeparationSettings>()
                .putAll(DimensionStructuresSettings.field_236191_b_)
                .put(structure, separationSettings)
                .build()

        return structure
    }

    fun registerStructures(structures: IForgeRegistry<Structure<*>>) {
        structures.register(LUMLIGHT_CABIN)
        structures.register(MUSHROOM_HUT)
        structures.register(RAINBOW_FACTORY)
    }

    fun addDimensionalSpacing(event: WorldEvent.Load) {
        if (event.world is ServerWorld) {
            val serverWorld = event.world as ServerWorld
            val tempMap = HashMap(serverWorld.chunkProvider.generator.func_235957_b_().func_236195_a_())

            tempMap[LUMLIGHT_CABIN] = DimensionStructuresSettings.field_236191_b_[LUMLIGHT_CABIN]
            tempMap[MUSHROOM_HUT] = DimensionStructuresSettings.field_236191_b_[MUSHROOM_HUT]
            tempMap[RAINBOW_FACTORY] = DimensionStructuresSettings.field_236191_b_[RAINBOW_FACTORY]

            serverWorld.chunkProvider.generator.func_235957_b_().field_236193_d_ = tempMap
        }
    }

    fun <FC : IFeatureConfig, F : Structure<FC>> register(name: String, feature: StructureFeature<FC, F>): StructureFeature<FC, F> {
        FlatGenerationSettings.STRUCTURES[feature.field_236268_b_] = feature
        return Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, ResourceLocation("hardcoredungeons", name), feature)
    }
}