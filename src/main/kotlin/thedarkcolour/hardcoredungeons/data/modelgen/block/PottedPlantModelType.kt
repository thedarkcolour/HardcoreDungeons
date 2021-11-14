package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.client.model.generators.ModelProvider
import thedarkcolour.hardcoredungeons.data.ModelGenerator
import thedarkcolour.hardcoredungeons.util.modLoc

class PottedPlantModelType : BlockModelType<Block>() {
    override fun process(block: Block, appearance: Block, gen: ModelGenerator) {
        gen.simpleBlock(block, gen.models().potted(gen.name(block), modLoc(ModelProvider.BLOCK_FOLDER + "/" + block.registryName!!.path.removePrefix("potted_"))))
    }

    private fun BlockModelProvider.potted(name: String, cross: ResourceLocation): BlockModelBuilder? {
        return singleTexture(name, mcLoc(ModelProvider.BLOCK_FOLDER + "/flower_pot_cross"), "plant", cross)
    }
}