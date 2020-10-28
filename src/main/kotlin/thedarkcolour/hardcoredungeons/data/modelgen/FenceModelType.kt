package thedarkcolour.hardcoredungeons.data.modelgen

import net.minecraft.block.FenceBlock
import net.minecraft.block.material.Material
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class FenceModelType : BlockModelType<FenceBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: FenceBlock, gen: ModelGenerator) {

        // Path of the wall ("oak_fence")
        val path = block.registryName!!.path

        // The actual block of this wall (oak, lumlight, castleton_brick)
        var base = path.removeSuffix("_fence")

        if (base.endsWith("brick")) {
            // Corrects "castleton_brick" to "castleton_bricks"
            base += "s"
        } else if (block.material == Material.WOOD || block.material == Material.NETHER_WOOD) {
            // Corrects "lumlight" to "lumlight_planks"
            base += "_planks"
        }

        val baseLoc = gen.blockLoc(base)

        gen.fenceBlock(block, baseLoc)
    }
}