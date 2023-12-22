package thedarkcolour.hardcoredungeons.client.model.block

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.block.BlockModelShaper
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

// thanks to commoble and raoulvdberge
class FullbrightBakedModel(private val base: BakedModel, private val fullbrightSprites: Set<ResourceLocation>) : BakedModel {
    @Deprecated("Deprecated in Java")
    override fun getQuads(state: BlockState?, side: Direction?, rand: RandomSource): List<BakedQuad> {
        return QUAD_CACHE.getUnchecked(CacheKey(state ?: return base.getQuads(null, side, rand), side, base, fullbrightSprites, rand))
    }

    override fun isCustomRenderer(): Boolean {
        return base.isCustomRenderer
    }

    override fun useAmbientOcclusion(): Boolean {
        return base.useAmbientOcclusion()
    }

    override fun isGui3d(): Boolean {
        return base.isGui3d
    }

    override fun usesBlockLight(): Boolean {
        return base.usesBlockLight()
    }

    override fun getOverrides(): ItemOverrides {
        return base.overrides
    }

    @Suppress("DEPRECATION")
    override fun getParticleIcon(): TextureAtlasSprite {
        return base.particleIcon
    }

    companion object {
        private val QUAD_CACHE = CacheBuilder.newBuilder().build(object : CacheLoader<CacheKey, List<BakedQuad>>() {
            override fun load(key: CacheKey): List<BakedQuad> {
                return transformQuads(key.model.getQuads(key.state, key.side, key.rand), key.textures)
            }
        })

        private val FULL_LIGHT = LightTexture.pack(15, 15)

        private fun transformQuads(oldQuads: List<BakedQuad>, textures: Set<ResourceLocation>): List<BakedQuad> {
            val newQuads = ArrayList(oldQuads)

            for (i in newQuads.indices) {
                val quad = newQuads[i]

                if (textures.contains(quad.sprite.atlasLocation())) {
                    newQuads[i] = transformQuad(quad)
                }
            }

            return newQuads
        }

        private fun transformQuad(quad: BakedQuad): BakedQuad {
            val vertexData = quad.vertices.clone()

            vertexData[6] = FULL_LIGHT
            vertexData[6 + 8] = FULL_LIGHT
            vertexData[6 + 8 + 8] = FULL_LIGHT
            vertexData[6 + 8 + 8 + 8] = FULL_LIGHT

            return BakedQuad(vertexData, quad.tintIndex, quad.direction, quad.sprite, quad.isShade)
        }

        /**
         * Adds fullbright effects to every possible state of the block.
         */
        fun addFullBrightEffects(registry: MutableMap<ResourceLocation, BakedModel>, block: Block, fullbrightLayers: Set<ResourceLocation>) {
            for (state in block.stateDefinition.possibleStates) {
                addFullBrightEffects(registry, state, fullbrightLayers)
            }
        }

        /**
         * Adds fullbright effects to the state's model.
         *
         * @param state variant to add fullbright effects to
         */
        private fun addFullBrightEffects(registry: MutableMap<ResourceLocation, BakedModel>, state: BlockState, fullbrightLayers: Set<ResourceLocation>) {
            val key = BlockModelShaper.stateToModelLocation(state)

            if (registry.containsKey(key)) {
                registry[key] = FullbrightBakedModel(registry[key]!!, fullbrightLayers)
            }
        }
    }

    private class CacheKey(
        val state: BlockState,
        val side: Direction?,
        val model: BakedModel,
        val textures: Set<ResourceLocation>,
        val rand: RandomSource,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as CacheKey

            if (state != other.state) return false
            if (side != other.side) return false

            return true
        }

        override fun hashCode(): Int {
            var result = state.hashCode()
            result = 31 * result + (side?.hashCode() ?: 0)
            return result
        }
    }
}