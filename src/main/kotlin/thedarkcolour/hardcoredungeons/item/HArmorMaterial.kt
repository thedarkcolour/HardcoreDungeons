package thedarkcolour.hardcoredungeons.item

import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import java.util.*

enum class HArmorMaterial(
    durability: Int,
    private val reduction: IntArray,
    private val enchantability: Int,
    private val soundEvent: SoundEvent,
    private val toughness: Float,
    private val knockbackReduction: Float,
    repairMaterial: () -> Ingredient
) : ArmorMaterial {
    TOWER(22, intArrayOf(2, 6, 7, 3), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.5f, Ingredient::EMPTY),
    SHROOMY(
        18,
        intArrayOf(3, 7, 7, 2),
        16,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        0.1f,
        0.0f,
        { Ingredient.of(net.minecraftforge.common.Tags.Items.MUSHROOMS) }
    )
    ;

    private val materialName = HardcoreDungeons.ID + ":" + name.lowercase(Locale.ROOT)
    private val repairMaterial by lazy(repairMaterial)
    private val maxDamage = intArrayOf(13 * durability, 15 * durability, 16 * durability, 11 * durability)

    override fun getDurabilityForType(slot: ArmorItem.Type) = maxDamage[slot.slot.index]
    override fun getDefenseForType(slot: ArmorItem.Type) = reduction[slot.slot.index]
    override fun getEnchantmentValue() = enchantability
    override fun getEquipSound() = soundEvent
    override fun getRepairIngredient(): Ingredient = repairMaterial
    override fun getName() = materialName
    override fun getToughness() = toughness
    override fun getKnockbackResistance() = knockbackReduction
}