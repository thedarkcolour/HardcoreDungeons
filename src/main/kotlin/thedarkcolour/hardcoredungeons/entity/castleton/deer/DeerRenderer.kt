package thedarkcolour.hardcoredungeons.entity.castleton.deer

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer
import thedarkcolour.hardcoredungeons.util.modLoc

class DeerRenderer(manager: EntityRendererManager) : ReloadableRenderer<CastletonDeerEntity, DoeModel>(manager, null, 0.4f) {
    private var female = DoeModel()
    private var stag = StagDeerModel()
    private var alpha = null

    init {
        addLayer(DeerFullbrightLayer(this))
    }

    override fun render(
        entity: CastletonDeerEntity, entityYaw: Float, partialTicks: Float,
        stack: MatrixStack, buffer: IRenderTypeBuffer, light: Int
    ) {
        val name = entity.customName?.unformattedComponentText

        entityModel = if (name == "TheDarkColour" || name == "bruh") {
            stag
        } else when (entity.pattern) {
            DeerPattern.BLUE_EYED_DOE,
            DeerPattern.BLUE_SPOTTED_DOE,
            DeerPattern.PURPLE_EYED_DOE,
            DeerPattern.PURPLE_SPOTTED_DOE
            -> female
            DeerPattern.BLUE_EYED_STAG,
            DeerPattern.BLUE_SPOTTED_STAG,
            DeerPattern.PURPLE_EYED_STAG,
            DeerPattern.PURPLE_SPOTTED_STAG
            -> stag
            DeerPattern.BLUE_ALPHA,
            DeerPattern.PURPLE_ALPHA
            -> alpha
        }
        super.render(entity, entityYaw, partialTicks, stack, buffer, light)
    }

    override fun getEntityTexture(entity: CastletonDeerEntity): ResourceLocation {
        val name = entity.customName?.unformattedComponentText

        if (name == "TheDarkColour" || name == "bruh") {
            return THEDARKCOLOUR
        }

        return when (entity.pattern) {
            DeerPattern.BLUE_EYED_DOE -> BLUE_EYED_FEMALE
            DeerPattern.BLUE_EYED_STAG -> BLUE_EYED_STAG
            DeerPattern.BLUE_SPOTTED_STAG -> BLUE_SPOTTED_STAG
            DeerPattern.PURPLE_SPOTTED_STAG -> PURPLE_SPOTTED_STAG
            else -> BLUE_EYED_FEMALE
        }
    }

    fun getOverlayTexture(entity: CastletonDeerEntity): RenderType {
        val name = entity.customName?.unformattedComponentText
        if (name == "TheDarkColour" || name == "bruh") {
            return THEDARKCOLOUR_OVERLAY
        }

        return when (entity.pattern) {
            DeerPattern.BLUE_EYED_DOE -> BLUE_EYED_FEMALE_OVERLAY
            DeerPattern.BLUE_EYED_STAG -> BLUE_EYED_STAG_OVERLAY
            DeerPattern.BLUE_SPOTTED_STAG -> BLUE_SPOTTED_STAG_OVERLAY
            DeerPattern.PURPLE_SPOTTED_STAG -> PURPLE_SPOTTED_STAG_OVERLAY
            else -> BLUE_EYED_FEMALE_OVERLAY
        }
    }

    override fun reload() {
        female = DoeModel()
        stag = StagDeerModel()
        alpha = null
    }

    companion object {
        private val BLUE_EYED_FEMALE = modLoc("textures/entity/deer/castleton/blue_eyed_female.png")
        private val BLUE_EYED_FEMALE_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_eyed_female_overlay.png"))
        private val BLUE_EYED_STAG = modLoc("textures/entity/deer/castleton/blue_eyed_stag.png")
        private val BLUE_EYED_STAG_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_eyed_stag_overlay.png"))
        private val BLUE_SPOTTED_STAG = modLoc("textures/entity/deer/castleton/spotted_stag.png")
        private val BLUE_SPOTTED_STAG_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_spotted_stag_overlay.png"))
        private val PURPLE_SPOTTED_STAG = modLoc("textures/entity/deer/castleton/spotted_stag.png")
        private val PURPLE_SPOTTED_STAG_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/purple_spotted_stag_overlay.png"))
        private val THEDARKCOLOUR = modLoc("textures/entity/deer/thedarkcolour.png")
        private val THEDARKCOLOUR_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/thedarkcolour_overlay.png"))
    }
}