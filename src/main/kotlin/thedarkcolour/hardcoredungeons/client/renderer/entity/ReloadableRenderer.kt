package thedarkcolour.hardcoredungeons.client.renderer.entity

import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.world.entity.Mob

/**
 * Allows in-game hot reload of this entity's model when changes are made
 * in its modelConfigure or wherever else it declares its parts.
 *
 * @param ctx Render context whose model set is used when baking layers.
 * @property defaultModelLayer Can be null if the render method does not use the model field.
 */
abstract class ReloadableRenderer<T : Mob, M : EntityModel<T>>(
    ctx: EntityRendererProvider.Context, private val defaultModelLayer: (() -> M)?, shadowSizeIn: Float
) : MobRenderer<T, M>(ctx, defaultModelLayer?.invoke(), shadowSizeIn) {
    protected val modelSet = ctx.modelSet

    init {
        @Suppress("LeakingThis")
        reloadables.add(this)
    }

    open fun reload() {
        this.model = defaultModelLayer?.invoke()
    }

    companion object {
        @Suppress("SpellCheckingInspection")
        val reloadables = arrayListOf<ReloadableRenderer<*, *>>()
    }
}