package thedarkcolour.hardcoredungeons.structure

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Mirror
import net.minecraft.util.ResourceLocation
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.MutableBoundingBox
import net.minecraft.util.math.vector.Vector3i
import net.minecraft.util.registry.DynamicRegistries
import net.minecraft.world.ISeedReader
import net.minecraft.world.IServerWorld
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.Heightmap
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.feature.structure.Structure.IStartFactory
import net.minecraft.world.gen.feature.structure.StructureManager
import net.minecraft.world.gen.feature.structure.StructureStart
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece
import net.minecraft.world.gen.feature.template.PlacementSettings
import net.minecraft.world.gen.feature.template.Template
import net.minecraft.world.gen.feature.template.TemplateManager
import thedarkcolour.hardcoredungeons.registry.HStructures
import thedarkcolour.hardcoredungeons.registry.setRegistryKey
import thedarkcolour.hardcoredungeons.util.modLoc
import java.util.*

/**
 * Simple structure template that will be used for many structures in the mod.
 *
 * Uses a Vector3i to ResourceLocation map to place structure pieces from NBT files.
 *
 * @author TheDarkColour
 */
class SimpleStructure(structureID: String, addPieces: (MutableMap<Vector3i, ResourceLocation>) -> Unit) : Structure<NoFeatureConfig>(NoFeatureConfig.CODEC) {
    private val pieceMap: Map<Vector3i, ResourceLocation>

    init {
        pieceMap = hashMapOf()
        addPieces(pieceMap)

        setRegistryKey(structureID)
    }

    /**
     * Start factory for building the structure from NBT.
     */
    override fun getStartFactory() = IStartFactory(::Start)

    override fun step(): GenerationStage.Decoration {
        return GenerationStage.Decoration.SURFACE_STRUCTURES
    }

    companion object {
        // structure name should match piece name
        fun single(structureID: String): SimpleStructure {
            return SimpleStructure(structureID) { pieces ->
                pieces[BlockPos.ZERO] = modLoc(structureID)
            }
        }
    }

    // requires an instance reference to add component structure pieces
    // as they are stored in SimpleStructure and are not passed into the constructor
    inner class Start(structure: Structure<NoFeatureConfig>, posX: Int, posZ: Int, bounds: MutableBoundingBox, references: Int, seed: Long) : StructureStart<NoFeatureConfig>(structure, posX, posZ, bounds, references, seed) {
        override fun generatePieces(
            dynamicRegistry: DynamicRegistries,
            generator: ChunkGenerator,
            manager: TemplateManager,
            chunkX: Int,
            chunkZ: Int,
            biome: Biome,
            config: NoFeatureConfig,
        ) {
            val rotation = Rotation.values()[random.nextInt(Rotation.values().size)]
            // rand.nextInt(Rotation.values().size) // in case ive made some horrible mistake
            // bit shift * 16
            val x = (chunkX shl 4)
            val z = (chunkZ shl 4)

            val y = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG)

            // replace from the pieces passed into constructor
            var rotationOffset: BlockPos

            for ((key, value) in pieceMap) {
                val offset = when (rotation) {
                    Rotation.NONE -> key
                    Rotation.CLOCKWISE_90 -> BlockPos(-key.z, key.y, key.x)
                    Rotation.CLOCKWISE_180 -> BlockPos(-key.x, key.y, -key.z)
                    Rotation.COUNTERCLOCKWISE_90 -> BlockPos(key.z, key.y, -key.x)
                }
                // x shl 5 = x * 32 because of piece sizes
                // and add integral position values
                rotationOffset = BlockPos(
                    (offset.x shl 5) + x,
                    (offset.y shl 5) + y,// - yOffset,
                    (offset.z shl 5) + z,
                )
                pieces.add(Piece(manager, value, rotationOffset, rotation))
            }

            calculateBoundingBox()
        }
    }

    class Piece : TemplateStructurePiece {
        private val location: ResourceLocation
        private val pieceRotation: Rotation

        constructor(manager: TemplateManager, piece: ResourceLocation, pos: BlockPos, rotation: Rotation) : super(HStructures.SIMPLE_STRUCTURE_PIECE, 0) {
            this.location = piece
            this.templatePosition = pos.above()
            this.pieceRotation = rotation
            setupPiece(manager)
        }

        constructor(manager: TemplateManager, compound: CompoundNBT) : super(HStructures.SIMPLE_STRUCTURE_PIECE, compound) {
            this.location = ResourceLocation(compound.getString("Template"))
            this.pieceRotation = Rotation.valueOf(compound.getString("Rot"))
            setupPiece(manager)
        }

        private fun setupPiece(manager: TemplateManager) {
            val template = manager.getOrCreate(location)
            val settings = PlacementSettings().setRotation(pieceRotation).setMirror(Mirror.NONE)
            setup(template, templatePosition, settings)
        }

        override fun addAdditionalSaveData(tagCompound: CompoundNBT) {
            super.addAdditionalSaveData(tagCompound)
            tagCompound.putString("Template", location.toString())
            tagCompound.putString("Rot", pieceRotation.name)
        }

        override fun handleDataMarker(
            function: String,
            pos: BlockPos,
            worldIn: IServerWorld,
            rand: Random,
            sbb: MutableBoundingBox
        ) {
            // chests i think
            // todo add stuff here
        }

        override fun postProcess(
            worldIn: ISeedReader,
            manager: StructureManager,
            generator: ChunkGenerator,
            rand: Random,
            bounds: MutableBoundingBox,
            chunkPos: ChunkPos,
            blockPos: BlockPos
        ): Boolean {
            val settings = PlacementSettings().setRotation(pieceRotation).setMirror(Mirror.NONE)
            templatePosition.offset(Template.calculateRelativePosition(settings, BlockPos.ZERO))

            return super.postProcess(worldIn, manager, generator, rand, bounds, chunkPos, blockPos)
        }
    }
}