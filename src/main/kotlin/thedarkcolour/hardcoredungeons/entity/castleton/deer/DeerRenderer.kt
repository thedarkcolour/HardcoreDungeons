package thedarkcolour.hardcoredungeons.entity.castleton.deer

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.util.modLoc

class DeerRenderer(manager: EntityRendererManager) : ReloadableRenderer<DeerEntity, DoeModel>(manager, null, 0.4f) {
    private var doe = DoeModel()
    private var stag = StagModel()
    private var alpha = null

    init {
        addLayer(DeerFullbrightLayer(this))
    }

    private fun isTheDarkColour(entity: DeerEntity): Boolean {
        return entity.customName?.unformattedComponentText?.toLowerCase() == "thedarkcolour"
    }

    override fun render(
        entity: DeerEntity, entityYaw: Float, partialTicks: Float,
        stack: MatrixStack, buffer: IRenderTypeBuffer, light: Int
    ) {
        entityModel = if (isTheDarkColour(entity)) {
            stag
        } else when {
            entity.pattern.isStag() -> stag
            entity.pattern.isDoe() -> doe
            entity.pattern.isAlpha() -> alpha
            else -> doe
        }
        super.render(entity, entityYaw, partialTicks, stack, buffer, light)
    }

    override fun getEntityTexture(entity: DeerEntity): ResourceLocation {
        if (isTheDarkColour(entity)) {
            return THEDARKCOLOUR
        }

        return when (entity.pattern) {
            DeerPattern.BLUE_EYED_DOE -> BLUE_EYED_FEMALE
            DeerPattern.FOREST_STAG -> FOREST_STAG
            DeerPattern.BLUE_EYED_STAG -> BLUE_EYED_STAG
            DeerPattern.BLUE_SPOTTED_STAG -> BLUE_SPOTTED_STAG
            DeerPattern.PURPLE_SPOTTED_STAG -> PURPLE_SPOTTED_STAG
            else -> BLUE_EYED_FEMALE
        }
    }

    fun getOverlayTexture(entity: DeerEntity): RenderType? {
        if (isTheDarkColour(entity)) {
            return THEDARKCOLOUR_OVERLAY
        }

        return when (entity.pattern) {
            DeerPattern.BLUE_EYED_STAG -> BLUE_EYED_STAG_OVERLAY
            DeerPattern.BLUE_EYED_DOE -> BLUE_EYED_FEMALE_OVERLAY
            DeerPattern.BLUE_SPOTTED_STAG -> BLUE_SPOTTED_STAG_OVERLAY
            //DeerPattern.BLUE_SPOTTED_DOE -> BLUE_SPOTTED_DOE_OVERLAY
            DeerPattern.PURPLE_SPOTTED_STAG -> PURPLE_SPOTTED_STAG_OVERLAY
            else -> null
        }
    }

    override fun reload() {
        doe = DoeModel()
        stag = StagModel()
        alpha = null
    }

    companion object {
        private val FOREST_STAG = modLoc("textures/entity/deer/overworld/forest_stag.png")
        private val BLUE_EYED_FEMALE = modLoc("textures/entity/deer/castleton/blue_eyed_female.png")
        private val BLUE_EYED_FEMALE_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_eyed_female_overlay.png"))
        private val BLUE_EYED_STAG = modLoc("textures/entity/deer/castleton/blue_eyed_stag.png")
        private val BLUE_EYED_STAG_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_eyed_stag_overlay.png"))
        private val BLUE_SPOTTED_STAG = modLoc("textures/entity/deer/castleton/spotted_stag.png")
        private val BLUE_SPOTTED_STAG_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_spotted_stag_overlay.png"))
        //private val BLUE_SPOTTED_DOE_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/blue_spotted_stag_overlay.png"))
        private val PURPLE_SPOTTED_STAG = modLoc("textures/entity/deer/castleton/spotted_stag.png")
        private val PURPLE_SPOTTED_STAG_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/castleton/purple_spotted_stag_overlay.png"))
        private val THEDARKCOLOUR = modLoc("textures/entity/deer/thedarkcolour.png")
        private val THEDARKCOLOUR_OVERLAY = RenderType.getEyes(modLoc("textures/entity/deer/thedarkcolour_overlay.png"))
    }
}