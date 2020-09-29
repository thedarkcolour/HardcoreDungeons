package thedarkcolour.hardcoredungeons.entity

import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.entity.MobEntity

abstract class ReloadableRenderer<T : MobEntity, M : EntityModel<T>>(
    renderManagerIn: EntityRendererManager, private val model: (() -> M)?, shadowSizeIn: Float
) : MobRenderer<T, M>(renderManagerIn, model?.invoke(), shadowSizeIn) {
    init {
        @Suppress("LeakingThis")
        reloadables.add(this)
    }

    open fun reload() {
        entityModel = model?.invoke()
    }

    companion object {
        @Suppress("SpellCheckingInspection")
        val reloadables = arrayListOf<ReloadableRenderer<*, *>>()
    }
}