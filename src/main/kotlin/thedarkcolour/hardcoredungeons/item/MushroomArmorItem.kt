package thedarkcolour.hardcoredungeons.item

import net.minecraft.client.Minecraft
import net.minecraft.client.model.HumanoidModel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ItemStack
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.model.HModelLayers
import thedarkcolour.hardcoredungeons.client.model.armor.MushroomArmorModel
import java.util.function.Consumer

class MushroomArmorItem(slot: Type, properties: Properties) : ArmorItem(HArmorMaterial.SHROOMY, slot, properties) {
    // Leaks 'this' so fields on the item are all null
    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(ClientExtensions)
    }

    override fun getArmorTexture(stack: ItemStack?, entity: Entity?, slot: EquipmentSlot?, type: String?): String {
        return HardcoreDungeons.ID + ":textures/models/armor/mushroom_armor.png"
    }

    object ClientExtensions : IClientItemExtensions {
        private val headModel by lazy {
            MushroomArmorModel(Minecraft.getInstance().entityModels.bakeLayer(HModelLayers.MUSHROOM_ARMOR), EquipmentSlot.HEAD)
        }
        private val chestModel by lazy {
            MushroomArmorModel(Minecraft.getInstance().entityModels.bakeLayer(HModelLayers.MUSHROOM_ARMOR), EquipmentSlot.CHEST)
        }

        override fun getHumanoidArmorModel(
            livingEntity: LivingEntity?,
            itemStack: ItemStack?,
            equipmentSlot: EquipmentSlot?,
            original: HumanoidModel<*>?
        ): HumanoidModel<*> {
            return when (equipmentSlot) {
                EquipmentSlot.HEAD -> headModel
                EquipmentSlot.CHEST -> chestModel
                else -> throw IllegalArgumentException("how")
            }
        }
    }
}