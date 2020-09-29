package thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut

import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.entity.castleton.FullbrightLayer

class KnightlyJuggernautRenderer(
    manager: EntityRendererManager
) : MobRenderer<KnightlyJuggernautEntity, KnightlyJuggernautModel>(manager, KnightlyJuggernautModel(), 1.5f) {
    init {
        addLayer(FullbrightLayer(this, ResourceLocation(HardcoreDungeons.ID, "textures/entity/knightly_juggernaut/knightly_juggernaut_overlay.png")))
    }

    override fun getEntityTexture(entity: KnightlyJuggernautEntity) = TEXTURE

    companion object {
        private val TEXTURE = ResourceLocation(HardcoreDungeons.ID, "textures/entity/knightly_juggernaut/knightly_juggernaut.png")
    }
}