package thedarkcolour.hardcoredungeons.entity.misc.cheeky

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation

class SphereCheekyRender(manager: EntityRendererManager) : EntityRenderer<CheekyEntity>(manager) {
    override fun render(
        cheeky: CheekyEntity,
        entityYaw: Float,
        partialTicks: Float,
        stack: MatrixStack,
        buffer: IRenderTypeBuffer,
        light: Int
    ) {
        val scale = cheeky.renderScale * 0.25f

        stack.push()
        stack.scale(scale, scale, scale)

        for (i in 0..4) {
            val tt = (i + (cheeky.ticksExisted % 10 + partialTicks) / 11.0f) / 5.0f
            val subScale = 1 + 0.5f * tt
            stack.push()
            stack.scale(subScale, subScale, subScale)

            aaaaaaaaaaa.render(buffer, RenderType.getEntityTranslucent(getEntityTexture(cheeky)), stack, 0x00f000f0, 0x7fffffff)

            stack.pop()
        }

        stack.pop()

        super.render(cheeky, entityYaw, partialTicks, stack, buffer, light)
    }

    override fun getEntityTexture(entity: CheekyEntity): ResourceLocation {
        return ResourceLocation("minecraft:textures/block/water_still.png")
    }

    companion object {
        val aaaaaaaaaaa = ModelHandle.of("hardcoredungeons:models/entity/sphere.obj")
    }
}