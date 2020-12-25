package thedarkcolour.hardcoredungeons.block.decoration.misc

import net.minecraft.block.AbstractBlock

/** todo
 * Idea is from the Chisel mod
 * @param variants the type of block like "short_oak_planks", "braided_oak_planks"
 */
class VariantBlock(properties: AbstractBlock.Properties, variants: Array<String>) {
    //val block: Array<Block>
    //val stairs: Array<Block>
    //val slab: Array<Block>

    init {
        for (variant in variants) {

        }/*

        block = Block(properties)
        stairs = StairsBlock(block::getDefaultState, properties)
        slab = SlabBlock(properties)

        block.setRegistryName(names[0])
        stairs.setRegistryName(names[1])
        slab.setRegistryName(names[2])*/
    }

}