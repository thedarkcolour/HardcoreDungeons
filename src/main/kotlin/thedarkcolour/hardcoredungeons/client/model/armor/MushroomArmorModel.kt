package thedarkcolour.hardcoredungeons.client.model.armor

import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.EquipmentSlot
import thedarkcolour.hardcoredungeons.legacy.modelConfigure

// Made in BlockBench
class MushroomArmorModel(root: ModelPart, slot: EquipmentSlot) : ArmorModel(root, slot) {
    fun armorModel() = modelConfigure {
        texWidth  = 64
        texHeight = 32

        // These names should not be modified and must match HumanoidModel
        // Positions are hardcoded, so don't bother changing them
        val head by this
        head.texOffs(0, 0).addBox(-5.0f, -10.0f, -5.0f, 10.0f, 5.0f, 10.0f, 0.0f, false)

        val leftArm by this
        leftArm.texOffs(30, 0).addBox(-1.5f, -3.0f, -2.5f, 5.0f, 5.0f, 5.0f, 0.0f, false)

        val rightArm by this
        rightArm.texOffs(30, 0).addBox(-3.5f, -3.0f, -2.5f, 5.0f, 5.0f, 5.0f, 0.0f, false)
    }
}