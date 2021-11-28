package thedarkcolour.hardcoredungeons.item.castleton

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import top.theillusivec4.curios.api.type.capability.ICurio
import top.theillusivec4.curios.common.capability.CurioItemCapability

class CrownItem(properties: Properties) : Item(properties) {
    override fun initCapabilities(stack: ItemStack?, nbt: CompoundNBT?): ICapabilityProvider? {
        return CurioItemCapability.createProvider(object : ICurio {
            override fun canRender(identifier: String?, index: Int, livingEntity: LivingEntity?) = true

            override fun render(
                identifier: String?,
                index: Int,
                matrixStack: MatrixStack?,
                renderTypeBuffer: IRenderTypeBuffer?,
                light: Int,
                livingEntity: LivingEntity?,
                limbSwing: Float,
                limbSwingAmount: Float,
                partialTicks: Float,
                ageInTicks: Float,
                netHeadYaw: Float,
                headPitch: Float
            ) {
                /*if (this.model !is CrownModel<*>) {
                    this.model = CrownModel<Any?>()
                }

                val crown = this.model as CrownModel<*>
                ICurio.RenderHelper.followHeadRotations(livingEntity, crown.crown)
                val vertexBuilder = ItemRenderer.getFoilBuffer(
                    renderTypeBuffer,
                    crown.renderType(CrownItem.CROWN_TEXTURE),
                    false,
                    stack!!.hasFoil()
                )
                crown.renderToBuffer(
                    matrixStack!!,
                    vertexBuilder,
                    light,
                    OverlayTexture.NO_OVERLAY,
                    1.0f,
                    1.0f,
                    1.0f,
                    1.0f
                )*/
            }
        })
    }
}