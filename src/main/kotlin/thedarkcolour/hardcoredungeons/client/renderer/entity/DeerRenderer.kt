package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.resources.ResourceLocation
import thedarkcolour.hardcoredungeons.client.model.entity.DoeModel
import thedarkcolour.hardcoredungeons.client.model.entity.StagModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.layers.DeerFullbrightLayer
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerType
import thedarkcolour.hardcoredungeons.util.modLoc

class DeerRenderer(manager: EntityRendererManager) : ReloadableRenderer<DeerEntity, DoeModel>(manager, null, 0.4f) {
    private var doe = DoeModel()
    private var stag = StagModel() // also used for alpha

    init {
        addLayer(DeerFullbrightLayer(this))
    }

    override fun render(
        entity: DeerEntity, entityYaw: Float, partialTicks: Float,
        stack: MatrixStack, buffer: IRenderTypeBuffer, light: Int
    ) {
        model = if (entity.isthedarkcolour()) {
            stag
        } else {
            if (entity.deerType.isDoe) doe else stag
        }
        super.render(entity, entityYaw, partialTicks, stack, buffer, light)
    }

    override fun getTextureLocation(entity: DeerEntity): ResourceLocation {
        if (entity.isthedarkcolour()) {
            return THEDARKCOLOUR
        }

        val deerType = entity.deerType

        return when {
            deerType.isForest -> FOREST
            deerType.isSpotted -> SPOTTED
            deerType.isAlpha -> ALPHA
            else -> PLAIN
        }
    }

    fun getOverlayTexture(entity: DeerEntity): RenderType? {
        if (entity.isthedarkcolour()) {
            return THEDARKCOLOUR_OVERLAY
        }

        return when (entity.deerType) {
            DeerType.BLUE_EYED_STAG, DeerType.BLUE_EYED_DOE -> BLUE_EYED_OVERLAY
            DeerType.BLUE_SPOTTED_STAG, DeerType.BLUE_SPOTTED_DOE -> BLUE_SPOTTED_OVERLAY
            DeerType.BLUE_ALPHA -> BLUE_ALPHA_OVERLAY
            DeerType.PURPLE_EYED_STAG, DeerType.PURPLE_EYED_DOE -> PURPLE_EYED_OVERLAY
            DeerType.PURPLE_SPOTTED_STAG, DeerType.PURPLE_SPOTTED_DOE -> PURPLE_SPOTTED_OVERLAY
            DeerType.PURPLE_ALPHA -> PURPLE_ALPHA_OVERLAY
            else -> null
        }
    }

    override fun reload() {
        doe = DoeModel()
        stag = StagModel()
    }

    companion object {
        private val FOREST = modLoc("textures/entity/deer/overworld/forest.png")
        private val PLAIN = modLoc("textures/entity/deer/castleton/plain.png")
        private val SPOTTED = modLoc("textures/entity/deer/castleton/spotted.png")
        private val ALPHA = modLoc("textures/entity/deer/castleton/alpha.png")

        private val BLUE_EYED_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/castleton/blue_eyed_overlay.png"))
        private val BLUE_ALPHA_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/castleton/blue_alpha_overlay.png"))
        private val BLUE_SPOTTED_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/castleton/blue_spotted_overlay.png"))
        private val PURPLE_EYED_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/castleton/purple_eyed_overlay.png"))
        private val PURPLE_ALPHA_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/castleton/purple_alpha_overlay.png"))
        private val PURPLE_SPOTTED_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/castleton/purple_spotted_overlay.png"))

        private val THEDARKCOLOUR = modLoc("textures/entity/deer/thedarkcolour.png")
        private val THEDARKCOLOUR_OVERLAY = RenderType.eyes(modLoc("textures/entity/deer/thedarkcolour_overlay.png"))
    }
}