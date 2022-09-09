package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class SootModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        val texture = gen.textureLoc(appearance) // use appearance

        gen.singleModel(block)
            .parent(gen.mcBlock("template_farmland"))
            .texture("particle", texture)
            .texture("dirt", texture)
            .texture("top", texture)
    }
}