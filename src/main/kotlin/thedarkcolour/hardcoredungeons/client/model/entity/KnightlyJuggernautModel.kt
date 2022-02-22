package thedarkcolour.hardcoredungeons.client.model.entity

import net.minecraft.client.renderer.entity.model.AgeableModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.util.toRadians
import kotlin.math.abs

/**
 * Huge thanks to BlockBench for adding 1.15 compatibility!
 */
class KnightlyJuggernautModel : AgeableModel<KnightlyJuggernautEntity>(false, 24.0f, 2.0f) {
    private var chest: ModelRenderer
    private var legRight: ModelRenderer
    private var legLeft: ModelRenderer
    private var armLeft: ModelRenderer
    private var shoulderLeft: ModelRenderer
    private var spikeX: ModelRenderer
    private var spikeY: ModelRenderer
    private var spikeZ: ModelRenderer
    private var head: ModelRenderer
    private var armRight: ModelRenderer
    private var shoulderRight: ModelRenderer
    private var spikeX2: ModelRenderer
    private var spikeY2: ModelRenderer
    private var spikeZ2: ModelRenderer

    private val headParts: List<ModelRenderer>
    private val bodyParts: List<ModelRenderer>

    init {
        texWidth = 128
        texHeight = 64

        chest = ModelRenderer(this)
        chest.setPos(0.0f, 24.0f, 0.0f)
        chest.texOffs(0, 52).addBox(-5.0f, -17.0f, -3.5f, 10.0f, 5.0f, 7.0f, 0.0f, false)
        chest.texOffs(0, 0).addBox(-8.0f, -22.0f, -4.0f, 16.0f, 5.0f, 8.0f, 0.0f, false)
        chest.texOffs(34, 40).addBox(-10.0f, -36.0f, -5.0f, 20.0f, 14.0f, 10.0f, 0.0f, false)

        legRight = ModelRenderer(this)
        legRight.setPos(-2.5f, 12.0f, 0.5f)
        legRight.texOffs(44, 23).addBox(-4.0f, 0.0f, -2.5f, 5.0f, 12.0f, 5.0f, 0.0f, true)

        legLeft = ModelRenderer(this)
        legLeft.setPos(2.5f, 12.0f, 0.5f)
        legLeft.texOffs(44, 23).addBox(-1.0f, 0.0f, -2.5f, 5.0f, 12.0f, 5.0f, 0.0f, false)

        head = ModelRenderer(this)
        head.setPos(0.0f, -12.0f, -5.0f)
        head.texOffs(48, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false)
        head.texOffs(64, 31).addBox(-1.5F, -3.0F, -7.0F, 3.0F, 6.0F, 3.0F, 0.0F, false)
        head.texOffs(92, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 7.0F, 9.0F, 0.0F, false)

        armRight = ModelRenderer(this)
        armRight.setPos(10.0f, -5.0f, 0.0f)
        armRight.texOffs(100, 22).addBox(-27.0f, -6.0f, -3.5f, 7.0f, 35.0f, 7.0f, 0.0f, false)

        shoulderRight = ModelRenderer(this)
        shoulderRight.setPos(-11.25f, -14.75f, 0.0f)
        armRight.addChild(shoulderRight)
        shoulderRight.setRotationAngle(0.0f, 0.0f, -0.6109f)
        shoulderRight.texOffs(0, 21).addBox(-21.0f, -5.75f, -5.5f, 11.0f, 11.0f, 11.0f, 0.0f, false)

        spikeX2 = ModelRenderer(this)
        spikeX2.setPos(4.0f, 4.0f, 10.75f)
        shoulderRight.addChild(spikeX2)
        spikeX2.texOffs(0, 26).addBox(-14.0F, -5.75F, -12.25F, 2.0F, 3.0F, 3.0F, 0.0F, false)
        spikeX2.texOffs(0, 26).addBox(-27.0F, -5.75F, -12.25F, 2.0F, 3.0F, 3.0F, 0.0F, false)

        spikeY2 = ModelRenderer(this)
        spikeY2.setPos(10.0f, 6.0f, 6.75f)
        shoulderRight.addChild(spikeY2)
        spikeY2.setRotationAngle(0.0f, 0.0f, 1.5708f)
        spikeY2.texOffs(0, 26).addBox(-13.75f, 24.0f, -8.25f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        spikeZ2 = ModelRenderer(this)
        spikeZ2.setPos(0.0f, 9.75f, 13.5f)
        shoulderRight.addChild(spikeZ2)
        spikeZ2.setRotationAngle(0.0f, 1.5708f, 0.0f)
        spikeZ2.texOffs(0, 26).addBox(6.0F, -11.5F, -17.0F, 2.0F, 3.0F, 3.0F, 0.0F, false)
        spikeZ2.texOffs(0, 26).addBox(19.0F, -11.5F, -17.0F, 2.0F, 3.0F, 3.0F, 0.0F, false)

        armLeft = ModelRenderer(this)
        armLeft.setPos(-10.0f, -5.0f, 0.0f)
        armLeft.texOffs(100, 22).addBox(20.0f, -6.0f, -3.5f, 7.0f, 35.0f, 7.0f, 0.0f, true)

        shoulderLeft = ModelRenderer(this)
        shoulderLeft.setPos(11.25f, -14.75f, 0.0f)
        armLeft.addChild(shoulderLeft)
        shoulderLeft.setRotationAngle(0.0f, 0.0f, 0.6109f)
        shoulderLeft.texOffs(0, 21).addBox(10.0f, -5.75f, -5.5f, 11.0f, 11.0f, 11.0f, 0.0f, true)

        spikeX = ModelRenderer(this)
        spikeX.setPos(-4.0f, 4.0f, 10.75f)
        shoulderLeft.addChild(spikeX)
        spikeX.texOffs(0, 26).addBox(12.0F, -5.75F, -12.25F, 2.0F, 3.0F, 3.0F, 0.0F, true)
        spikeX.texOffs(0, 26).addBox(25.0F, -5.75F, -12.25F, 2.0F, 3.0F, 3.0F, 0.0F, true)

        spikeY = ModelRenderer(this)
        spikeY.setPos(-10.0f, 6.0f, 6.75f)
        shoulderLeft.addChild(spikeY)
        spikeY.setRotationAngle(0.0f, 0.0f, -1.5708f)
        spikeY.texOffs(0, 26).addBox(11.75f, 24.0f, -8.25f, 2.0f, 3.0f, 3.0f, 0.0f, true)

        spikeZ = ModelRenderer(this)
        spikeZ.setPos(0.0f, 9.75f, 13.5f)
        shoulderLeft.addChild(spikeZ)
        spikeZ.setRotationAngle(0.0f, -1.5708f, 0.0f)
        spikeZ.texOffs(0, 26).addBox(-8.0F, -11.5F, -17.0F, 2.0F, 3.0F, 3.0F, 0.0F, true)
        spikeZ.texOffs(0, 26).addBox(-21.0F, -11.5F, -17.0F, 2.0F, 3.0F, 3.0F, 0.0F, true)

        headParts = listOf(head)
        bodyParts = listOf(chest, legLeft, legRight, armLeft, armRight)
    }

    override fun setupAnim(
        entityIn: KnightlyJuggernautEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
    ) {
        head.xRot = toRadians(headPitch)
        head.yRot = toRadians(netHeadYaw)

        legLeft.xRot = -1.5f * triangleWave(limbSwing) * limbSwingAmount
        legLeft.yRot = 0.0f
        legRight.xRot = 1.5f * triangleWave(limbSwing) * limbSwingAmount
        legRight.yRot = 0.0f

        armLeft.xRot = 1.5f * triangleWave(limbSwing) * -limbSwingAmount
        armRight.xRot = 1.5f * triangleWave(limbSwing) * limbSwingAmount
    }

    private fun triangleWave(limbSwing: Float): Float {
        return (abs(limbSwing % 13.0f - 13.0f * 0.5f) - 13.0f * 0.25f) / (13.0f * 0.25f)
    }

    override fun headParts() = headParts
    override fun bodyParts() = bodyParts
}