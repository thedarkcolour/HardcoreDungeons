package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import top.theillusivec4.curios.api.type.capability.ICurio
import top.theillusivec4.curios.common.capability.CurioItemCapability

class CrownItem(properties: Properties) : Item(properties) {
    override fun initCapabilities(stack: ItemStack?, nbt: CompoundNBT?): ICapabilityProvider? {
        return CurioItemCapability.createProvider(object : ICurio {
            override fun canRender(identifier: String?, index: Int, livingEntity: LivingEntity?) = true
        })
    }
}