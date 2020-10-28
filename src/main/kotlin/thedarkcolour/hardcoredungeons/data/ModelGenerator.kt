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
import thedarkcolour.hardcoredungeons.block.decoration.StairsBlock
import thedarkcolour.hardcoredungeons.data.modelgen.ModelType

class ModelGenerator(gen: DataGenerator, private val helper: ExistingFileHelper) : BlockStateProvider(gen, HardcoreDungeons.ID, helper) {
    override fun registerStatesAndModels() {

        for (modelType in MODEL_TYPES) {
            modelType.generateModels(this)
        }

        // todo add
        //for ((s, e) in CUBE_COLUMNS) {
        //    column(s, e)
        //}
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
    private fun blockItemModel(path: String, parent: String) {
        itemModels().getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(modLoc("block/$parent")))
    }

    private fun stairs(stairs: Block, block: Block) {
        if (stairs is StairsBlock) {
            stairsBlock(stairs, ResourceLocation(HardcoreDungeons.ID, "block/" + block.registryName!!.path))
            blockItemModel(stairs)
        } else {
            throw IllegalArgumentException("Not a stair")
        }
    }



    private fun vase(vase: Block) {
        blockItemModel(vase)

        val path = vase.registryName!!.path
        val blockPath = blockLoc(path)
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
            .texture("end", blockLoc(end))
            .texture("side", blockLoc(path))

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

    fun blockLoc(b: Block, suffix: String? = null): ResourceLocation
            = blockLoc(b.registryName!!.path + (suffix ?: ""), namespace = b.registryName!!.namespace)

    fun blockLoc(path: String, namespace: String = HardcoreDungeons.ID): ResourceLocation
            = ResourceLocation(namespace, "block/$path")

    /**
     * Gets a model builder for the block.
     */
    private fun getBuilder(block: Block): BlockModelBuilder {
        val outputLoc = extendWithFolder(if (name.contains(":")) mcLoc(name) else modLoc(name))
        val b = models().generatedModels.computeIfAbsent(outputLoc) { k -> BlockModelBuilder(k, helper) }
        BLOCK_MODEL_LOOKUP[b] = outputLoc
        return b
    }

    fun mcFile(path: String): ModelFile {
        return ModelFile.UncheckedModelFile(mcLoc(path))
    }

    fun modFile(path: String): ModelFile {
        return ModelFile.UncheckedModelFile(modLoc(path))
    }

    /**
     * Creates a block model builder for json file [name].
     *
     * @param name the name of the .json file to be exported in modid/assets/models/block/
     */
    fun blockModel(name: String): BlockModelBuilder {
        val outputLoc = extendWithFolder(if (name.contains(":")) ResourceLocation(name) else modLoc(name))
        val model = models().generatedModels.computeIfAbsent(outputLoc) { k -> BlockModelBuilder(k, helper) }
        BLOCK_MODEL_LOOKUP[model] = outputLoc
        return model
    }

    private fun extendWithFolder(rl: ResourceLocation): ResourceLocation {
        val path = rl.path

        return if (path.contains('/')) {
            rl
        } else ResourceLocation(rl.namespace, "block/$path")
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

    // todo maybe some sort of ModelType system
    companion object {
        private val BLOCK_MODEL_LOOKUP = HashMap<BlockModelBuilder, ResourceLocation>()

        val MODEL_TYPES = ArrayList<ModelType<*>>()
    }
}