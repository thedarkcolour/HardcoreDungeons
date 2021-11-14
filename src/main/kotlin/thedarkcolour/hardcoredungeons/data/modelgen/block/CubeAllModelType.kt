package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class CubeAllModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        // block state and model
        simpleBlock(gen, block, appearance)
        // item model
        gen.blockItemModel(block)
    }

    private fun simpleBlock(gen: ModelGenerator, block: Block, appearance: Block) {
        gen.getVariantBuilder(block).partialState().addModels(ConfiguredModel(gen.models().cubeAll(gen.name(block), gen.blockTexture(appearance))))
    }
}