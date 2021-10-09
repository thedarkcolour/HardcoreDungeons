package thedarkcolour.hardcoredungeons.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.entity.ai.attributes.Attribute
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.ItemStack
import java.util.*

/**
 * speed boots
 * feather boots
 */
class SpeedBootsItem(material: IArmorMaterial, props: Properties) : ArmorItem(material, EquipmentSlotType.FEET, props) {
    private val defaultModifiers = ImmutableMultimap.builder<Attribute, AttributeModifier>()
        .putAll(getDefaultAttributeModifiers(EquipmentSlotType.FEET))
        .put(Attributes.MOVEMENT_SPEED, AttributeModifier(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), "Movement Speed Boost", 1.2, AttributeModifier.Operation.MULTIPLY_TOTAL))
        .build()

    override fun getAttributeModifiers(
        slot: EquipmentSlotType?,
        stack: ItemStack?
    ): Multimap<Attribute, AttributeModifier> {
        return defaultModifiers
    }
}