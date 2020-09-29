package thedarkcolour.hardcoredungeons.block.properties

import net.minecraft.block.Block
import net.minecraft.potion.Effect
import net.minecraft.potion.Effects

@Suppress("HasPlatformType")
class PlantProperties private constructor() : Properties<PlantProperties>() {
    /** Used to determine if a block is valid for placement. */
    var blocks: MutableSet<Block> = hashSetOf()
    /** Used for mushrooms to determine if podzol and mycelium are valid for placement. */
    var strict = false
    /** Used for flowers in suspicious stew */
    var effect = Effects.SPEED
    /** Used for flowers in suspicious stew*/
    var duration = 0

    /**
     * Restricts valid ground blocks to the ones chosen in [validGround]
     */
    fun strict(): PlantProperties {
        strict = true
        return this
    }

    /**
     * Set the potion effect of this flower for use in suspicious stew.
     */
    fun effect(effect: Effect): PlantProperties {
        this.effect = effect
        return this
    }

    /**
     * Set the potion effect duration of this flower for use in suspicious stew.
     */
    fun duration(duration: Int): PlantProperties {
        this.duration = duration
        return this
    }

    /**
     * Add valid blocks for placement.
     */
    fun validGround(vararg blocksIn: Block): PlantProperties {
        blocks.addAll(blocksIn)
        return this
    }

    /**
     * The factory that should be a companion object of
     * the implementing [Properties] class
     */
    override fun getFactory(): Factory<PlantProperties> {
        return Companion
    }

    companion object : Factory<PlantProperties>() {
        override fun createProperties(): PlantProperties {
            return PlantProperties()
        }
    }
}