package thedarkcolour.hardcoredungeons.entity.misc.cheeky

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.*
import net.minecraft.client.renderer.texture.NativeImage
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.data.AnimationMetadataSection
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.IModelConfiguration
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.client.model.obj.OBJModel.ModelSettings
import java.util.*
import java.util.function.Function

// credit to gigaherz
class ModelHandle(modelLocation: ResourceLocation) {
    private val rand = Random()
    private var model: IBakedModel? = null

    init {
        val model = OBJLoader.INSTANCE.loadModel(ModelSettings(modelLocation, false, true, true, true, null))
        this.model = model.bake(
            FakeModelConfiguration(modelLocation),
            ModelLoader.instance(),
            FakeSprite.GETTER,
            ModelRotation.X0_Y0,
            null,
            modelLocation
        )
    }

    fun render(buffer: IRenderTypeBuffer, rt: RenderType, stack: MatrixStack, light: Int, rgba: Int) {
        render(buffer, rt, stack, light, OverlayTexture.NO_OVERLAY, rgba)
    }

    fun render(
        bufferIn: IRenderTypeBuffer,
        rt: RenderType,
        matrixStackIn: MatrixStack,
        packedLightIn: Int,
        overlay: Int,
        color: Int
    ) {
        val a = ((color shr 24) and 0xff) / 255.0f
        val r = ((color shr 16) and 0xff) / 255.0f
        val g = ((color shr 8 ) and 0xff) / 255.0f
        val b = ((color shr 0 ) and 0xff) / 255.0f

        val bb = bufferIn.getBuffer(rt)
        for (quad in model!!.getQuads(null, null, rand, EmptyModelData.INSTANCE)) {
            bb.addVertexData(matrixStackIn.last, quad, r, g, b, a, packedLightIn, overlay, true)
        }
    }

    private class FakeSprite : TextureAtlasSprite(null, Info(LOCATION, 1, 1, AnimationMetadataSection.EMPTY), 0, 1, 1, 0, 0, NativeImage(1, 1, false)) {
        override fun getInterpolatedU(u: Double) = u.toFloat() / 16
        override fun getInterpolatedV(v: Double) = v.toFloat() / 16

        companion object {
            val LOCATION = ResourceLocation("elementsofpower", "fake")
            val INSTANCE = FakeSprite()
            val GETTER = Function<RenderMaterial, TextureAtlasSprite> { INSTANCE }
        }
    }

    private class FakeModelConfiguration(private val modelLocation: ResourceLocation) : IModelConfiguration {
        override fun getOwnerModel(): IUnbakedModel? {
            return null
        }

        override fun getModelName(): String {
            return modelLocation.toString()
        }

        override fun isTexturePresent(name: String): Boolean {
            return false
        }

        override fun resolveTexture(name: String): RenderMaterial {
            return RenderMaterial(FakeSprite.LOCATION, FakeSprite.LOCATION)
        }

        override fun isShadedInGui(): Boolean {
            return true
        }

        override fun isSideLit(): Boolean {
            return true
        }

        override fun useSmoothLighting(): Boolean {
            return true
        }

        override fun getCameraTransforms(): ItemCameraTransforms {
            return ItemCameraTransforms.DEFAULT
        }

        override fun getCombinedTransform(): IModelTransform {
            return ModelRotation.X0_Y0
        }
    }

    companion object {
        fun of(modelLocation: String): ModelHandle {
            return ModelHandle(ResourceLocation(modelLocation))
        }
    }
}