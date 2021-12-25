package thedarkcolour.hardcoredungeons.client.model.entity

import net.minecraft.client.renderer.entity.model.AgeableModel
import net.minecraft.client.renderer.model.ModelRenderer
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
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
        chest.setPos(1.0f, 25.0f, 0.0f)
        chest.texOffs(0, 49).addBox(-4.5f, -18.0f, -5.0f, 7.0f, 5.0f, 10.0f, 0.0f, false)
        chest.texOffs(0, 0).addBox(-5.0f, -23.0f, -8.0f, 8.0f, 5.0f, 16.0f, 0.0f, false)
        chest.texOffs(34, 30).addBox(-6.0f, -37.0f, -10.0f, 10.0f, 14.0f, 20.0f, 0.0f, false)

        legRight = ModelRenderer(this)
        legRight.setPos(0.0f, 18.0f, 2.5f)
        legRight.texOffs(74, 33).addBox(-2.0f, -6.0f, -1.0f, 5.0f, 12.0f, 5.0f, 0.0f, false)

        legLeft = ModelRenderer(this)
        legLeft.setPos(1.0f, 18.0f, -2.5f)
        legLeft.texOffs(74, 33).addBox(-3.0f, -6.0f, -4.0f, 5.0f, 12.0f, 5.0f, 0.0f, false)

        armLeft = ModelRenderer(this)
        armLeft.setPos(0.0f, -5.0f, -10.0f)
        armLeft.texOffs(100, 22).addBox(-3.5f, -6.0f, -7.0f, 7.0f, 35.0f, 7.0f, 0.0f, false)

        shoulderLeft = ModelRenderer(this)
        shoulderLeft.setPos(0.0f, 4.75f, 3.25f)
        armLeft.addChild(shoulderLeft)
        setRotationAngle(shoulderLeft, 0.6109f, 0.0f, 0.0f)
        shoulderLeft.texOffs(0, 21).addBox(-5.5f, -18.5798f, -5.3671f, 11.0f, 11.0f, 11.0f, 0.0f, false)

        spikeX = ModelRenderer(this)
        spikeX.setPos(2.0f, -9.0f, 0.0f)
        shoulderLeft.addChild(spikeX)
        spikeX.texOffs(0, 26).addBox(-9.5f, -5.5798f, -1.3671f, 2.0f, 3.0f, 3.0f, 0.0f, false)
        spikeX.texOffs(0, 26).addBox(3.5f, -5.5798f, -1.3671f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        spikeY = ModelRenderer(this)
        spikeY.setPos(10.0f, -7.0f, 0.0f)
        shoulderLeft.addChild(spikeY)
        setRotationAngle(spikeY, 0.0f, 0.0f, 1.5708f)
        spikeY.texOffs(0, 26).addBox(-13.3298f, 8.5f, -1.3671f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        spikeZ = ModelRenderer(this)
        spikeZ.setPos(0.0f, -3.25f, -6.75f)
        shoulderLeft.addChild(spikeZ)
        setRotationAngle(spikeZ, 0.0f, -1.5708f, 0.0f)
        spikeZ.texOffs(0, 26).addBox(-0.6171f, -11.3298f, -1.5f, 2.0f, 3.0f, 3.0f, 0.0f, false)
        spikeZ.texOffs(0, 26).addBox(12.3829f, -11.3298f, -1.5f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        head = ModelRenderer(this)
        head.setPos(-5.0f, -5.0f, 0.0f)
        head.texOffs(48, 0).addBox(-4.0f, -15.0f, -4.0f, 8.0f, 10.0f, 8.0f, 0.0f, false)
        head.texOffs(32, 7).addBox(-7.0f, -10.0f, -1.5f, 3.0f, 6.0f, 3.0f, 0.0f, false)
        head.texOffs(92, 0).addBox(-4.5f, -15.5f, -4.5f, 9.0f, 7.0f, 9.0f, 0.0f, false)

        armRight = ModelRenderer(this)
        armRight.setPos(0.0f, -5.0f, 10.0f)
        armRight.texOffs(100, 22).addBox(-3.5f, -6.0f, 0.0f, 7.0f, 35.0f, 7.0f, 0.0f, false)

        shoulderRight = ModelRenderer(this)
        shoulderRight.setPos(0.0f, 4.75f, -3.25f)
        armRight.addChild(shoulderRight)
        setRotationAngle(shoulderRight, -0.6109f, 0.0f, 0.0f)
        shoulderRight.texOffs(0, 21).addBox(-5.5f, -18.5798f, -5.6329f, 11.0f, 11.0f, 11.0f, 0.0f, false)

        spikeX2 = ModelRenderer(this)
        spikeX2.setPos(2.0f, -9.0f, 0.0f)
        shoulderRight.addChild(spikeX2)
        spikeX2.texOffs(0, 26).addBox(-9.5f, -5.5798f, -1.6329f, 2.0f, 3.0f, 3.0f, 0.0f, false)
        spikeX2.texOffs(0, 26).addBox(3.5f, -5.5798f, -1.6329f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        spikeY2 = ModelRenderer(this)
        spikeY2.setPos(10.0f, -7.0f, 0.0f)
        shoulderRight.addChild(spikeY2)
        setRotationAngle(spikeY2, 0.0f, 0.0f, 1.5708f)
        spikeY2.texOffs(0, 26).addBox(-13.3298f, 8.5f, -1.6329f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        spikeZ2 = ModelRenderer(this)
        spikeZ2.setPos(0.0f, -3.25f, 6.75f)
        shoulderRight.addChild(spikeZ2)
        setRotationAngle(spikeZ2, 0.0f, 1.5708f, 0.0f)
        spikeZ2.texOffs(0, 26).addBox(-0.6171f, -11.3298f, -1.5f, 2.0f, 3.0f, 3.0f, 0.0f, false)
        spikeZ2.texOffs(0, 26).addBox(12.3829f, -11.3298f, -1.5f, 2.0f, 3.0f, 3.0f, 0.0f, false)

        headParts = listOf(head)
        bodyParts = listOf(chest, legLeft, legRight, armLeft, armRight)
    }

    private fun setRotationAngle(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.xRot = x
        model.yRot = y
        model.zRot = z
    }

    override fun setupAnim(
        entityIn: KnightlyJuggernautEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
    ) {
        head.yRot = netHeadYaw * (Math.PI.toFloat() / 180f)
        head.xRot = headPitch * (Math.PI.toFloat() / 180f)
        legLeft.zRot = -1.5f * triangleWave(limbSwing) * limbSwingAmount
        legLeft.yRot = 0.0f
        legRight.zRot = 1.5f * triangleWave(limbSwing) * limbSwingAmount
        legRight.yRot = 0.0f
    }

    private fun triangleWave(limbSwing: Float): Float {
        return (abs(limbSwing % 13.0f - 13.0f * 0.5f) - 13.0f * 0.25f) / (13.0f * 0.25f)
    }

    override fun headParts() = headParts
    override fun bodyParts() = bodyParts
}