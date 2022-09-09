package thedarkcolour.hardcoredungeons.data.modelgen.block

import net.minecraft.world.level.block.Block
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.core.Direction
import net.minecraftforge.client.model.generators.ConfiguredModel
import thedarkcolour.hardcoredungeons.block.portal.HPortalBlock
import thedarkcolour.hardcoredungeons.data.ModelGenerator

class PortalModelType : BlockModelType<HPortalBlock>() {
    override fun process(block: HPortalBlock, appearance: Block, gen: ModelGenerator) {
        val path = block.registryName!!.path
        val texture = gen.textureLoc(path)

        val ns = gen.blockModel(path + "_ns")
            .parent(gen.mcModel("block/nether_portal_ns"))
            .texture("particle", texture)
            .texture("portal", texture)
        val ew = gen.blockModel(path + "_ew")
            .parent(gen.mcModel("block/nether_portal_ew"))
            .texture("particle", texture)
            .texture("portal", texture)

        gen.getVariantBuilder(block)
            .partialState()
            .with(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X)
            .addModels(ConfiguredModel(gen.getModelFile(ns)))
            .partialState()
            .with(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z)
            .addModels(ConfiguredModel(gen.getModelFile(ew)))
    }
}