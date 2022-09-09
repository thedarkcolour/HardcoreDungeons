package thedarkcolour.hardcoredungeons.item

import net.minecraft.client.model.HumanoidModel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.model.armor.MushroomArmorModel
import thedarkcolour.kotlinforforge.forge.runWhenOn
import java.util.function.Consumer

class MushroomArmorItem(slot: EquipmentSlot, properties: Properties) : ArmorItem(HArmorMaterial.SHROOMY, slot, properties) {
    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(ClientExtensions(slot))
    }

    override fun getArmorTexture(stack: ItemStack?, entity: Entity?, slot: EquipmentSlot?, type: String?): String {
        return HardcoreDungeons.ID + ":textures/models/armor/mushroom_armor.png"
    }

    class ClientExtensions(slot: EquipmentSlot) : IClientItemExtensions {
        private lateinit var model: MushroomArmorModel

        init {
            runWhenOn(Dist.CLIENT) {
                model = MushroomArmorModel(slot)
            }
        }

        override fun getHumanoidArmorModel(
            livingEntity: LivingEntity?,
            itemStack: ItemStack?,
            equipmentSlot: EquipmentSlot?,
            original: HumanoidModel<*>?
        ): HumanoidModel<*> {
            return model
        }
    }
}