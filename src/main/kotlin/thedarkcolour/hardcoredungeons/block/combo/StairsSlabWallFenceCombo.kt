package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.data.IFinishedRecipe
import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import java.util.function.Consumer

// Assumes this is a stone block
class StairsSlabWallFenceCombo(name: String, properties: HProperties) : StairsSlabWallCombo(name, properties) {
    val fence by BlockMaker.registerFence(BlockMaker.getComboName(name) + "_fence", properties)

    init {
        ItemMaker.blockItem(BlockMaker.getComboName(name) + "_fence", ItemModelType.WALL_FENCE_ITEM, ::fence)
    }

    override fun addTags(gen: BlockTagGenerator) {
        super.addTags(gen)

        gen.tag(BlockTags.FENCES).add(fence)
    }

    override fun addRecipes(consumer: Consumer<IFinishedRecipe>) {

    }
}