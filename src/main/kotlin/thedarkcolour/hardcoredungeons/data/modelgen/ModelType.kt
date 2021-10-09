package thedarkcolour.hardcoredungeons.data.modelgen

import thedarkcolour.hardcoredungeons.data.ModelGenerator

// todo split out of block registry and do a similar thing to the other generators
@Suppress("LeakingThis")
abstract class ModelType<T> {
    init {
        ModelGenerator.MODEL_TYPES.add(this)
    }

    /**
     * Ran during model generation.
     */
    abstract fun generateModels(gen: ModelGenerator)
}