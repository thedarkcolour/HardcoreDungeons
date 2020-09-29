package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.data.DataGenerator
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ExistingFileHelper
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks

class BlockModelGenerator(gen: DataGenerator, private val helper: ExistingFileHelper) : BlockStateProvider(gen, HardcoreDungeons.ID, helper) {
    override fun registerStatesAndModels() {
        simpleBlock(HBlocks.SHROOMY_COBBLESTONE)
        slab(HBlocks.SHROOMY_COBBLESTONE_SLAB, modLoc("block/shroomy_cobblestone"))
        stairsBlock(HBlocks.SHROOMY_COBBLESTONE_STAIRS, modLoc("block/shroomy_cobblestone"))
        //vase(HBlocks.CASTLETON_TREASURE_VASE)
        vase(HBlocks.VASE)
        vase(HBlocks.SHROOMY_VASE)
        vase(HBlocks.CASTLETON_VASE)

        simpleBlock(HBlocks.SHROOMY_STONE_BRICKS)
        stairsBlock(HBlocks.SHROOMY_STONE_BRICK_STAIRS, modLoc("block/shroomy_stone_bricks"))
        slab(HBlocks.SHROOMY_STONE_BRICK_SLAB, modLoc("block/shroomy_stone_bricks"))
    }

    private fun slab(block: SlabBlock, a: ResourceLocation) =
        slabBlock(block, a, a)

    private fun lantern(name: String, texture: ResourceLocation) =
        singleTexture(name, modLoc(BLOCK_FOLDER + "lantern"), "all", texture)

    private fun lanternHanging(name: String, texture: ResourceLocation) =
        singleTexture(name, modLoc(BLOCK_FOLDER + "hanging_lantern"), "all", texture)

    private fun singleTexture(name: String, parent: String, texture: ResourceLocation) =
        singleTexture(name, modLoc(parent), texture)

    private fun singleTexture(name: String, parent: ResourceLocation, texture: ResourceLocation) =
        singleTexture(name, parent, "texture", texture)

    private fun singleTexture(name: String, parent: ResourceLocation, textureKey: String, texture: ResourceLocation) =
        withExistingParent(name, parent).texture(textureKey, texture)

    private fun withExistingParent(name: String, parent: ResourceLocation) =
        getBuilder(name).parent(getExistingFile(parent))

    private fun getBuilder(path: String): BlockModelBuilder {
        val output = extendWithFolder(if (':' in path) ResourceLocation(path) else modLoc(path))

        return models().generatedModels.computeIfAbsent(output) { o ->
            BlockModelBuilder(o, helper)
        }
    }

    private fun vase(block: Block, texture: ResourceLocation = block.registryName!!) {
        val path = BLOCK_FOLDER + texture.path
        val model = singleTexture(path, modLoc("template_vase"), "vase", modLoc(path))

        getVariantBuilder(block)
            .partialState().addModels(ConfiguredModel(model))
    }

    private fun getExistingFile(parent: ResourceLocation) =
        ExistingModelFile(extendWithFolder(parent), helper).also(ExistingModelFile::assertExistence)

    private fun extendWithFolder(location: ResourceLocation): ResourceLocation {
        if ('/' in location.path) {
            return location
        }

        return ResourceLocation(location.namespace, BLOCK_FOLDER + location.path)
    }

    companion object {
        const val BLOCK_FOLDER = "block/"
    }
}
