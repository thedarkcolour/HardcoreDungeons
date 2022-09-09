package thedarkcolour.hardcoredungeons.registry

import net.minecraft.enchantment.Enchantment
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.enchantment.ProspectingEnchantment
import thedarkcolour.hardcoredungeons.enchantment.WitheringEnchantment

object HEnchantments : HRegistry<Enchantment>(ForgeRegistries.Keys.ENCHANTMENTS) {
    // todo consider removing
    val PROSPECTING by register("prospecting", ::ProspectingEnchantment)
    val WITHERING by register("withering", ::WitheringEnchantment)
}