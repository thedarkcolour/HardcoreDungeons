package thedarkcolour.hardcoredungeons.registry

import net.minecraft.potion.Effect
import net.minecraft.potion.EffectType
import net.minecraftforge.registries.IForgeRegistry
import thedarkcolour.hardcoredungeons.effect.RageEffect

/**
 * @author TheDarkColour
 */
object HEffects {
    val RAGE = RageEffect(EffectType.BENEFICIAL, 16711752).setRegistryKey("rage")
    
    fun registerEffects(effects: IForgeRegistry<Effect>) {

    }
}