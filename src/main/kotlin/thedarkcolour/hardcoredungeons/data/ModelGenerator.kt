package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.base.HStairsBlock
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType

class ModelGenerator(gen: DataGenerator, private val helper: ExistingFileHelper) : BlockStateProvider(gen, HardcoreDungeons.ID, helper) {
    override fun registerStatesAndModels() {
        for (modelType in MODEL_TYPES) {
            modelType.generateModels(this)
        }
    }

    fun name(block: Block): String {
        return block.registryName!!.path
    }

    /**
     * Generates an in-gui version of the block
     * that appears the same way it does in the world.
     */
    fun blockItemModel(block: Block) {
        val name = block.registryName!!.path
        blockItemModel(name, name)
    }

    /**
     * Generates an in-gui version for the
     * block with the specified parent model.
     *
     * @param path the path of the registry name for a model
     * @param parent the path of the parent model registry name
     */
    fun blockItemModel(path: String, parent: String) {
        itemModels().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(modLoc("block/$parent")))
    }

    // Adds a single block model to the state definition without any conditions.
    fun singleModel(block: Block): BlockModelBuilder {
        val builder = blockModel(block.registryName!!.path)

        getVariantBuilder(block)
            .partialState()
            .addModels(ConfiguredModel(builder))

        return builder
    }

    private fun stairs(stairs: Block, block: Block) {
        if (stairs is HStairsBlock) {
            stairsBlock(stairs, ResourceLocation(HardcoreDungeons.ID, "block/" + block.registryName!!.path))
            blockItemModel(stairs)
        } else {
            throw IllegalArgumentException("Not a stair")
        }
    }

    private fun vase(vase: Block) {
        blockItemModel(vase)

        val path = vase.registryName!!.path
        val blockPath = textureLoc(path)
        val f = ConfiguredModel.builder().modelFile(ModelFile.UncheckedModelFile(blockPath)).buildLast()

        models().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(modLoc("block/template_vase")))
            .texture("vase", blockPath)

        getVariantBuilder(vase)
            .partialState()
            .addModels(f)
    }

    private fun lantern(lantern: Block) {
        simpleItem(lantern)

        val path = lantern.registryName!!.path

        val normal = blockModel(path)
            .parent(ModelFile.UncheckedModelFile(mcLoc("block/lantern")))
            .texture("particle", path)
            .texture("all", path)
        val hanging = blockModel("hanging_$path")
            .parent(ModelFile.UncheckedModelFile(mcLoc("block/hanging_lantern")))
            .texture("particle", path)
            .texture("all", path)

        getVariantBuilder(lantern)
            .partialState()
            .with(BlockStateProperties.HANGING, true)
            .addModels(ConfiguredModel(getModelFile(hanging)))
            .partialState()
            .with(BlockStateProperties.HANGING, false)
            .addModels(ConfiguredModel(getModelFile(normal)))
    }

    private fun column(side: Block, end: Block) {
        blockItemModel(side)

        val path = side.registryName!!.path

        val m = models().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(mcLoc("block/cube_column")))
            .texture("end", textureLoc(end))
            .texture("side", textureLoc(path))

        getVariantBuilder(side)
            .partialState()
            .setModels(ConfiguredModel(getModelFile(m)))
    }

    fun simpleItem(item: IItemProvider) {
        val i = item.asItem()
        simpleItem(i.registryName!!.path)
    }

    private fun simpleItem(path: String) =
        itemModels().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(mcLoc("item/generated")))
            .texture("layer0", modLoc("item/$path"))

    // Fancy version of blockTexture
    fun textureLoc(block: Block, suffix: String? = null): ResourceLocation {
        return textureLoc(block.registryName!!.path + (suffix ?: ""), namespace = block.registryName!!.namespace)
    }

    /**
     * Gets a texture location in the blocks folder using
     * the given [path] and [namespace].
     */
    fun textureLoc(path: String, namespace: String = HardcoreDungeons.ID): ResourceLocation {
        return ResourceLocation(namespace, "block/$path")
    }

    /**
     * Gets a model builder for the block.
     */
    private fun getBuilder(block: Block): BlockModelBuilder {
        val outputLoc = intoBlockFolder(if (name.contains(":")) mcLoc(name) else modLoc(name))
        val b = models().generatedModels.computeIfAbsent(outputLoc) { k -> BlockModelBuilder(k, helper) }
        BLOCK_MODEL_LOOKUP[b] = outputLoc
        return b
    }

    fun mcModel(path: String): ModelFile {
        return ModelFile.UncheckedModelFile(mcLoc(path))
    }

    // Returns a vanilla model to be used as a model parent.
    fun mcBlock(path: String): ModelFile {
        return mcModel("block/$path")
    }

    fun modModel(path: String): ModelFile {
        return ModelFile.UncheckedModelFile(modLoc(path))
    }

    /**
     * Creates a block model builder for json file [name].
     * Name does not need 'block/' prefix.
     *
     * @param name the name of the .json file to be exported in modid/assets/models/block/
     */
    fun blockModel(name: String): BlockModelBuilder {
        val outputLoc = intoBlockFolder(if (name.contains(":")) ResourceLocation(name) else modLoc(name))
        val model = models().generatedModels.computeIfAbsent(outputLoc) { k -> BlockModelBuilder(k, helper) }
        BLOCK_MODEL_LOOKUP[model] = outputLoc
        return model
    }

    private fun intoBlockFolder(rl: ResourceLocation): ResourceLocation {
        val path = rl.path

        return if (path.contains('/')) rl else ResourceLocation(rl.namespace, "block/$path")
    }

    fun configure(b: BlockModelBuilder): ConfiguredModel {
        return ConfiguredModel(getModelFile(b))
    }

    fun getModelFile(builder: BlockModelBuilder): ModelFile {
        return ModelFile.UncheckedModelFile(BLOCK_MODEL_LOOKUP.computeIfAbsent(builder) {
            models().generatedModels.entries.first { (_, builder) ->
                builder == builder
            }.key
        })
    }

    companion object {
        private val BLOCK_MODEL_LOOKUP = HashMap<BlockModelBuilder, ResourceLocation>()

        val MODEL_TYPES = ArrayList<ModelType<*>>()
    }
}