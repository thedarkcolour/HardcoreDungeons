package thedarkcolour.hardcoredungeons.block.decoration.misc

import net.minecraft.block.Block
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.block.HBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HBlocks.registerSimpleBlock
import thedarkcolour.hardcoredungeons.registry.setRegistryKey

/**
 * Idea is from the Chisel mod
 * @param variants the PREFIX of block ("short_oak_planks", "braided_oak_planks")
 */
class VariantBlock(
    properties: HProperties,
    base: String,
    vararg variants: String,
    hasBaseVariant: Boolean = true,
    blockSupplier: (HProperties) -> HBlock = ::HBlock,
    private val registerFunction: ((IForgeRegistry<Block>, Block) -> Unit)? = null
) {
    val variants: Array<Block> = Array(if (hasBaseVariant) variants.size + 1 else variants.size) { i ->
        val registryName = if (hasBaseVariant && i == 0) {
            base
        } else {
            variants[if (hasBaseVariant) i - 1 else i] + "_" + base
        }
        blockSupplier(properties).setRegistryKey(registryName)
    }

    fun registerBlocks(registry: IForgeRegistry<Block>) {
        val registerFunction = registerFunction

        for (variant in variants) {
            if (registerFunction != null) {
                registerFunction(registry, variant)
            } else {
                registry.registerSimpleBlock(variant)
            }
        }
    }
}