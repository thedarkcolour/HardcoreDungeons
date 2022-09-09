package thedarkcolour.hardcoredungeons.item

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.model.armor.PendantModel
import thedarkcolour.kotlinforforge.forge.runWhenOn
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurio
import top.theillusivec4.curios.api.type.capability.ICurioItem

class PendantItem(properties: Properties) : Item(properties), ICurioItem {
    private lateinit var model: Any

    init {
        runWhenOn(Dist.CLIENT) {
            model = PendantModel()
        }
    }

    override fun canEquipFromUse(slotContext: SlotContext?, stack: ItemStack?): Boolean {
        return true
    }

    override fun canRender(identifier: String?, index: Int, livingEntity: LivingEntity?, stack: ItemStack?): Boolean {
        return true
    }

    override fun render(identifier: String, index: Int, matrixStack: MatrixStack, renderTypeBuffer: IRenderTypeBuffer, light: Int, livingEntity: LivingEntity, limbSwing: Float, limbSwingAmount: Float, partialTicks: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, stack: ItemStack) {
        val model = model as PendantModel
        val buffer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(TEXTURE), false, stack.hasFoil())

        // thank you mr curios
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity)
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity)

        model.renderToBuffer(matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)
    }

    companion object {
        private val TEXTURE = ResourceLocation(HardcoreDungeons.ID + ":textures/models/curios/aurigold_pendant.png")
    }
}