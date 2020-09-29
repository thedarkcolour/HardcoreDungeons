package thedarkcolour.hardcoredungeons.feature.structure

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Mirror
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.MutableBoundingBox
import net.minecraft.util.math.Vec3i
import net.minecraft.world.IWorld
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.Heightmap
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.gen.feature.structure.ScatteredStructure
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.feature.structure.Structure.IStartFactory
import net.minecraft.world.gen.feature.structure.StructureStart
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece
import net.minecraft.world.gen.feature.template.PlacementSettings
import net.minecraft.world.gen.feature.template.Template
import net.minecraft.world.gen.feature.template.TemplateManager
import net.minecraft.world.gen.placement.IPlacementConfig
import net.minecraft.world.gen.placement.Placement
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HStructures
import thedarkcolour.hardcoredungeons.registry.setRegistryKey
import thedarkcolour.hardcoredungeons.util.checkNot
import java.util.*
import kotlin.collections.ArrayList

/**
 * Simple structure template that will be used for many structures in the mod.
 *
 * Uses a Vec3i to ResourceLocation map to place structure pieces from NBT files.
 *
 * @author TheDarkColour
 */
class SimpleStructure(structureID: String, properties: Properties, addPieces: (MutableMap<Vec3i, ResourceLocation>) -> Unit) : ScatteredStructure<NoFeatureConfig>({ NoFeatureConfig.deserialize(it) }) {
    private val structureName = HardcoreDungeons.ID + structureID
    private val pieces: Map<Vec3i, ResourceLocation>

    // Properties
    private val minSeparation = properties.minSeparation
    private val maxSeparation = properties.maxSeparation
    private val seed = checkNot(properties.seed, 0)

    init {
        pieces = hashMapOf()
        addPieces(pieces)
        setRegistryKey(structureID)

        if (properties.biomes?.isNotEmpty() == true) {
            val stage = properties.genStage

            for (b in properties.biomes!!)  {
                addToBiome(b, stage)
            }
        }

        if (properties.addLandBelow) {
            // avoid using the ImmutableList
            if (Feature.ILLAGER_STRUCTURES !is ArrayList) Feature.ILLAGER_STRUCTURES = ArrayList(Feature.ILLAGER_STRUCTURES)
            Feature.ILLAGER_STRUCTURES.add(this)
        }
    }

