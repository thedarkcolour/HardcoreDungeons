package thedarkcolour.hardcoredungeons.client.renderer.entity

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import thedarkcolour.hardcoredungeons.client.model.HModelLayers
import thedarkcolour.hardcoredungeons.client.model.entity.DeerModel
import thedarkcolour.hardcoredungeons.client.renderer.entity.layers.DeerFullbrightLayer
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerType
import thedarkcolour.hardcoredungeons.util.modLoc

class DeerRenderer(ctx: EntityRendererProvider.Context) : ReloadableRenderer<DeerEntity, DeerModel>(ctx, null, 0.4f) {
    private var doe = DeerModel(ctx.bakeLayer(HModelLayers.DOE))
    // also used for alpha
    private var stag = DeerModel(ctx.bakeLayer(HModelLayers.STAG))

    init {
        addLayer(DeerFullbrightLayer(this))
    }

    override fun render(
        entity: DeerEntity, entityYaw: Float, partialTicks: Float,
        stack: PoseStack, buffer: MultiBufferSource, light: Int
    ) {
        model = if (entity.isMe()) {
            stag
        } else {
            if (entity.deerType.isDoe) doe else stag
        }
        super.render(entity, entityYaw, partialTicks, stack, buffer, light)
    }

    override fun getTextureLocation(entity: DeerEntity): ResourceLocation {
        if (entity.isMe()) {
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
        if (entity.isMe()) {
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
        doe = DeerModel(modelSet.bakeLayer(HModelLayers.DOE))
        stag = DeerModel(modelSet.bakeLayer(HModelLayers.STAG))
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