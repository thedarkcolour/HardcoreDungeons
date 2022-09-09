package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.block.FenceGateBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

// todo simplify
class FenceGateModelType : BlockModelType<FenceGateBlock>() {
    override fun process(block: FenceGateBlock, appearance: Block, gen: ModelGenerator) {
        // The actual block of this wall (oak, lumlight, castleton_brick)

        gen.fenceGateBlock(block, gen.textureLoc(appearance.registryName!!.path))
    }
}