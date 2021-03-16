package thedarkcolour.hardcoredungeons.registry

import net.minecraft.enchantment.Enchantment
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.enchantment.ProspectingEnchantment
import thedarkcolour.hardcoredungeons.enchantment.WitheringEnchantment

object HEnchantments {
    val PROSPECTING = ProspectingEnchantment().setRegistryKey("prospecting")
    val WITHERING = WitheringEnchantment().setRegistryKey("withering")

    fun registerEnchantments(event: IForgeRegistry<Enchantment>) {
        event.registerAll(PROSPECTING, WITHERING)
    }
}