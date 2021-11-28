package thedarkcolour.hardcoredungeons.registry

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.enchantment.ProspectingEnchantment
import thedarkcolour.hardcoredungeons.enchantment.WitheringEnchantment

object HEnchantments {
    val ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, HardcoreDungeons.ID)

    val PROSPECTING = ENCHANTMENTS.register("prospecting", ::ProspectingEnchantment)
    val WITHERING = ENCHANTMENTS.register("withering", ::WitheringEnchantment)
}