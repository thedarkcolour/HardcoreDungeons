package thedarkcolour.hardcoredungeons.particle

import net.minecraft.client.particle.TexturedParticle

fun TexturedParticle.color(r: Int, g: Int, b: Int) {
    setColor(r / 255.0f, g / 255.0f, b / 255.0f)
}