package thedarkcolour.hardcoredungeons.client.model.entity

import net.minecraft.client.model.geom.ModelPart
import thedarkcolour.hardcoredungeons.legacy.modelConfigure


/**
 * Made with BlockBench
 */
class StagModel(root: ModelPart) : DoeModel(root) {
    @Suppress("DuplicatedCode")
    fun stagModel() = doeModel().then {
        texHeight = 64
        texWidth = 64

        val antlerLeft1 by this
        antlerLeft1.setPos(-2.0f, -4.5f, 0.5f)
        antlerLeft1.texOffs(0, 32).addBox(-0.5f, -4.6303f, -0.2732f, 1.0f, 5.0f, 1.0f, 0.0f, false)
        antlerLeft1.xRot = -0.7854f

        val antlerLeft2 by this
        antlerLeft2.setPos(-2.0f, -7.5f, 3.0f)
        antlerLeft2.setRotationAngle(-0.9599f, -0.6981f, -0.1745f)
        antlerLeft2.texOffs(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0F, false)

        val antlerLeft3 by this
        antlerLeft3.setPos(-4.0f, -9.25f, 5.5f)
        antlerLeft3.setRotationAngle(-0.6109f, -0.7854f, 0.0f)
        antlerLeft3.texOffs(0, 38).addBox(-0.2934f, -6.25f, -0.0483f, 1.0f, 6.0f, 1.0f, 0.0f, false)

        val antlerLeft4 by this
        antlerLeft4.setPos(-2.0f, -7.5f, 3.0f)
        antlerLeft4.setRotationAngle( -0.9599f, -1.3963f, -0.1745f)
        antlerLeft4.texOffs(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0f, false)

        val antlerLeft5 by this
        antlerLeft5.setPos(-5.5f, -9.0F, 4.0F)
        antlerLeft5.setRotationAngle(0.1745f, 0.0873f, -0.6981f)
        antlerLeft5.texOffs(4, 38).addBox(-0.5807f, -5.1f, -0.4515f, 1.0f, 5.0f, 1.0f, 0.0F, false)

        val antlerLeft6 by this
        antlerLeft6.setPos(-2.5f, -7.75f, 3.75f)
        antlerLeft6.setRotationAngle(-0.9599f, 0.0873f, 0.0873f)
        antlerLeft6.texOffs(8, 38).addBox(-0.0781f, -4.2138f, -0.2729f, 1.0f, 4.0f, 1.0f, 0.0F, false)

        val antlerLeft7 by this
        antlerLeft7.setPos(-2.0F, -9.75f, 6.75f)
        antlerLeft7.setRotationAngle(-0.6109f, -0.0873f, 0.0F)
        antlerLeft7.texOffs(8, 38).addBox(-0.0444f, -4.3335f, -0.2096f, 1.0f, 4.0f, 1.0f, 0.0F, false)

        val antlerRight1 by this
        antlerRight1.setPos(-2.0f, -4.5f, 0.5f)
        antlerRight1.xRot = -0.7854f
        antlerRight1.texOffs(0, 32).addBox(3.5f, -4.6303f, -0.2732f, 1.0f, 5.0f, 1.0f, 0.0f, true)

        val antlerRight2 by this
        antlerRight2.setPos(2.0F, -7.5F, 3.0F)
        antlerRight2.setRotationAngle(-0.9599F, 0.6981F, 0.1745F)
        antlerRight2.texOffs(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0F, true)

        val antlerRight3 by this
        antlerRight3.setPos(4.0F, -9.25F, 5.5F)
        antlerRight3.setRotationAngle(-0.6109F, 0.7854F, 0.0F)
        antlerRight3.texOffs(0, 38).addBox(-0.7066F, -6.25F, -0.0483F, 1.0f, 6.0f, 1.0f, 0.0f, true)

        val antlerRight4 by this
        antlerRight4.setPos(2.0F, -7.5F, 3.0F)
        antlerRight4.setRotationAngle( -0.9599F, 1.3963F, 0.1745F)
        antlerRight4.texOffs(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0f, true)

        val antlerRight5 by this
        antlerRight5.setPos(5.5F, -9.0F, 4.0F)
        antlerRight5.setRotationAngle(0.1745F, -0.0873F, 0.6981F)
        antlerRight5.texOffs(4, 38).addBox(-0.4193F, -5.1F, -0.4515F, 1.0f, 5.0f, 1.0f, 0.0F, true)

        val antlerRight6 by this
        antlerRight6.setPos(2.5F, -7.75F, 3.75F)
        antlerRight6.setRotationAngle(-0.9599F, -0.0873F, -0.0873F)
        antlerRight6.texOffs(8, 38).addBox(-0.9219F, -4.2138F, -0.2729F, 1.0f, 4.0f, 1.0f, 0.0F, true)

        val antlerRight7 by this
        antlerRight7.setPos(2.0F, -9.75F, 6.75F)
        antlerRight7.setRotationAngle(-0.6109F, 0.0873F, 0.0F)
        antlerRight7.texOffs(8, 38).addBox(-0.9556F, -4.3335F, -0.2096F, 1.0f, 4.0f, 1.0f, 0.0F, true)

        "face".addChild(antlerLeft1)
        "face".addChild(antlerLeft2)
        "face".addChild(antlerLeft3)
        "face".addChild(antlerLeft4)
        "face".addChild(antlerLeft5)
        "face".addChild(antlerLeft6)
        "face".addChild(antlerLeft7)
        "face".addChild(antlerRight1)
        "face".addChild(antlerRight2)
        "face".addChild(antlerRight3)
        "face".addChild(antlerRight4)
        "face".addChild(antlerRight5)
        "face".addChild(antlerRight6)
        "face".addChild(antlerRight7)
    }
}