package thedarkcolour.hardcoredungeons.registry

import net.minecraft.potion.Effect
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.effect.RageEffect

/**
 * @author TheDarkColour
 */
object HEffects {
    val RAGE = RageEffect().setRegistryKey("rage")
    
    fun registerEffects(effects: IForgeRegistry<Effect>) {
        effects.register(RAGE)
    }
}