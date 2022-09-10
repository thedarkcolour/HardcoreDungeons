package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.ChatFormatting
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.RecipeGenerator
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.lines
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shaped
import thedarkcolour.hardcoredungeons.data.RecipeGenerator.Companion.shapeless
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.hardcoredungeons.util.registryName
import java.text.DecimalFormat
import java.util.function.Consumer
import kotlin.math.pow

class CompressedCombo(val block: () -> Block, private val properties: HProperties? = null) : ICombo {
    val variants = ArrayList<ObjectHolderDelegate<HBlock>>(8)

    init {
        for (i in 0..7) {
            variants.add(BlockMaker.cubeAllWithItem(getCompressedName(i)) {
                Variant(i + 1, properties ?: HProperties.copy(block()))
            })
        }
    }

    override fun addRecipes(consumer: Consumer<FinishedRecipe>) {
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
        val tooltip: Component

        init {
            val amount = 9.0.pow(compressionLevel.toDouble())
            val format = DecimalFormat("#,###")

            tooltip = Component.literal(format.format(amount) + " Blocks").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY))
        }

        override fun appendHoverText(
            stack: ItemStack,
            worldIn: BlockGetter?,
            tooltip: MutableList<Component>,
            flagIn: TooltipFlag,
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