package thedarkcolour.hardcoredungeons.registry

import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.Vec3i
import net.minecraft.util.math.vector.Vector3i
import net.minecraft.util.registry.MutableRegistry
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.structure.IStructurePieceType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.feature.structure.SimpleStructure
import thedarkcolour.hardcoredungeons.feature.structure.SimpleStructure.Properties

@Suppress("MemberVisibilityCanBePrivate")
object HStructures {
    val RAINBOW_FACTORY_PIECE_0 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_0")
    val RAINBOW_FACTORY_PIECE_1 = ResourceLocation(HardcoreDungeons.ID, "rainbow_factory_slice_1")

    val SIMPLE_STRUCTURE_PIECE = IStructurePieceType(SimpleStructure::Piece)

    val KNIGHTLY_SHRUBLAND_CABIN = SimpleStructure.single(
        "knightly_shrubland_cabin",
        Properties().seed(523332).separation(8..32).addLandBelow(),
    )
    val MUSHROOM_HUT = SimpleStructure.single(
        "mushroom_hut",
            Properties().seed(5234532).separation(4..16).addLandBelow(),
    )
    val RAINBOW_FACTORY = SimpleStructure(
        "rainbow_factory",
        Properties().seed(5223332).separation(8..32),
    ) { map ->
        map[Vector3i(0, 0, 0)] = RAINBOW_FACTORY_PIECE_0
        map[Vector3i(0, 0, 1)] = RAINBOW_FACTORY_PIECE_1
    }

    fun registerStructures(structures: IForgeRegistry<Feature<*>>) {
        structures.register(KNIGHTLY_SHRUBLAND_CABIN)
        structures.register(MUSHROOM_HUT)
        structures.register(RAINBOW_FACTORY)

        MUSHROOM_HUT.addToBiome(Biomes.MUSHROOM_FIELDS)
        MUSHROOM_HUT.addToBiome(Biomes.MUSHROOM_FIELD_SHORE)

        register(SIMPLE_STRUCTURE_PIECE, ResourceLocation(HardcoreDungeons.ID, "simple_structure_piece"))
    }

    private fun register(structurePieceType: IStructurePieceType, registryName: ResourceLocation) {
        (Registry.STRUCTURE_PIECE as MutableRegistry).register(registryName, structurePieceType)
    }
}