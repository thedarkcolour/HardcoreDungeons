package thedarkcolour.hardcoredungeons.registry

import com.google.common.collect.ImmutableMap
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3i
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.structure.IStructurePieceType
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.settings.DimensionGeneratorSettings
import net.minecraft.world.gen.settings.DimensionStructuresSettings
import net.minecraft.world.gen.settings.StructureSeparationSettings
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.feature.structure.SimpleStructure
//import thedarkcolour.hardcoredungeons.feature.structure.SimpleStructure.Properties

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
object HStructures {
    val RAINBOW_FACTORY_PIECE_0 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_0")
    val RAINBOW_FACTORY_PIECE_1 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_1")

    val SIMPLE_STRUCTURE_PIECE: IStructurePieceType = IStructurePieceType.register(SimpleStructure::Piece, "hardcoredungeons:simple_structure_piece")

    val KNIGHTLY_SHRUBLAND_CABIN = SimpleStructure.single("knightly_shrubland_cabin")
    val MUSHROOM_HUT = SimpleStructure.single("mushroom_hut")
    val RAINBOW_FACTORY = SimpleStructure("rainbow_factory") { map ->
        map[Vector3i(0, 0, 0)] = RAINBOW_FACTORY_PIECE_0
        map[Vector3i(0, 0, 1)] = RAINBOW_FACTORY_PIECE_1
    }

    val MUSHROOM_HUT_FEATURE = MUSHROOM_HUT.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG)

    // hack DimensionStructuresSettings
    init {
        val a = ObfuscationReflectionHelper.findField(DimensionStructuresSettings::class.java, "field_236191_b_")
        val oldMap = a.get(null) as ImmutableMap<Structure<*>, StructureSeparationSettings>
        val builder = ImmutableMap.builder<Structure<*>, StructureSeparationSettings>()

        // add custom settings
        builder.put(MUSHROOM_HUT, StructureSeparationSettings(4, 16, 5234532))
        builder.put(RAINBOW_FACTORY, StructureSeparationSettings(8, 32, 5223332))
        builder.put(KNIGHTLY_SHRUBLAND_CABIN, StructureSeparationSettings(8, 32, 523332))

        a.set(null, builder.putAll(oldMap))
    }

    fun registerStructures(structures: IForgeRegistry<Structure<*>>) {
        structures.register(KNIGHTLY_SHRUBLAND_CABIN)
        structures.register(MUSHROOM_HUT)
        structures.register(RAINBOW_FACTORY)
    }
}