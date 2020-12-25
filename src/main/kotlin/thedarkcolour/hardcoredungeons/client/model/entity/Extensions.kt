package thedarkcolour.hardcoredungeons.client.model.entity

import net.minecraft.client.renderer.model.ModelRenderer

// Helper function from BlockBench
fun ModelRenderer.setRotationAngle(x: Float, y: Float, z: Float) {
    rotateAngleX = x
    rotateAngleY = y
    rotateAngleZ = z
}