    /**
     * Adds this structure to the specified [biome].
     *
     * @param biome The biome to add this structure to
     * @param stage The decoration stage this structure generates in
     */
    fun addToBiome(biome: Biome, stage: GenerationStage.Decoration = GenerationStage.Decoration.SURFACE_STRUCTURES) {
        biome.addStructure(this.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG))
        biome.addFeature(stage, this.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)))
    }

    override fun getBiomeFeatureSeparation(chunkGenerator: ChunkGenerator<*>): Int {
        return minSeparation
    }
    override fun getBiomeFeatureDistance(chunkGenerator: ChunkGenerator<*>): Int {
        return maxSeparation
    }

    /**
     * Legacy function that has no use in a forge environment.
     */
    override fun getSize() = 3

    /**
     * Start factory for building the structure from NBT.
     */
    override fun getStartFactory() = IStartFactory(::Start)

    /**
     * Namespaced registry name for use in the /locate command.
     */
    override fun getStructureName(): String {
        return structureName
    }

    /**
     * The unique seed for the placement of this structure.
     */
    override fun getSeedModifier(): Int {
        return seed
    }

    companion object {
        // structure name should match piece name
        fun single(structureID: String, properties: Properties): SimpleStructure {
            return SimpleStructure(structureID, properties) { pieces ->
                pieces[BlockPos.ZERO] = ResourceLocation(HardcoreDungeons.ID, structureID)
            }
        }
    }

    class Properties {
        var minSeparation = 32
        var maxSeparation = 8
        var seed = 0
        var addLandBelow = false
        var biomes: Array<out Biome>? = null
        var genStage = GenerationStage.Decoration.SURFACE_STRUCTURES

        fun validBiomes(stage: GenerationStage.Decoration, vararg biomes: Biome): Properties {
            if (biomes.isEmpty()) return this

            this.biomes = biomes
            this.genStage = stage

            return this
        }

        fun separation(range: IntRange): Properties {
            if (range.isEmpty()) throw IllegalArgumentException("Specified range $range is empty!")

            this.minSeparation = range.first
            this.maxSeparation = range.last

            return this
        }

        fun seed(seed: Int): Properties {
            this.seed = seed

            return this
        }

        // todo make this actually work by using Jigsaw pieces somehow
        fun addLandBelow(): Properties {
            this.addLandBelow = true

            return this
        }
    }

    // requires an instance reference to add component structure pieces
    // as they are stored in SimpleStructure and are not passed into the constructor
    inner class Start(structure: Structure<*>, posX: Int, posZ: Int, bounds: MutableBoundingBox, references: Int, seed: Long) :
        StructureStart(structure, posX, posZ, bounds, references, seed) {

        override fun init(generator: ChunkGenerator<*>, manager: TemplateManager, chunkX: Int, chunkZ: Int, biome: Biome) {
            val rotation = Rotation.values()[rand.nextInt(Rotation.values().size)]

            // bit shift * 16
            val x = (chunkX shl 4)
            val z = (chunkZ shl 4)

            val y = generator.getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG)

            // replace from the pieces passed into constructor
            var rotationOffset: BlockPos

            for (entry in pieces) {
                val offset = entry.key
                // x shl 5 = x * 32 because of piece sizes
                // and add integral position values
                rotationOffset = BlockPos(
                    (offset.x shl 5) + x,
                    (offset.y shl 5) + y,// - yOffset,
                    (offset.z shl 5) + z,
                )
                components.add(Piece(manager, entry.value, rotationOffset, rotation))
            }

            recalculateStructureSize()
        }
    }

    class Piece : TemplateStructurePiece {
        private val location: ResourceLocation
        private val pieceRotation: Rotation

        constructor(manager: TemplateManager, piece: ResourceLocation, pos: BlockPos, rotation: Rotation) : super(
            HStructures.SIMPLE_STRUCTURE_PIECE, 0) {
            this.location = piece
            this.templatePosition = pos.up()
            this.pieceRotation = rotation
            setupPiece(manager)
        }

        constructor(manager: TemplateManager, compound: CompoundNBT) : super(HStructures.SIMPLE_STRUCTURE_PIECE, compound) {
            this.location = ResourceLocation(compound.getString("Template"))
            this.pieceRotation = Rotation.valueOf(compound.getString("Rot"))
            setupPiece(manager)
        }

        private fun setupPiece(manager: TemplateManager) {
            val template = manager.getTemplateDefaulted(location)
            val settings = PlacementSettings().setRotation(pieceRotation).setMirror(Mirror.NONE)
            setup(template, templatePosition, settings)
        }

        override fun readAdditional(tagCompound: CompoundNBT) {
            super.readAdditional(tagCompound)
            tagCompound.putString("Template", location.toString())
            tagCompound.putString("Rot", pieceRotation.name)
        }

        override fun handleDataMarker(function: String, pos: BlockPos, worldIn: IWorld, rand: Random, sbb: MutableBoundingBox) {
            // for chests and stuff
        }

        override fun create(worldIn: IWorld, generator: ChunkGenerator<*>, rand: Random, box: MutableBoundingBox, pos: ChunkPos): Boolean {
            val settings = PlacementSettings().setRotation(pieceRotation).setMirror(Mirror.NONE)
            //val blockPos = BlockPos(0, 1, 0)
            templatePosition.add(Template.transformedBlockPos(settings, BlockPos.ZERO))

            return super.create(worldIn, generator, rand, box, pos)
        }
    }
}