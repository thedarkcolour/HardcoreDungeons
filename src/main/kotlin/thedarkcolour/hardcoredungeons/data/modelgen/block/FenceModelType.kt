package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FenceBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.registryName

// todo simplify
class FenceModelType : BlockModelType<FenceBlock>() {
    /**
     * Generate a model for block [block].
     */
    override fun process(block: FenceBlock, appearance: Block, gen: ModelGenerator) {
        // Path of the wall ("oak_fence")
        val path = block.registryName!!.path

        // The actual block of this wall (oak, lumlight, castleton_brick)
        var texture = path.removeSuffix("_fence")

        if (texture.endsWith("brick")) {
            // Corrects "castleton_brick" to "castleton_bricks"
            texture += "s"
        }/* else if (block.material == Material.WOOD || block.material == Material.NETHER_WOOD) {
            // Corrects "lumlight" to "lumlight_planks"
            texture += "_planks"
        }*/

        val textureLoc = gen.textureLoc(texture)

        gen.blockModel(path.toString() + "_inventory")
            .parent(gen.mcModel("block/fence_inventory"))
            .texture("texture", textureLoc)
        gen.fenceBlock(block, textureLoc)
    }
}