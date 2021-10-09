package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.potion.Effect
import net.minecraft.potion.Effects
import net.minecraft.tags.ITag
import thedarkcolour.hardcoredungeons.block.base.properties.BlockProperties

@Suppress("HasPlatformType")
class PlantProperties private constructor() : BlockProperties<PlantProperties>() {
    /** Used to determine if a block is valid for placement. */
    var predicate: (BlockState) -> Boolean = DEFAULT_PREDICATE
    /** Used for mushrooms to determine if podzol and mycelium are valid for placement. */
    var strict = false
    /** Used for flowers in suspicious stew */
    var effect = Effects.MOVEMENT_SPEED
    /** Used for flowers in suspicious stew*/
    var duration = 0

    /**
     * Set the potion effect of this flower for use in suspicious stew.
     */
    fun stewEffect(effect: Effect, duration: Int): PlantProperties {
        this.effect = effect
        this.duration = duration
        return this
    }

    /**
     * Add valid blocks for placement.
     */
    fun validGround(vararg blocksIn: Block, replace: Boolean = false): PlantProperties {
        predicate = if (replace) {
            { state -> blocksIn.contains(state.block) }
        } else {
            { state -> DEFAULT_PREDICATE(state) && blocksIn.contains(state.block) }
        }
        return this
    }

    fun validGround(tag: ITag<Block>): PlantProperties {
        predicate = { state -> DEFAULT_PREDICATE(state) && state.`is`(tag) }
        return this
    }

    /**
     * The factory that should be a companion object of
     * the implementing [BlockProperties] class
     */
    override fun getFactory(): Factory<PlantProperties> {
        return Companion
    }

    companion object : Factory<PlantProperties>() {
        private val DEFAULT_PREDICATE: (BlockState) -> Boolean = { state -> state.`is`(Blocks.GRASS_BLOCK) || state.`is`(Blocks.DIRT) || state.`is`(Blocks.COARSE_DIRT) || state.`is`(Blocks.PODZOL) || state.`is`(Blocks.FARMLAND) }
        override fun createProperties(): PlantProperties {
            return PlantProperties()
        }
    }
}