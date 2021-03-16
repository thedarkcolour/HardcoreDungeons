package thedarkcolour.hardcoredungeons.client.model.armor

import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.inventory.EquipmentSlotType

// Made in BlockBench :)
class MushroomArmorModel(slot: EquipmentSlotType) : ArmorModel(slot) {
    private val helmet: ModelRenderer
    private val leftShoulder: ModelRenderer
    private val rightShoulder: ModelRenderer

    init {
        textureWidth  = 64
        textureHeight = 32

        helmet = ModelRenderer(this)
        helmet.setRotationPoint(0.0f, 0.0f, 0.0f)
        helmet.setTextureOffset(0, 0).addBox(-5.0f, -10.0f, -5.0f, 10.0f, 5.0f, 10.0f, 0.0f, false)

        leftShoulder = ModelRenderer(this)
        leftShoulder.setRotationPoint(5.0f, 2.0f, 0.0f)
        leftShoulder.setTextureOffset(30, 0).addBox(-1.5f, -3.0f, -2.5f, 5.0f, 5.0f, 5.0f, 0.0f, false)

        rightShoulder = ModelRenderer(this)
        rightShoulder.setRotationPoint(-5.0f, 2.0f, 0.0f)
        rightShoulder.setTextureOffset(30, 0).addBox(-3.5f, -3.0f, -2.5f, 5.0f, 5.0f, 5.0f, 0.0f, false)

        if (slot == EquipmentSlotType.HEAD) {
            leftShoulder.showModel = false
            rightShoulder.showModel = false
        } else if (slot == EquipmentSlotType.CHEST) {
            helmet.showModel = false
        }

        bipedHead = helmet
        bipedLeftArm = leftShoulder
        bipedRightArm = rightShoulder
    }

    private val headParts = listOf(helmet)
    private val bodyParts = listOf(leftShoulder, rightShoulder)

    override fun getHeadParts(): Iterable<ModelRenderer> {
        return headParts
    }

    override fun getBodyParts(): Iterable<ModelRenderer> {
        return bodyParts
    }
}