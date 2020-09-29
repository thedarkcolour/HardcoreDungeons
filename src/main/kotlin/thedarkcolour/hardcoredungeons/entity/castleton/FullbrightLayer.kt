package thedarkcolour.hardcoredungeons.entity.castleton

import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.IEntityRenderer
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ResourceLocation

open class FullbrightLayer<T : LivingEntity, M : EntityModel<T>>(renderer: IEntityRenderer<T, M>, location: ResourceLocation) : AbstractEyesLayer<T, M>(renderer) {
    private val eyesTexture = RenderType.getEyes(location)

    override fun getRenderType(): RenderType {
        return eyesTexture
    }
}