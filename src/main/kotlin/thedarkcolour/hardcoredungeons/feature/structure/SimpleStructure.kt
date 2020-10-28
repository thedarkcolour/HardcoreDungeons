package thedarkcolour.hardcoredungeons.feature.structure

//import net.minecraft.world.gen.feature.structure.ScatteredStructure
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
class SimpleStructure(structureID: String, addPieces: (MutableMap<Vector3i, ResourceLocation>) -> Unit) : Structure<NoFeatureConfig>(NoFeatureConfig.field_236558_a_) {
    private val pieces: Map<Vector3i, ResourceLocation>

    init {
        pieces = hashMapOf()
        addPieces(pieces)

        setRegistryKey(structureID)
    }

    /**
     * Start factory for building the structure from NBT.
     */
    override fun getStartFactory() = IStartFactory(::Start)

    // todo make a property
    override fun func_236396_f_(): GenerationStage.Decoration {
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
    inner class Start(structure: Structure<NoFeatureConfig>, posX: Int, posZ: Int, bounds: MutableBoundingBox, references: Int, seed: Long) :
        StructureStart<NoFeatureConfig>(structure, posX, posZ, bounds, references, seed) {

        override fun func_230364_a_(
            dynamicRegistry: DynamicRegistries,
            generator: ChunkGenerator,
            manager: TemplateManager,
            chunkX: Int,
            chunkZ: Int,
            biome: Biome,
            config: NoFeatureConfig
        ) {
            val rotation = Rotation.values()[rand.nextInt(Rotation.values().size)]
            // rand.nextInt(Rotation.values().size) // in case ive made some horrible mistake
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

        override fun func_230383_a_(
            worldIn: ISeedReader,
            manager: StructureManager,
            generator: ChunkGenerator,
            rand: Random,
            bounds: MutableBoundingBox,
            chunkPos: ChunkPos,
            blockPos: BlockPos
        ): Boolean {
            val settings = PlacementSettings().setRotation(pieceRotation).setMirror(Mirror.NONE)
            templatePosition.add(Template.transformedBlockPos(settings, BlockPos.ZERO))

            return super.func_230383_a_(worldIn, manager, generator, rand, bounds, chunkPos, blockPos)
        }
    }
}