package thedarkcolour.hardcoredungeons.item

import net.minecraft.item.IItemTier
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.LazyValue
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.HItems
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
 * @author TheDarkColour
 */
enum class HItemTier(
    private val harvestLevel: Int,
    private val durability: Int,
    private val efficiency: Float,
    private val damage: Float,
    private val enchantability: Int,
    private val repairMaterial: LazyValue<Ingredient>
) : IItemTier {
    CANDY_CANE(4, 726, 7.0f, 3.0f, 17, { Ingredient.fromItems(HItems.CANDY_CANE) }), // todo make unique
    SHROOMY(4, 726, 7.0f, 3.0f, 17, { Ingredient.fromTag(Tags.Items.MUSHROOMS) });

    constructor(
        harvestLevel: Int,
        durability: Int,
        efficiency: Float,
        damage: Float,
        enchantability: Int,
        repairMaterial: Supplier<Ingredient>
    ) : this(harvestLevel, durability, efficiency, damage, enchantability, LazyValue(repairMaterial))

    override fun getRepairMaterial(): Ingredient {
        return repairMaterial.value
    }

    override fun getHarvestLevel(): Int {
        return harvestLevel
    }

    override fun getMaxUses(): Int {
        return durability
    }

    override fun getAttackDamage(): Float {
        return damage
    }

    override fun getEnchantability(): Int {
        return enchantability
    }

    override fun getEfficiency(): Float {
        return efficiency
    }
}