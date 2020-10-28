package thedarkcolour.hardcoredungeons.entity.misc.magic

import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

class MagicBoltRenderer<T : Entity>(manager: EntityRendererManager) : EntityRenderer<T>(manager) {

    override fun getEntityTexture(entity: T): ResourceLocation {
        return PIG_TEXTURES
    }

    companion object {
        private val PIG_TEXTURES = ResourceLocation("textures/entity/pig/pig.png")
    }
}