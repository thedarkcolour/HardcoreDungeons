package thedarkcolour.hardcoredungeons.particle

import net.minecraft.client.particle.*
import net.minecraft.client.particle.IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.world.ClientWorld
import net.minecraft.particles.BasicParticleType
import net.minecraft.util.math.MathHelper
import kotlin.math.max

class SoulFrayParticle(
    worldIn: ClientWorld,
    x: Double, y: Double, z: Double,
    mX: Double, mY: Double, mZ: Double,
    private val cloudSprite: IAnimatedSprite
) :
    SpriteTexturedParticle(worldIn, x, y, z, mX, mY, mZ) {

    init {
        motionX *= 0.1f.toDouble()
        motionY *= 0.1f.toDouble()
        motionZ *= 0.1f.toDouble()
        motionX += mX
        motionY += mY
        motionZ += mZ
        val f1 = 1.0f - (Math.random() * 0.3f.toDouble()).toFloat()
        particleRed = f1
        particleGreen = f1
        particleBlue = f1
        particleScale *= 1.875f
        val i = (8.0 / (Math.random() * 0.8 + 0.3)).toInt()
        maxAge = max(i.toFloat() * 2.5f, 1.0f).toInt()
        canCollide = false
        selectSpriteWithAge(cloudSprite)
    }

    override fun getRenderType(): IParticleRenderType {
        return PARTICLE_SHEET_TRANSLUCENT
    }

    override fun getScale(partialTicks: Float): Float {
        return particleScale * MathHelper.clamp((age + partialTicks) / maxAge * 32.0f, 0.0f, 1.0f)
    }

    override fun tick() {
        prevPosX = posX
        prevPosY = posY
        prevPosZ = posZ
        if (age++ >= maxAge) {
            setExpired()
        } else {
            selectSpriteWithAge(cloudSprite)
            move(motionX, motionY, motionZ)
            motionX *= 0.96f.toDouble()
            motionY *= 0.96f.toDouble()
            motionZ *= 0.96f.toDouble()
            val playerentity = world.getClosestPlayer(posX, posY, posZ, 2.0, false)
            if (playerentity != null) {
                val d0 = playerentity.posY
                if (posY > d0) {
                    posY += (d0 - posY) * 0.2
                    motionY += (playerentity.motion.y - motionY) * 0.2
                    setPosition(posX, posY, posZ)
                }
            }
            if (onGround) {
                motionX *= 0.7f.toDouble()
                motionZ *= 0.7f.toDouble()
            }
        }
    }

    override fun getBrightnessForRender(partialTick: Float): Int {
        return FULL_LIGHT
    }

    class Factory(private val spriteSet: IAnimatedSprite) : IParticleFactory<BasicParticleType> {
        override fun makeParticle(
            typeIn: BasicParticleType,
            worldIn: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            mX: Double,
            mY: Double,
            mZ: Double
        ): Particle {
            val particle = SoulFrayParticle(worldIn, x, y, z, mX, mY, mZ, spriteSet)
            particle.color(50, 230, 255)
            particle.setAlphaF(0.4f)
            return particle
        }
    }

    companion object {
        private val FULL_LIGHT = LightTexture.packLight(15, 15)
    }
}