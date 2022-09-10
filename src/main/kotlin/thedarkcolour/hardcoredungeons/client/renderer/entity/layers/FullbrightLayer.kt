package thedarkcolour.hardcoredungeons.client.renderer.entity.layers

import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.EyesLayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity

/**
 * Fullbright entity renderer that renders using [eyesTexture] for the texture.
 *
 * @author thedarkcolour
 */
class FullbrightLayer<T : LivingEntity, M : EntityModel<T>>(renderer: RenderLayerParent<T, M>, location: ResourceLocation) : EyesLayer<T, M>(renderer) {
    private val eyesTexture = RenderType.eyes(location)

    override fun renderType(): RenderType {
        return eyesTexture
    }
}