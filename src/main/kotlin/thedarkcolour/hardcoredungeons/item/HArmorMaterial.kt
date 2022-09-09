package thedarkcolour.hardcoredungeons.item

import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.crafting.Ingredient
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.LazyLoadedValue
import net.minecraft.util.LazyValue
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraft.world.entity.EquipmentSlot
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
    SPEED_BOOTS(30, intArrayOf(0, 0, 0, 3), 17, SoundEvents.ARMOR_EQUIP_LEATHER, 0.4f, 0.0f, Ingredient::EMPTY),
    SHROOMY(
        18,
        intArrayOf(3, 7, 7, 2),
        16,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        0.1f,
        0.0f,
        { Ingredient.of(net.minecraftforge.common.Tags.Items.MUSHROOMS) })
    ;

    private val materialName = HardcoreDungeons.ID + ":" + name.lowercase(Locale.ROOT)
    private val repairMaterial = LazyLoadedValue(repairMaterial)
    private val maxDamage = intArrayOf(13 * durability, 15 * durability, 16 * durability, 11 * durability)

    override fun getDurabilityForSlot(slot: EquipmentSlot) = maxDamage[slot.index]
    override fun getDefenseForSlot(slot: EquipmentSlot) = reduction[slot.index]
    override fun getEnchantmentValue() = enchantability
    override fun getEquipSound() = soundEvent
    override fun getRepairIngredient(): Ingredient = repairMaterial.get()
    override fun getName() = materialName
    override fun getToughness() = toughness
    override fun getKnockbackResistance() = knockbackReduction
}