package thedarkcolour.hardcoredungeons.block.misc

import net.minecraft.block.Block
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HBlocks.registerSimpleBlock
import thedarkcolour.hardcoredungeons.registry.setRegistryKey

/**
 * Represents a compressed block.
 * Has functions to easily manage each compressed variant.
 *
 * When instantiated, this class generates 8 HBlock instances for
 * 8 levels of compressed variants for block [block].
 *
 * @property block the the non-compressed variant
 *                 to use when generating compressed variants.
 * @property properties
 *
 * @author TheDarkColour
 */
class CompressedBlock(private val block: () -> Block, private val properties: HProperties?) {
    /**
     * Contains each compressed variant of [block].
     * The size of this array is always 8.
     */
    private lateinit var blockVariants: Array<HBlock>

    ///** Contains a BlockItem for each compressed block variant. */
    //private lateinit var itemVariants: Array<Item>

    /**
     * Registers all compressed variants to the given block registry,
     * then stores the compressed variants to the [blockVariants] array.
     */
    fun registerBlocks(registry: IForgeRegistry<Block>) {
        // get the non-compressed block
        val block = block()

        // get the most detailed block properties available
        val properties = properties ?: if (block is HBlock) {
            HProperties.from(block)
        } else HProperties.fromVanilla(block)

        // create the variants array
        blockVariants = Array(8) { i ->
            HBlock(properties).setRegistryKey(getCompressedName(i))
        }

        // register each variant
        for (variant in blockVariants) {
            // from HBlocks.kt
            registry.registerSimpleBlock(variant)
        }
    }

    /**
     * Returns the name for a compressed block
     * variant of the given [compressionLevel].
     *
     * Only used when creating the HBlock compressed variants.
     */
    private fun getCompressedName(compressionLevel: Int): String {
        return compressionLevelNames[compressionLevel] + block().registryName!!.path
    }

    ///**
    // * Registers a BlockItem instance for each
    // * compressed variant to the given item registry.
    // */
    //fun registerItems(registry: IForgeRegistry<Item>) {
    //    itemVariants = Array(8) { i ->
    //        HItems.getItemBlock(blockVariants[i])
    //    }
    //}

    /**
     * Returns the compressed [HBlock] variant for the given [compressionLevel].
     */
    fun getBlock(compressionLevel: Int): HBlock {
        return blockVariants[compressionLevel]
    }

    //fun getItem(compressionLevel: Int): Item {
    //    return itemVariants[compressionLevel]
    //}

    companion object {
        /** Prefixes for each level of compression. */
        private val compressionLevelNames = arrayOf(
            "compressed_",
            "double_compressed_",
            "triple_compressed_",
            "quadruple_compressed_",
            "quintuple_compressed_",
            "sextuple_compressed_",
            "septuple_compressed_",
            "octuple_compressed_"
        )
    }
}