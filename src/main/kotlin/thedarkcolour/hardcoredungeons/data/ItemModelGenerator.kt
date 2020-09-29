package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ExistingFileHelper
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelFile
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems

/**
 * Item model generator for Hardcore Dungeons.
 *
 * @author TheDarkColour
 */
class ItemModelGenerator(
    generator: DataGenerator,
    existingFileHelper: ExistingFileHelper?
) : ItemModelProvider(generator, HardcoreDungeons.ID, existingFileHelper) {

    /**
     * Generate all your item models here.
     */
    override fun registerModels() {
        // Overworld blocks
        blockItemModel(HBlocks.VASE)
        blockItemModel(HBlocks.SHROOMY_COBBLESTONE)
        blockItemModel(HBlocks.SHROOMY_COBBLESTONE_SLAB)
        blockItemModel(HBlocks.SHROOMY_COBBLESTONE_STAIRS)
        blockItemModel(HBlocks.SHROOMY_STONE_BRICKS)
        blockItemModel(HBlocks.SHROOMY_STONE_BRICK_SLAB)
        blockItemModel(HBlocks.SHROOMY_STONE_BRICK_STAIRS)
        blockItemModel(HBlocks.SHROOMY_VASE)

        // Overworld items
        handHeldItem(HItems.SHROOMY_SWORD)

        // Castleton blocks
        blockItemModel(HBlocks.CASTLETON_SOIL)
        blockItemModel(HBlocks.CASTLETON_GRASS_BLOCK)
        blockItemModel(HBlocks.CASTLETON_STONE)
        blockItemModel(HBlocks.CASTLETON_LOAM)
        blockItemModel(HBlocks.CASTLETON_BRICKS)
        blockItemModel(HBlocks.CASTLETON_BRICK_STAIRS)
        blockItemModel(HBlocks.CASTLETON_BRICK_SLAB)
        blockItemModel(HBlocks.CRACKED_CASTLETON_BRICKS)
        blockItemModel(HBlocks.CHARGED_CASTLETON_BRICKS)
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    override fun getName(): String {
        return "Hardcore Dungeons Item Models"
    }

    /**
     * Generates an in-gui version of the block
     * that appears the same way it does in the world.
     */
    private fun blockItemModel(block: Block) {
        val name = block.registryName!!.path
        blockItemModel(name, name)
    }

    /**
     * Generates an in-gui version for the
     * block with the specified parent model.
     */
    private fun blockItemModel(path: String, parent: String) {
        getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(modLoc("block/$parent")))
    }

    private fun handHeldItem(item: Item) =
        handHeldItem(item.registryName!!.path)

    private fun handHeldItem(path: String) =
        singleLayerItem(path, mcLoc("item/handheld"))

    private fun singleLayerItem(path: String, parent: ResourceLocation) =
        getBuilder(path)
            .parent(ModelFile.UncheckedModelFile(parent))
            .texture("layer0", modLoc("item/$path"))
}