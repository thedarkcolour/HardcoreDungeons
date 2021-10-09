package thedarkcolour.hardcoredungeons.client.model.armor

import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.item.ArmorStandEntity
import net.minecraft.inventory.EquipmentSlotType
import thedarkcolour.hardcoredungeons.util.PI

// Thanks to Botania
open class ArmorModel(val slot: EquipmentSlotType) : BipedModel<LivingEntity>(1.0f) {
    override fun setupAnim(
        entityIn: LivingEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
    ) {
        if (entityIn !is ArmorStandEntity) {
            return super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        }

        head.xRot = ONE_DEGREES * entityIn.headPose.x
        head.yRot = ONE_DEGREES * entityIn.headPose.y
        head.zRot = ONE_DEGREES * entityIn.headPose.z
        head.setPos(0.0f, 1.0f, 0.0f)
        body.xRot = ONE_DEGREES * entityIn.bodyPose.x
        body.yRot = ONE_DEGREES * entityIn.bodyPose.y
        body.zRot = ONE_DEGREES * entityIn.bodyPose.z
        leftArm.xRot = ONE_DEGREES * entityIn.leftArmPose.x
        leftArm.yRot = ONE_DEGREES * entityIn.leftArmPose.y
        leftArm.zRot = ONE_DEGREES * entityIn.leftArmPose.z
        rightArm.xRot = ONE_DEGREES * entityIn.rightArmPose.x
        rightArm.yRot = ONE_DEGREES * entityIn.rightArmPose.y
        rightArm.zRot = ONE_DEGREES * entityIn.rightArmPose.z
        leftLeg.xRot = ONE_DEGREES * entityIn.leftLegPose.x
        leftLeg.yRot = ONE_DEGREES * entityIn.leftLegPose.y
        leftLeg.zRot = ONE_DEGREES * entityIn.leftLegPose.z
        leftLeg.setPos(1.9f, 11.0f, 0.0f)
        rightLeg.xRot = ONE_DEGREES * entityIn.rightLegPose.x
        rightLeg.yRot = ONE_DEGREES * entityIn.rightLegPose.y
        rightLeg.zRot = ONE_DEGREES * entityIn.rightLegPose.z
        rightLeg.setPos(-1.9f, 11.0f, 0.0f)
        hat.copyFrom(head)
    }

    protected fun setRotateAngle(modelRenderer: ModelRenderer, x: Float, y: Float, z: Float) {
        modelRenderer.xRot = x
        modelRenderer.yRot = y
        modelRenderer.zRot = z
    }

    companion object {
        const val ONE_DEGREES = PI / 180.0f
    }
}