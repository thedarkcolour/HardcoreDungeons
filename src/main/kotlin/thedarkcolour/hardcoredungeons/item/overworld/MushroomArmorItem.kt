package thedarkcolour.hardcoredungeons.item.overworld

import net.minecraft.client.model.HumanoidModel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.IItemRenderProperties
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.model.armor.MushroomArmorModel
import thedarkcolour.hardcoredungeons.item.HArmorMaterial
import thedarkcolour.kotlinforforge.forge.runWhenOn
import java.util.function.Consumer

class MushroomArmorItem(slot: EquipmentSlot, properties: Properties) : ArmorItem(HArmorMaterial.Shroomy, slot, properties) {
    @OnlyIn(Dist.CLIENT)
    private var model: Any? = null

    init {
        runWhenOn(Dist.CLIENT) {
            model = MushroomArmorModel(slot)
        }
    }

    override fun initializeClient(consumer: Consumer<IItemRenderProperties>) {
        consumer.accept(object : IItemRenderProperties {
            // Rule of thumb
            // Always leave params nullable to avoid NPEs for unused parameters
            override fun <A : HumanoidModel<*>?> getArmorModel(entityLiving: LivingEntity?, itemStack: ItemStack?, armorSlot: EquipmentSlot?, _default: A): A {
                return model as A
            }
        })
    }

    override fun getArmorTexture(stack: ItemStack?, entity: Entity?, slot: EquipmentSlot?, type: String?): String {
        return HardcoreDungeons.ID + ":textures/models/armor/mushroom_armor.png"
    }
}