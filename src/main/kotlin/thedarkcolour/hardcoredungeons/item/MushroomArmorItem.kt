package thedarkcolour.hardcoredungeons.item

import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.model.armor.MushroomArmorModel
import thedarkcolour.kotlinforforge.forge.runWhenOn

class MushroomArmorItem(slot: EquipmentSlotType, properties: Properties) : ArmorItem(HArmorMaterial.SHROOMY, slot, properties) {
    private lateinit var model: Any

    init {
        runWhenOn(Dist.CLIENT) {
            model = MushroomArmorModel(slot)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun <A : BipedModel<*>> getArmorModel(entityLiving: LivingEntity?, itemStack: ItemStack?, armorSlot: EquipmentSlotType?, _default: A): A {
        return model as A
    }

    override fun getArmorTexture(stack: ItemStack?, entity: Entity?, slot: EquipmentSlotType?, type: String?): String {
        return HardcoreDungeons.ID + ":textures/models/armor/mushroom_armor.png"
    }
}