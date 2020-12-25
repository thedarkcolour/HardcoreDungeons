package thedarkcolour.hardcoredungeons.item

import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.LazyValue
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
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
) : IArmorMaterial {
    TOWER(22, intArrayOf(2, 6, 7, 3), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.5f, Ingredient::EMPTY),
    SHROOMY(18, intArrayOf(3, 7, 7, 2), 16, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.1f, 0.0f, { Ingredient.fromTag(net.minecraftforge.common.Tags.Items.MUSHROOMS) })
    ;

    private val materialName = HardcoreDungeons.ID + ":" + name.toLowerCase(Locale.ROOT)
    private val repairMaterial = LazyValue(repairMaterial)
    private val maxDamage = intArrayOf(13 * durability, 15 * durability, 16 * durability, 11 * durability)

    override fun getDurability(slot: EquipmentSlotType) = maxDamage[slot.index]
    override fun getDamageReductionAmount(slot: EquipmentSlotType) = reduction[slot.index]
    override fun getEnchantability() = enchantability
    override fun getSoundEvent() = soundEvent
    override fun getRepairMaterial(): Ingredient = repairMaterial.value
    override fun getName() = materialName
    override fun getToughness() = toughness
    override fun getKnockbackResistance() = knockbackReduction
}