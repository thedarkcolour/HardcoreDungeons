package thedarkcolour.hardcoredungeons.client.renderer.entity

import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

// what the fuck is even this
class MagicBoltRenderer<T : Entity>(manager: EntityRendererManager) : EntityRenderer<T>(manager) {

    override fun getEntityTexture(entity: T): ResourceLocation {
        return PIG_TEXTURES
    }

    companion object {
        private val PIG_TEXTURES = ResourceLocation("textures/entity/pig/pig.png")
    }
}