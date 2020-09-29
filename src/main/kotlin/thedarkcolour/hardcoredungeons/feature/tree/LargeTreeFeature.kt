package thedarkcolour.hardcoredungeons.feature.tree

import com.mojang.datafixers.Dynamic
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig
import net.minecraft.world.gen.feature.Feature
import thedarkcolour.hardcoredungeons.util.PI
import java.util.*


class LargeTreeFeature<T : BaseTreeFeatureConfig>(deserializer: (Dynamic<*>) -> T) : Feature<T>(deserializer) {
    override fun place(
        worldIn: IWorld,
        generator: ChunkGenerator<out GenerationSettings>,
        rand: Random,
        pos: BlockPos,
        config: T
    ): Boolean {
        TODO("not implemented")
    }
}

class NatureRNG private constructor(
    var average: Float,
    private var variance: Float,
    private val distribution: Distribution
): Random() {
    private var offset = 0f

    fun copyWithOffset(offset: Float): NatureRNG {
        val copy = NatureRNG(offset, offset, distribution)
        copy.offset += offset
        return copy
    }

    fun addOffset(offset: Float): NatureRNG {
        this.offset += offset
        return this
    }

    fun setOffset(offset: Float): NatureRNG {
        this.offset = offset
        return this
    }

    override fun nextFloat(): Float {
        return this.nextFloat(1.0f)
    }

    fun nextFloat(multiplier: Float): Float {
        var rnd: Float

        return when (distribution) {
            Distribution.DIRAC -> {
                rnd = super.nextFloat() - 0.5f
                val value = offset + multiplier * (average + rnd * 2 * variance)

                average = 0.0f
                variance = 0.0f
                value
            }
            Distribution.UNIFORM -> {
                rnd = super.nextFloat() - 0.5f

                offset + multiplier * (average + rnd * 2 * variance)
            }
            Distribution.GAUSSIAN -> {
                rnd = (super.nextFloat() + super.nextFloat() + super.nextFloat()) / 3

                rnd -= 0.5f

                offset + multiplier * (average + rnd * 2 * variance)
            }
            Distribution.INVERSE_GAUSSIAN -> {
                rnd = (super.nextFloat() + super.nextFloat() + super.nextFloat()) / 3

                if (rnd > 0.5f) {
                    rnd -= 0.5f
                } else {
                    rnd += 0.5f
                }

                rnd -= 0.5f

                offset + multiplier * (average + rnd * variance)
            }
            Distribution.TRIANGLE -> {
                rnd = (super.nextFloat() + super.nextFloat()) / 2

                rnd -= 0.5f

                offset + multiplier * (average + rnd * 2 * variance)
            }
            Distribution.POISSON -> 0.0f
        }
    }

    companion object {
        const val HALF_PI = PI / 2

        fun uniform(average: Float, variance: Float): NatureRNG {
            return NatureRNG(average, variance, Distribution.UNIFORM)
        }

        fun gaussian(average: Float, variance: Float): NatureRNG {
            return NatureRNG(average, variance, Distribution.GAUSSIAN)
        }

        fun inverseGaussian(average: Float, variance: Float): NatureRNG {
            return NatureRNG(average, variance, Distribution.INVERSE_GAUSSIAN)
        }

        fun triangle(average: Float, variance: Float): NatureRNG {
            return NatureRNG(average, variance, Distribution.TRIANGLE)
        }

        fun poisson(average: Float, variance: Float): NatureRNG {
            return NatureRNG(average, variance, Distribution.POISSON)
        }

        fun dirac(average: Float, variance: Float): NatureRNG {
            return NatureRNG(average, variance, Distribution.DIRAC)
        }
    }

    enum class Distribution {
        UNIFORM,
        GAUSSIAN,
        INVERSE_GAUSSIAN,
        TRIANGLE,
        POISSON,
        DIRAC,
        ;
    }
}