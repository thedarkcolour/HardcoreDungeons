package thedarkcolour.hardcoredungeons.client.renderer.curio

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.client.ICurioRenderer

class CrownCurioRenderer  : ICurioRenderer {
    //private val model = PendantModel(Minecraft.getInstance().entityModels.bakeLayer(HModelLayers.PENDANT))

    override fun <T : LivingEntity?, M : EntityModel<T>> render(stack: ItemStack, ctx: SlotContext, poseStack: PoseStack, parent: RenderLayerParent<T, M>, buffers: MultiBufferSource, light: Int, limbSwing: Float, limbSwingAmount: Float, partialTicks: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        val livingEntity = ctx.entity
        val buffer = ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(TEXTURE), false, stack.hasFoil())

        ICurioRenderer.translateIfSneaking(poseStack, livingEntity)
        ICurioRenderer.rotateIfSneaking(poseStack, livingEntity)

        //model.renderToBuffer(poseStack, buffer, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)
    }

    companion object {
        private val TEXTURE = ResourceLocation(HardcoreDungeons.ID + ":textures/models/curios/crown.png")
    }
}
