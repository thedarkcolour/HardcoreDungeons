package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.block.Block
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.data.IFinishedRecipe
import net.minecraft.world.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.IBlockReader
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.RecipeGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.lines
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shaped
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shapeless
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.text.DecimalFormat
import java.util.function.Consumer
import kotlin.math.pow

class CompressedCombo(val block: () -> Block, private val properties: HProperties? = null) : ICombo {
    val variants = ArrayList<ObjectHolderDelegate<HBlock>>(8)

    init {
        for (i in 0..7) {
            variants.add(HBlockMaker.cubeAllWithItem(getCompressedName(i)) {
                Variant(i + 1, properties ?: HProperties.copy(block()))
            })
        }

    }

    override fun addRecipes(consumer: Consumer<IFinishedRecipe>) {
        val variants = variants

        variants.forEachIndexed { i, obj ->
            val previous = if (i == 0) block() else variants[i - 1]()
            val predicate = if (i == 0) RecipeGenerator.has(Tags.Items.COBBLESTONE) else RecipeGenerator.has(previous)
            val block = obj()

            consumer.shaped(block, 1) { builder ->
                builder.define('#', previous)
                builder.lines("###", "###", "###")
                builder.unlockedBy("has_previous", predicate)
            }

            consumer.shapeless(previous, 9, modLoc(RecipeGenerator.path(previous) + "_from_" + RecipeGenerator.path(block))) { builder ->
                builder.requires(block)
                builder.unlockedBy("has_item", predicate)
            }
        }
    }

    private fun getCompressedName(compressionLevel: Int): String {
        return compressionLevelNames[compressionLevel] + block().registryName!!.path
    }

    private class Variant(compressionLevel: Int, properties: HProperties) : HBlock(properties) {
        val tooltip: ITextComponent

        init {
            val amount = 9.0.pow(compressionLevel.toDouble())
            val format = DecimalFormat("#,###")

            tooltip = StringTextComponent(format.format(amount) + " Blocks").setStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY))
        }

        override fun appendHoverText(
            stack: ItemStack,
            worldIn: IBlockReader?,
            tooltip: MutableList<ITextComponent>,
            flagIn: ITooltipFlag,
        ) {
            tooltip.add(this.tooltip)
        }
    }

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