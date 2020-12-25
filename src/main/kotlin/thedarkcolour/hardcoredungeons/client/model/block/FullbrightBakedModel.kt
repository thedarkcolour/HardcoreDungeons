package thedarkcolour.hardcoredungeons.client.model.block

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.renderer.BlockModelShapes
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.client.renderer.model.ItemOverrideList
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.Direction
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.data.EmptyModelData
import java.util.*

// thanks to commoble and raoulvdberge
class FullbrightBakedModel(private val base: IBakedModel, private val fullbrightSprites: Set<ResourceLocation>) : IBakedModel {
    @Suppress("DEPRECATION")
    override fun getQuads(state: BlockState?, side: Direction?, rand: Random): List<BakedQuad> {
        return QUAD_CACHE.getUnchecked(CacheKey(state ?: return base.getQuads(state, side, rand), side, base, fullbrightSprites, rand))
    }

    override fun isBuiltInRenderer(): Boolean {
        return base.isBuiltInRenderer
    }

    override fun isAmbientOcclusion(): Boolean {
        return base.isAmbientOcclusion
    }

    override fun isGui3d(): Boolean {
        return base.isGui3d
    }

    override fun isSideLit(): Boolean {
        return base.isSideLit
    }

    override fun getOverrides(): ItemOverrideList {
        return base.overrides
    }

    @Suppress("DEPRECATION")
    override fun getParticleTexture(): TextureAtlasSprite {
        return base.particleTexture
    }

    companion object {
        private val QUAD_CACHE = CacheBuilder.newBuilder().build(object : CacheLoader<CacheKey, List<BakedQuad>>() {
            override fun load(key: CacheKey): List<BakedQuad> {
                return transformQuads(key.model.getQuads(key.state, key.side, key.rand, EmptyModelData.INSTANCE), key.textures)
            }
        })

        private val FULL_LIGHT = LightTexture.packLight(15, 15)

        private fun transformQuads(oldQuads: List<BakedQuad>, textures: Set<ResourceLocation>): List<BakedQuad> {
            val newQuads = ArrayList(oldQuads)

            for (i in newQuads.indices) {
                val quad = newQuads[i]

                if (textures.contains(quad.sprite.name)) {
                    newQuads[i] = transformQuad(quad)
                }
            }

            return newQuads
        }

        private fun transformQuad(quad: BakedQuad): BakedQuad {
            val vertexData = quad.vertexData.clone()

            vertexData[6] = FULL_LIGHT
            vertexData[6 + 8] = FULL_LIGHT
            vertexData[6 + 8 + 8] = FULL_LIGHT
            vertexData[6 + 8 + 8 + 8] = FULL_LIGHT

            return BakedQuad(vertexData, quad.tintIndex, quad.face, quad.sprite, quad.applyDiffuseLighting())
        }

        /**
         * Adds fullbright effects to every possible state of the block.
         */
        fun addFullBrightEffects(registry: MutableMap<ResourceLocation, IBakedModel>, block: Block, fullbrightLayers: Set<ResourceLocation>) {
            for (state in block.stateContainer.validStates) {
                addFullBrightEffects(registry, state, fullbrightLayers)
            }
        }

        /**
         * Adds fullbright effects to the state's model.
         *
         * @param state variant to add fullbright effects to
         */
        private fun addFullBrightEffects(registry: MutableMap<ResourceLocation, IBakedModel>, state: BlockState, fullbrightLayers: Set<ResourceLocation>) {
            val key = BlockModelShapes.getModelLocation(state)

            if (registry.containsKey(key)) {
                registry[key] = FullbrightBakedModel(registry[key]!!, fullbrightLayers)
            }
        }
    }

    private class CacheKey(
        val state: BlockState,
        val side: Direction?,
        val model: IBakedModel,
        val textures: Set<ResourceLocation>,
        val rand: Random,
    ) {
        override fun equals(other: Any?): Boolean {
            return when {
                this === other -> true
                other !is CacheKey -> false
                side == other.side && state == other.state -> true
                else -> false
            }
        }

        override fun hashCode(): Int {
            return state.hashCode() + 113 * (side?.hashCode() ?: 0)
        }
    }
}