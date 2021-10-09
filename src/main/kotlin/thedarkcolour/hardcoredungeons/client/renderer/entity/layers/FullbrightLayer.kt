package thedarkcolour.hardcoredungeons.client.renderer.entity.layers

import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.IEntityRenderer
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ResourceLocation

/**
 * Fullbright entity renderer that renders using [eyesTexture] for the texture.
 *
 * @author TheDarkColour
 */
class FullbrightLayer<T : LivingEntity, M : EntityModel<T>>(renderer: IEntityRenderer<T, M>, location: ResourceLocation) : AbstractEyesLayer<T, M>(renderer) {
    private val eyesTexture = RenderType.eyes(location)

    override fun renderType(): RenderType {
        return eyesTexture
    }
}