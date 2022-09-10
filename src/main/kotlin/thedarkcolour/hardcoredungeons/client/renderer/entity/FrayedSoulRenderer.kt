package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.entity.EntityRendererProvider
import thedarkcolour.hardcoredungeons.client.model.HModelLayers
import thedarkcolour.hardcoredungeons.client.model.entity.FrayedSoulModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.layers.FullbrightLayer
import thedarkcolour.hardcoredungeons.entity.castleton.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.util.modLoc
import kotlin.math.sin

class FrayedSoulRenderer(context: EntityRendererProvider.Context) : ReloadableRenderer<FrayedSoulEntity, FrayedSoulModel>(
    context,
    { FrayedSoulModel(context.bakeLayer(HModelLayers.FRAYED_SOUL)) },
    0.4f
) {
    init {
        addLayer(FullbrightLayer(this, modLoc("textures/entity/frayed_soul/frayed_soul_eyes.png")))
    }

    override fun getTextureLocation(entity: FrayedSoulEntity) = TEXTURE

    override fun setupRotations(
        entity: FrayedSoulEntity, stack: PoseStack, ageInTicks: Float,
        rotationYaw: Float, partialTicks: Float
    ) {
        super.setupRotations(entity, stack, ageInTicks, rotationYaw, partialTicks)
        stack.translate(0.0, sin((entity.tickCount.toDouble() + partialTicks) / 12) / 6, 0.0)
    }

    companion object {
        private val TEXTURE = modLoc("textures/entity/frayed_soul/frayed_soul.png")
    }
}