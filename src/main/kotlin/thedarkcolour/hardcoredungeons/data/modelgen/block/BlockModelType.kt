package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType

abstract class BlockModelType<B : Block> : ModelType<B>() {
    val blocks = hashMapOf<() -> B, () -> Block>()

    /**
     * Ran during model generation.
     */
    override fun generateModels(gen: ModelGenerator) {
        for ((block, appearance) in blocks) {
            // I don't want to put missing textures every time I load data
            try {
                process(block(), appearance(), gen)
            } catch (e: Exception) {
                println("Error generating ${block().registryName}: " + e.message)
                continue
            }
        }
    }

    /**
     * Generate a model for block [block].
     */
    abstract fun process(block: B, appearance: Block, gen: ModelGenerator)

    open fun add(block: () -> B, appearance: () -> Block) {
        blocks[block] = appearance
    }

    companion object {
        val CUBE_ALL = CubeAllModelType()
        val SLAB = SlabModelType()
        val CROSS = CrossModelType()
        val POTTED_PLANT = PottedPlantModelType()
        val STAIRS = StairsModelType()
        val WALL = WallModelType()
        val FENCE = FenceModelType()
        val FENCE_GATE = FenceGateModelType()
        val BUTTON = ButtonModelType()
        val ROTATED_PILLAR = PillarModelType()
        val DOOR = DoorModelType()
        val TRAPDOOR = TrapDoorModelType()
        val PRESSURE_PLATE = PressurePlateModelType()
        val FARMLAND = FarmlandModelType()
        val VASE_BLOCK = VaseModelType()
        val LANTERN = LanternModelType()
        val PORTAL = PortalModelType()
        val SOOT = SootModelType()
    }
}