package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.entity.EntityRendererManager
import thedarkcolour.hardcoredungeons.client.model.entity.KnightlyJuggernautModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.layers.FullbrightLayer
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.util.modLoc

class KnightlyJuggernautRenderer(
    manager: EntityRendererManager
) : ReloadableRenderer<KnightlyJuggernautEntity, KnightlyJuggernautModel>(manager, ::KnightlyJuggernautModel, 1.5f) {
    init {
        addLayer(FullbrightLayer(this, modLoc("textures/entity/knightly_juggernaut/knightly_juggernaut_overlay.png")))
    }

    override fun setupRotations(
        entity: KnightlyJuggernautEntity, stack: MatrixStack, ageInTicks: Float,
        rotationYaw: Float, partialTicks: Float
    ) {
        super.setupRotations(entity, stack, ageInTicks, rotationYaw, partialTicks)
    }

    override fun getTextureLocation(entity: KnightlyJuggernautEntity) = TEXTURE

    companion object {
        private val TEXTURE = modLoc("textures/entity/knightly_juggernaut/knightly_juggernaut.png")
    }
}