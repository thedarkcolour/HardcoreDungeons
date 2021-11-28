package thedarkcolour.hardcoredungeons.item

import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags

/**
 * Item Tier enum class for Hardcore Dungeons items.
 *
 * @property harvestLevel the harvest level of this tool material
 * @property durability the durability of this tool material
 * @property efficiency the efficiency of this tool material
 * @property damage the attack damage of this tool material
 * @property enchantability the enchantability of this tool material
 * @property repairMaterial the lazily initialized repair material for this tool material
 *
 * @author TheDarkColour
 */
class HItemTier(
    private val harvestLevel: Int,
    private val durability: Int,
    private val efficiency: Float,
    private val damage: Float,
    private val enchantability: Int,
    private val repairMaterial: () -> Ingredient,
) : Tier {
    companion object {
        val CANDY_CANE = createTier(2, 726, 7.0f, 3.0f, 17, HItems::CANDY_CANE) // todo make unique
        val MALACHITE = createTier(3, 1824, 8.0f, 2.0f, 18, HItemTags.GEMS_MALACHITE)
        val VALABLADE = createTier(0, 1237, 5.0f, 3.0f, 26)
        val SHROOMY = createTier(3, 726, 7.0f, 3.0f, 17, Tags.Items.MUSHROOMS)

        fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int, item: ItemLike): HItemTier {
            return HItemTier(level, durability, efficiency, damage, enchantability) { Ingredient.of(item.asItem()) }
        }

        fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int, item: Tag<Item>): HItemTier {
            return HItemTier(level, durability, efficiency, damage, enchantability) { Ingredient.of(item) }
        }

        fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int): HItemTier {
            return HItemTier(level, durability, efficiency, damage, enchantability, Ingredient::EMPTY)
        }
    }

    override fun getRepairIngredient(): Ingredient {
        return repairMaterial()
    }

    override fun getLevel(): Int {
        return harvestLevel
    }

    override fun getUses(): Int {
        return durability
    }

    override fun getAttackDamageBonus(): Float {
        return damage
    }

    override fun getEnchantmentValue(): Int {
        return enchantability
    }

    override fun getSpeed(): Float {
        return efficiency
    }
}