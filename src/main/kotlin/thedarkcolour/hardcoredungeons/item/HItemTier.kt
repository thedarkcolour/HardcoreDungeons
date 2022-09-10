package thedarkcolour.hardcoredungeons.item

import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.ForgeTier
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.items.CANDY_CANE_ITEM
import thedarkcolour.hardcoredungeons.tags.HItemTags

object HItemTier {
    val CANDY_CANE = createTier(3, 726, 7.0f, 3.0f, 17, BlockTags.NEEDS_DIAMOND_TOOL, CANDY_CANE_ITEM)
    val MALACHITE = createTier(3, 1824, 8.0f, 2.0f, 18, BlockTags.NEEDS_DIAMOND_TOOL, HItemTags.GEMS_MALACHITE)
    val SHROOMY = createTier(2, 726, 7.0f, 3.0f, 17, BlockTags.NEEDS_IRON_TOOL, Tags.Items.MUSHROOMS)

    // Item repair ingredient
    fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int, harvestTag: TagKey<Block>, item: ItemLike): ForgeTier {
        return ForgeTier(level, durability, efficiency, damage, enchantability, harvestTag) { Ingredient.of(item.asItem()) }
    }

    // Tag repair ingredient
    private fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int, harvestTag: TagKey<Block>, repairTag: TagKey<Item>): ForgeTier {
        return ForgeTier(level, durability, efficiency, damage, enchantability, harvestTag) { Ingredient.of(repairTag) }
    }
}
