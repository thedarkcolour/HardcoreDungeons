package thedarkcolour.hardcoredungeons.entity.castleton.deer

import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.setRotationAngle


/**
 * Made with BlockBench
 */
class StagModel : DoeModel() {
    init {
        textureHeight = 64
        textureWidth = 64

        val antlerLeft1 = ModelRenderer(this)
        antlerLeft1.setRotationPoint(-2.0f, -4.5f, 0.5f)
        antlerLeft1.setTextureOffset(0, 32).addBox(-0.5f, -4.6303f, -0.2732f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        antlerLeft1.rotateAngleX = -0.7854f

        val antlerLeft2 = ModelRenderer(this)
        antlerLeft2.setRotationPoint(-2.0f, -7.5f, 3.0f)
        antlerLeft2.setRotationAngle(-0.9599f, -0.6981f, -0.1745f)
        antlerLeft2.setTextureOffset(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0F, false)

        val antlerLeft3 = ModelRenderer(this)
        antlerLeft3.setRotationPoint(-4.0f, -9.25f, 5.5f)
        antlerLeft3.setRotationAngle(-0.6109f, -0.7854f, 0.0f)
        antlerLeft3.setTextureOffset(0, 38).addBox(-0.2934f, -6.25f, -0.0483f, 1.0f, 6.0f, 1.0f, 0.0f, false)

        val antlerLeft4 = ModelRenderer(this)
        antlerLeft4.setRotationPoint(-2.0f, -7.5f, 3.0f)
        antlerLeft4.setRotationAngle( -0.9599f, -1.3963f, -0.1745f)
        antlerLeft4.setTextureOffset(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0f, false)

        val antlerLeft5 = ModelRenderer(this)
        antlerLeft5.setRotationPoint(-5.5f, -9.0F, 4.0F)
        antlerLeft5.setRotationAngle(0.1745f, 0.0873f, -0.6981f)
        antlerLeft5.setTextureOffset(4, 38).addBox(-0.5807f, -5.1f, -0.4515f, 1.0f, 5.0f, 1.0f, 0.0F, false)

        val antlerLeft6 = ModelRenderer(this)
        antlerLeft6.setRotationPoint(-2.5f, -7.75f, 3.75f)
        antlerLeft6.setRotationAngle(-0.9599f, 0.0873f, 0.0873f)
        antlerLeft6.setTextureOffset(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0F, false)

        val antlerLeft7 = ModelRenderer(this)
        antlerLeft7.setRotationPoint(-2.0F, -9.75f, 6.75f)
        antlerLeft7.setRotationAngle(-0.6109f, -0.0873f, 0.0F)
        antlerLeft7.setTextureOffset(8, 38).addBox(-0.0444f, -4.3335f, -0.2096f, 1.0f, 4.0f, 1.0f, 0.0F, false)

        val antlerRight1 = ModelRenderer(this)
        antlerRight1.setRotationPoint(-2.0f, -4.5f, 0.5f)
        antlerRight1.rotateAngleX = -0.7854f
        antlerRight1.setTextureOffset(0, 32).addBox(3.5f, -4.6303f, -0.2732f, 1.0f, 5.0f, 1.0f, 0.0f, true)

        val antlerRight2 = ModelRenderer(this)
        antlerRight2.setRotationPoint(2.0F, -7.5F, 3.0F)
        antlerRight2.setRotationAngle(-0.9599F, 0.6981F, 0.1745F)
        antlerRight2.setTextureOffset(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0F, true)

        val antlerRight3 = ModelRenderer(this)
        antlerRight3.setRotationPoint(4.0F, -9.25F, 5.5F)
        antlerRight3.setRotationAngle(-0.6109F, 0.7854F, 0.0F)
        antlerRight3.setTextureOffset(0, 38).addBox(-0.7066F, -6.25F, -0.0483F, 1.0f, 6.0f, 1.0f, 0.0f, true)

        val antlerRight4 = ModelRenderer(this)
        antlerRight4.setRotationPoint(2.0F, -7.5F, 3.0F)
        antlerRight4.setRotationAngle( -0.9599F, 1.3963F, 0.1745F)
        antlerRight4.setTextureOffset(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0f, true)

        val antlerRight5 = ModelRenderer(this)
        antlerRight5.setRotationPoint(5.5F, -9.0F, 4.0F)
        antlerRight5.setRotationAngle(0.1745F, -0.0873F, 0.6981F)
        antlerRight5.setTextureOffset(4, 38).addBox(-0.4193F, -5.1F, -0.4515F, 1.0f, 5.0f, 1.0f, 0.0F, true)

        val antlerRight6 = ModelRenderer(this)
        antlerRight6.setRotationPoint(2.5F, -7.75F, 3.75F)
        antlerRight6.setRotationAngle(-0.9599F, -0.0873F, -0.0873F)
        antlerRight6.setTextureOffset(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0F, true)

        val antlerRight7 = ModelRenderer(this)
        antlerRight7.setRotationPoint(2.0F, -9.75F, 6.75F)
        antlerRight7.setRotationAngle(-0.6109F, 0.0873F, 0.0F)
        antlerRight7.setTextureOffset(8, 38).addBox(-0.9556F, -4.3335F, -0.2096F, 1.0f, 4.0f, 1.0f, 0.0F, true)

        face.addChild(antlerLeft1)
        face.addChild(antlerLeft2)
        face.addChild(antlerLeft3)
        face.addChild(antlerLeft4)
        face.addChild(antlerLeft5)
        face.addChild(antlerLeft6)
        face.addChild(antlerLeft7)
        face.addChild(antlerRight1)
        face.addChild(antlerRight2)
        face.addChild(antlerRight3)
        face.addChild(antlerRight4)
        face.addChild(antlerRight5)
        face.addChild(antlerRight6)
        face.addChild(antlerRight7)
    }
}