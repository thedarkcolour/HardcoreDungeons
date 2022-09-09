package thedarkcolour.hardcoredungeons.entity

import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.world.entity.Mob

abstract class ReloadableRenderer<T : Mob, M : EntityModel<T>>(
    ctx: EntityRendererProvider.Context, private val modelFactory: (() -> M)?, shadowSizeIn: Float
) : MobRenderer<T, M>(ctx, modelFactory?.invoke(), shadowSizeIn) {
    init {
        @Suppress("LeakingThis")
        reloadables.add(this)
    }

    open fun reload() {
        this.model = modelFactory?.invoke()
    }

    companion object {
        @Suppress("SpellCheckingInspection")
        val reloadables = arrayListOf<ReloadableRenderer<*, *>>()
    }
}