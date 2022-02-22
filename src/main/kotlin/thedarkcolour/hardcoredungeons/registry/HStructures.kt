package thedarkcolour.hardcoredungeons.registry

import com.google.common.collect.ImmutableMap
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3i
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.structure.IStructurePieceType
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.settings.DimensionStructuresSettings
import net.minecraft.world.gen.settings.StructureSeparationSettings
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.structure.SimpleStructure
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
object HStructures {
    val RAINBOW_FACTORY_PIECE_0 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_0")
    val RAINBOW_FACTORY_PIECE_1 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_1")

    val SIMPLE_STRUCTURE_PIECE: IStructurePieceType = IStructurePieceType.setPieceId(SimpleStructure::Piece, "hardcoredungeons:simple_structure_piece")

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

    fun <C : IFeatureConfig> structure(
        structure: Structure<C>,
        separationSettings: StructureSeparationSettings,
        addLand: Boolean
    ): Structure<C> {
        Structure.STRUCTURES_REGISTRY[structure.registryName.toString().lowercase(Locale.ROOT)] = structure

        if (addLand) {
            Structure.NOISE_AFFECTING_FEATURES = ArrayList(Structure.NOISE_AFFECTING_FEATURES).also { list ->
                list.add(structure)
            }
        }

        DimensionStructuresSettings.DEFAULTS =
            ImmutableMap.builder<Structure<*>, StructureSeparationSettings>()
                .putAll(DimensionStructuresSettings.DEFAULTS)
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
            val tempMap = HashMap(serverWorld.chunkSource.generator.settings.structureConfig())

            tempMap[LUMLIGHT_CABIN] = DimensionStructuresSettings.DEFAULTS[LUMLIGHT_CABIN]
            tempMap[MUSHROOM_HUT] = DimensionStructuresSettings.DEFAULTS[MUSHROOM_HUT]
            tempMap[RAINBOW_FACTORY] = DimensionStructuresSettings.DEFAULTS[RAINBOW_FACTORY]

            serverWorld.chunkSource.generator.settings.structureConfig = tempMap
        }
    }
}