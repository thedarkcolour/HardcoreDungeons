package thedarkcolour.hardcoredungeons.item

import net.minecraft.item.IItemTier
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import net.minecraft.world.level.ItemLike
import net.minecraft.util.LazyValue
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.util.function.Supplier

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
 * @author thedarkcolour
 */
class HItemTier(
    private val harvestLevel: Int,
    private val durability: Int,
    private val efficiency: Float,
    private val damage: Float,
    private val enchantability: Int,
    private val repairMaterial: LazyValue<Ingredient>,
) : IItemTier {
    companion object {
        val CANDY_CANE = createTier(2, 726, 7.0f, 3.0f, 17, HItems::CANDY_CANE) // todo make unique
        val MALACHITE = createTier(3, 1824, 8.0f, 2.0f, 18, HItemTags.GEMS_MALACHITE)
        val VALABLADE = createTier(0, 1237, 5.0f, 3.0f, 26)
        val SHROOMY = createTier(3, 726, 7.0f, 3.0f, 17, Tags.Items.MUSHROOMS)

        fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int, item: ItemLike): HItemTier {
            return HItemTier(level, durability, efficiency, damage, enchantability) { Ingredient.of(item.asItem()) }
        }

        fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int, item: ITag<Item>): HItemTier {
            return HItemTier(level, durability, efficiency, damage, enchantability) { Ingredient.of(item) }
        }

        fun createTier(level: Int, durability: Int, efficiency: Float, damage: Float, enchantability: Int): HItemTier {
            return HItemTier(level, durability, efficiency, damage, enchantability, Ingredient::EMPTY)
        }
    }

    constructor(
        harvestLevel: Int,
        durability: Int,
        efficiency: Float,
        damage: Float,
        enchantability: Int,
        repairMaterial: Supplier<Ingredient>,
    ) : this(harvestLevel, durability, efficiency, damage, enchantability, LazyValue(repairMaterial))

    override fun getRepairIngredient(): Ingredient {
        return repairMaterial.get()
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