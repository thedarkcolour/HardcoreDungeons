package thedarkcolour.hardcoredungeons.data.modelgen

import thedarkcolour.hardcoredungeons.data.ModelGenerator

@Suppress("LeakingThis")
abstract class ModelType<T> {
    init {
        ModelGenerator.MODEL_TYPES.add(this)
    }

    /**
     * Ran during model generation.
     */
    abstract fun generateModels(gen: ModelGenerator)

    companion object {
        // block models
        val SIMPLE_BLOCK = SimpleBlockModelType()
        val SLAB_BLOCK = SlabModelType()
        val STAIRS_BLOCK = StairsModelType()
        val WALL_BLOCK = WallModelType()
        val FENCE_BLOCK = FenceModelType()
        val BUTTON_BLOCK = ButtonModelType()
        val COLUMN_BLOCK = ColumnModelType()
        val PILLAR_BLOCK = PillarModelType()
        val DOOR_BLOCK = DoorModelType()
        val TRAPDOOR_BLOCK = TrapDoorModelType()
        val FARMLAND_BLOCK = FarmlandModelType()
        val VASE_BLOCK = VaseModelType()

        // item models
        val SIMPLE_ITEM = SimpleItemModelType()
        val HANDHELD_ITEM = HandheldModelType()
        val SPAWN_EGG = SpawnEggModelType()
    }
}