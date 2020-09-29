package thedarkcolour.hardcoredungeons.item.overworld

import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.entity.LivingEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import thedarkcolour.hardcoredungeons.item.HArmorMaterial

class MushroomCapItem(properties: Properties) : ArmorItem(HArmorMaterial.SHROOMY, EquipmentSlotType.HEAD, properties) {
    /**
     * Override this method to have an item handle its own armor rendering.
     *
     * @param entityLiving The entity wearing the armor
     * @param itemStack    The itemStack to render the model of
     * @param armorSlot    The slot the armor is in
     * @param _default     Original armor model. Will have attributes set.
     * @return A ModelBiped to render instead of the default
     */
    override fun <A : BipedModel<*>?> getArmorModel(
        entityLiving: LivingEntity?,
        itemStack: ItemStack?,
        armorSlot: EquipmentSlotType?,
        _default: A
    ): A? {
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default)
    }
}