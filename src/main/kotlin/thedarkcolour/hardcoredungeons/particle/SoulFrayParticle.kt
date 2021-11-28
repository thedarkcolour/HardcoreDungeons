package thedarkcolour.hardcoredungeons.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*
import net.minecraft.client.particle.ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT
import net.minecraft.client.renderer.LightTexture
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.util.Mth
import kotlin.math.max

class SoulFrayParticle(
    worldIn: ClientLevel,
    x: Double, y: Double, z: Double,
    mX: Double, mY: Double, mZ: Double,
    private val cloudSprite: SpriteSet
) : TextureSheetParticle(worldIn, x, y, z, mX, mY, mZ) {

    init {
        xd *= 0.1f.toDouble()
        yd *= 0.1f.toDouble()
        zd *= 0.1f.toDouble()
        xd += mX
        yd += mY
        zd += mZ
        val f1 = 1.0f - (Math.random() * 0.3f.toDouble()).toFloat()
        rCol = f1
        gCol = f1
        bCol = f1
        quadSize *= 1.875f
        val i = (8.0 / (Math.random() * 0.8 + 0.3)).toInt()
        lifetime = max(i.toFloat() * 2.5f, 1.0f).toInt()
        hasPhysics = false
        setSpriteFromAge(cloudSprite)
    }

    override fun getRenderType(): ParticleRenderType {
        return PARTICLE_SHEET_TRANSLUCENT
    }

    override fun getQuadSize(partialTicks: Float): Float {
        return quadSize * Mth.clamp((age + partialTicks) / lifetime * 32.0f, 0.0f, 1.0f)
    }

    override fun tick() {
        xo = x
        yo = y
        zo = z
        if (age++ >= lifetime) {
            remove()
        } else {
            setSpriteFromAge(cloudSprite)
            move(xd, yd, zd)
            xd *= 0.96f.toDouble()
            yd *= 0.96f.toDouble()
            zd *= 0.96f.toDouble()
            val playerentity = level.getNearestPlayer(x, y, z, 2.0, false)
            if (playerentity != null) {
                val d0 = playerentity.y
                if (y > d0) {
                    y += (d0 - y) * 0.2
                    yd += (playerentity.deltaMovement.y - yd) * 0.2
                    setPos(x, y, z)
                }
            }
            if (onGround) {
                xd *= 0.7f.toDouble()
                zd *= 0.7f.toDouble()
            }
        }
    }

    override fun getLightColor(partialTick: Float): Int {
        return FULL_LIGHT
    }

    class Factory(private val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(
            typeIn: SimpleParticleType,
            worldIn: ClientLevel,
            x: Double, y: Double, z: Double,
            mX: Double, mY: Double, mZ: Double
        ): Particle {
            val particle = SoulFrayParticle(worldIn, x, y, z, mX, mY, mZ, spriteSet)
            particle.color(50, 230, 255)
            particle.setAlpha(0.4f)
            return particle
        }
    }

    companion object {
        private val FULL_LIGHT = LightTexture.pack(15, 15)
    }
}