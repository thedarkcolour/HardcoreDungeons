package thedarkcolour.hardcoredungeons.entity

import net.minecraft.client.renderer.model.ModelRenderer

fun ModelRenderer.setRotationAngle(x: Float, y: Float, z: Float) {
    rotateAngleX = x
    rotateAngleY = y
    rotateAngleZ = z
}