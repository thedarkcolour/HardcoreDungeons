package thedarkcolour.hardcoredungeons.entity.castleton.knightwolf

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ItemStack
import net.minecraft.util.HandSide
import net.minecraft.world.World

class KnightWolfEntity(type: EntityType<KnightWolfEntity>, worldIn: World) : LivingEntity(type, worldIn) {
    override fun setItemStackToSlot(p_184201_1_: EquipmentSlotType, p_184201_2_: ItemStack) {
        TODO("not implemented")
    }

    override fun getArmorInventoryList(): MutableIterable<ItemStack> {
        TODO("not implemented")
    }

    override fun getItemStackFromSlot(p_184582_1_: EquipmentSlotType): ItemStack {
        TODO("not implemented")
    }

    override fun getPrimaryHand(): HandSide {
        TODO("not implemented")
    }
}