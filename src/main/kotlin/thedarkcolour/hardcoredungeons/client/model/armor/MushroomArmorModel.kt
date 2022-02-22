package thedarkcolour.hardcoredungeons.client.model.armor

import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.inventory.EquipmentSlotType

// Made in BlockBench
class MushroomArmorModel(slot: EquipmentSlotType) : ArmorModel() {
    private val helmet: ModelRenderer
    private val leftShoulder: ModelRenderer
    private val rightShoulder: ModelRenderer

    init {
        texWidth  = 64
        texHeight = 32

        helmet = ModelRenderer(this)
        helmet.setPos(0.0f, 0.0f, 0.0f)
        helmet.texOffs(0, 0).addBox(-5.0f, -10.0f, -5.0f, 10.0f, 5.0f, 10.0f, 0.0f, false)

        leftShoulder = ModelRenderer(this)
        leftShoulder.setPos(5.0f, 2.0f, 0.0f)
        leftShoulder.texOffs(30, 0).addBox(-1.5f, -3.0f, -2.5f, 5.0f, 5.0f, 5.0f, 0.0f, false)

        rightShoulder = ModelRenderer(this)
        rightShoulder.setPos(-5.0f, 2.0f, 0.0f)
        rightShoulder.texOffs(30, 0).addBox(-3.5f, -3.0f, -2.5f, 5.0f, 5.0f, 5.0f, 0.0f, false)

        if (slot == EquipmentSlotType.HEAD) {
            leftShoulder.visible = false
            rightShoulder.visible = false
        } else if (slot == EquipmentSlotType.CHEST) {
            helmet.visible = false
        }

        head = helmet
        leftArm = leftShoulder
        rightArm = rightShoulder
    }

    private val headParts = listOf(helmet)
    private val bodyParts = listOf(leftShoulder, rightShoulder)

    override fun headParts(): Iterable<ModelRenderer> {
        return headParts
    }

    override fun bodyParts(): Iterable<ModelRenderer> {
        return bodyParts
    }
}