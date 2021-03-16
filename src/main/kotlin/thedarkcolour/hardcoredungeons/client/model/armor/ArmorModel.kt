package thedarkcolour.hardcoredungeons.client.model.armor

import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.item.ArmorStandEntity
import net.minecraft.inventory.EquipmentSlotType
import thedarkcolour.hardcoredungeons.util.PI





open class ArmorModel(val slot: EquipmentSlotType) : BipedModel<LivingEntity>(1.0f) {
    override fun setRotationAngles(
        entityIn: LivingEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float,
    ) {
        if (entityIn !is ArmorStandEntity) {
            return super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        }

        bipedHead.rotateAngleX = ONE_RADIAN * entityIn.headRotation.x
        bipedHead.rotateAngleY = ONE_RADIAN * entityIn.headRotation.y
        bipedHead.rotateAngleZ = ONE_RADIAN * entityIn.headRotation.z
        bipedHead.setRotationPoint(0.0f, 1.0f, 0.0f)
        bipedBody.rotateAngleX = ONE_RADIAN * entityIn.bodyRotation.x
        bipedBody.rotateAngleY = ONE_RADIAN * entityIn.bodyRotation.y
        bipedBody.rotateAngleZ = ONE_RADIAN * entityIn.bodyRotation.z
        bipedLeftArm.rotateAngleX = ONE_RADIAN * entityIn.leftArmRotation.x
        bipedLeftArm.rotateAngleY = ONE_RADIAN * entityIn.leftArmRotation.y
        bipedLeftArm.rotateAngleZ = ONE_RADIAN * entityIn.leftArmRotation.z
        bipedRightArm.rotateAngleX = ONE_RADIAN * entityIn.rightArmRotation.x
        bipedRightArm.rotateAngleY = ONE_RADIAN * entityIn.rightArmRotation.y
        bipedRightArm.rotateAngleZ = ONE_RADIAN * entityIn.rightArmRotation.z
        bipedLeftLeg.rotateAngleX = ONE_RADIAN * entityIn.leftLegRotation.x
        bipedLeftLeg.rotateAngleY = ONE_RADIAN * entityIn.leftLegRotation.y
        bipedLeftLeg.rotateAngleZ = ONE_RADIAN * entityIn.leftLegRotation.z
        bipedLeftLeg.setRotationPoint(1.9f, 11.0f, 0.0f)
        bipedRightLeg.rotateAngleX = ONE_RADIAN * entityIn.rightLegRotation.x
        bipedRightLeg.rotateAngleY = ONE_RADIAN * entityIn.rightLegRotation.y
        bipedRightLeg.rotateAngleZ = ONE_RADIAN * entityIn.rightLegRotation.z
        bipedRightLeg.setRotationPoint(-1.9f, 11.0f, 0.0f)
        bipedHeadwear.copyModelAngles(bipedHead)
    }

    protected fun setRotateAngle(modelRenderer: ModelRenderer, x: Float, y: Float, z: Float) {
        modelRenderer.rotateAngleX = x
        modelRenderer.rotateAngleY = y
        modelRenderer.rotateAngleZ = z
    }

    companion object {
        const val ONE_RADIAN = PI / 180.0f
    }
}