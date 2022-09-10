package thedarkcolour.hardcoredungeons.registry

import net.minecraft.world.item.enchantment.Enchantment
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.enchantment.WitheringEnchantment

object HEnchantments : HRegistry<Enchantment>(ForgeRegistries.Keys.ENCHANTMENTS) {
    val WITHERING by register("withering", ::WitheringEnchantment)
}