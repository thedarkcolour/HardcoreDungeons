package thedarkcolour.hardcoredungeons.feature.tree

import com.google.common.collect.Lists
import com.mojang.serialization.Dynamic
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.MutableBoundingBox
import net.minecraft.world.gen.IWorldGenerationReader
import java.util.*
import kotlin.math.*

class TallTreeFeature(deserializer: (Dynamic<*>) -> TreeFeatureConfig) : FancyTreeFeature(deserializer) {
    private val rng = NatureRNG.gaussian(2.0f, 6.0f)

    private fun makeLeafLayer(
        worldIn: IWorldGenerationReader, rand: Random, pos: BlockPos, size: Float,
        changedBlocks: Set<BlockPos>, bounds: MutableBoundingBox, cfg: TreeFeatureConfig
    ) {
        val i = (size.toDouble() + 0.618).toInt()
        for (j in -i..i) {
            for (k in -i..i) {
                if ((abs(j).toDouble() + 0.5).pow(2.0) + (abs(k).toDouble() + 0.5).pow(2.0) <= (size * size)) {
                    setLeaf(worldIn, rng, pos.add(j, 0, k), changedBlocks, bounds, cfg)
                }
            }
        }
    }

    private fun getBaseBranchSize(height: Int, y: Int): Float {
        return if (y < height * 0.3f) {
            -1.0f
        } else {
            val f = height.toFloat() / 2.0f
            val f1 = f - y.toFloat()
            var f2 = sqrt(f * f - f1 * f1)
            if (f1 == 0.0f) {
                f2 = f
            } else if (abs(f1) >= f) {
                return 0.0f
            }
            f2 * 0.5f
        }
    }

    private fun getLeafRadiusForLayer(i: Int): Float {
        return if (i != 0 && i != 4) 3.0f else 2.0f
    }

    private fun makeLeaves(
        worldIn: IWorldGenerationReader, rand: Random, pos: BlockPos,
        changedBlocks: Set<BlockPos>, bounds: MutableBoundingBox, cfg: TreeFeatureConfig
    ) {
        for (i in 0..4) {
            makeLeafLayer(worldIn, rand, pos.up(i), getLeafRadiusForLayer(i), changedBlocks, bounds, cfg)
        }
    }

    private fun makeOrCheckBranch(
        worldIn: IWorldGenerationReader,
        p_227235_3_: BlockPos,
        p_227235_4_: BlockPos,
        p_227235_5_: Boolean,
        p_227235_6_: MutableSet<BlockPos>,
        p_227235_7_: MutableBoundingBox,
        p_227235_8_: TreeFeatureConfig
    ): Int {
        return if (!p_227235_5_ && p_227235_3_ == p_227235_4_) {
            -1
        } else {
            val blockpos = p_227235_4_.add(-p_227235_3_.x, -p_227235_3_.y, -p_227235_3_.z)
            val i = getLongestSide(blockpos)
            val f = blockpos.x.toFloat() / i.toFloat()
            val f1 = blockpos.y.toFloat() / i.toFloat()
            val f2 = blockpos.z.toFloat() / i.toFloat()
            for (j in 0..i) {
                val blockpos1 = p_227235_3_.add(
                    (0.5f + j.toFloat() * f).toDouble(),
                    (0.5f + j.toFloat() * f1).toDouble(),
                    (0.5f + j.toFloat() * f2).toDouble()
                )
                if (p_227235_5_) {
                    this.setBlockState(
                        worldIn,
                        blockpos1,
                        p_227235_8_.trunkProvider.getBlockState(rng, blockpos1)
                            .with(
                                LogBlock.AXIS,
                                getLogAxis(p_227235_3_, blockpos1)
                            ),
                        p_227235_7_
                    )
                    p_227235_6_.add(blockpos1)
                } else if (!AbstractTreeFeature.canBeReplacedByLogs(worldIn, blockpos1)) {
                    return j
                }
            }
            -1
        }
    }

    private fun getLongestSide(p_227237_1_: BlockPos): Int {
        val i = MathHelper.abs(p_227237_1_.x)
        val j = MathHelper.abs(p_227237_1_.y)
        val k = MathHelper.abs(p_227237_1_.z)
        return if (k > i && k > j) {
            k
        } else {
            if (j > i) j else i
        }
    }

    private fun getLogAxis(p_227238_1_: BlockPos, p_227238_2_: BlockPos): Direction.Axis {
        var `direction$axis` = Direction.Axis.Y
        val i = Math.abs(p_227238_2_.x - p_227238_1_.x)
        val j = Math.abs(p_227238_2_.z - p_227238_1_.z)
        val k = Math.max(i, j)
        if (k > 0) {
            if (i == k) {
                `direction$axis` = Direction.Axis.X
            } else if (j == k) {
                `direction$axis` = Direction.Axis.Z
            }
        }
        return `direction$axis`
    }

    private fun makeLeaves(
        worldIn: IWorldGenerationReader,
        rand: Random,
        p_227232_3_: Int,
        pos: BlockPos,
        leafPositions: List<ExtendedPos>,
        p_227232_6_: Set<BlockPos>,
        bounds: MutableBoundingBox,
        p_227232_8_: TreeFeatureConfig
    ) {
        for (extendedPos in leafPositions) {
            if (isHighEnough(p_227232_3_, extendedPos.endY - pos.y)) {
                makeLeaves(worldIn, rng, extendedPos, p_227232_6_, bounds, p_227232_8_)
            }
        }
    }

    private fun isHighEnough(p_227239_1_: Int, p_227239_2_: Int): Boolean {
        return p_227239_2_.toDouble() >= p_227239_1_.toDouble() * 0.2
    }

    private fun makeTrunk(
        p_227234_1_: IWorldGenerationReader,
        p_227234_2_: Random,
        p_227234_3_: BlockPos,
        p_227234_4_: Int,
        p_227234_5_: MutableSet<BlockPos>,
        p_227234_6_: MutableBoundingBox,
        p_227234_7_: TreeFeatureConfig
    ) {
        makeOrCheckBranch(
            p_227234_1_,
            p_227234_3_,
            p_227234_3_.up(p_227234_4_),
            true,
            p_227234_5_,
            p_227234_6_,
            p_227234_7_
        )
    }

    private fun makeBranches(
        p_227240_1_: IWorldGenerationReader,
        p_227240_2_: Random,
        p_227240_3_: Int,
        p_227240_4_: BlockPos,
        p_227240_5_: List<ExtendedPos>,
        p_227240_6_: MutableSet<BlockPos>,
        p_227240_7_: MutableBoundingBox,
        p_227240_8_: TreeFeatureConfig
    ) {
        for (`fancytreefeature$extendedpos` in p_227240_5_) {
            val i = `fancytreefeature$extendedpos`.endY
            val blockpos = BlockPos(p_227240_4_.x, i, p_227240_4_.z)
            if (blockpos != `fancytreefeature$extendedpos` && isHighEnough(
                    p_227240_3_,
                    i - p_227240_4_.y
                )
            ) {
                makeOrCheckBranch(
                    p_227240_1_,
                    blockpos,
                    `fancytreefeature$extendedpos`,
                    true,
                    p_227240_6_,
                    p_227240_7_,
                    p_227240_8_
                )
            }
        }
    }

    override fun place(
        worldIn: IWorldGenerationReader,
        rand: Random,
        pos: BlockPos,
        changedPos: MutableSet<BlockPos>,
        p_225557_5_: Set<BlockPos>,
        p_225557_6_: MutableBoundingBox,
        p_225557_7_: TreeFeatureConfig
    ): Boolean {
        val random = NatureRNG.triangle(rand.nextFloat(), rand.nextDouble().toFloat())
        val i = getTreeHeight(
            worldIn,
            pos,
            5 + random.nextInt(12),
            changedPos,
            p_225557_6_,
            p_225557_7_
        )
        return if (i == -1) {
            false
        } else {
            setDirt(worldIn, pos.down())
            var j = (i.toDouble() * 0.618).toInt()
            if (j >= i) {
                j = i - 1
            }
            val d0 = 1.0
            var k = (1.382 + Math.pow(1.0 * i.toDouble() / 13.0, 2.0)).toInt()
            if (k < 1) {
                k = 1
            }
            val l = pos.y + j
            var i1 = i - 5
            val leafPositions: MutableList<ExtendedPos> = Lists.newArrayList()
            leafPositions.add(ExtendedPos(pos.up(i1), l))
            while (i1 >= 0) {
                val f = getBaseBranchSize(i, i1)
                if (f >= 0.0f) {
                    for (j1 in 0 until k) {
                        val d1 = 1.0
                        val d2 = 1.0 * f.toDouble() * (random.nextFloat().toDouble() + 0.328)
                        val d3 = (random.nextFloat() * 2.0f).toDouble() * Math.PI
                        val d4 = d2 * sin(d3) + 0.5
                        val d5 = d2 * cos(d3) + 0.5
                        val blockpos = pos.add(d4, (i1 - 1).toDouble(), d5)
                        val blockpos1 = blockpos.up(5)
                        if (makeOrCheckBranch(
                                worldIn,
                                blockpos,
                                blockpos1,
                                false,
                                changedPos,
                                p_225557_6_,
                                p_225557_7_
                            ) == -1
                        ) {
                            val k1 = pos.x - blockpos.x
                            val l1 = pos.z - blockpos.z
                            val d6 =
                                blockpos.y.toDouble() - Math.sqrt((k1 * k1 + l1 * l1).toDouble()) * 0.381
                            val i2 = if (d6 > l.toDouble()) l else d6.toInt()
                            val blockpos2 = BlockPos(pos.x, i2, pos.z)
                            if (makeOrCheckBranch(
                                    worldIn,
                                    blockpos2,
                                    blockpos,
                                    false,
                                    changedPos,
                                    p_225557_6_,
                                    p_225557_7_
                                ) == -1
                            ) {
                                leafPositions.add(ExtendedPos(blockpos, blockpos2.y))
                            }
                        }
                    }
                }
                --i1
            }
            makeLeaves(worldIn, rand, i, pos, leafPositions, p_225557_5_, p_225557_6_, p_225557_7_)
            makeTrunk(worldIn, rand, pos, j, changedPos, p_225557_6_, p_225557_7_)
            makeBranches(worldIn, rand, i, pos, leafPositions, changedPos, p_225557_6_, p_225557_7_)
            true
        }
    }

    private fun getTreeHeight(
        p_227241_1_: IWorldGenerationReader,
        p_227241_3_: BlockPos,
        p_227241_4_: Int,
        p_227241_5_: MutableSet<BlockPos>,
        p_227241_6_: MutableBoundingBox,
        p_227241_7_: TreeFeatureConfig
    ): Int {
        return if (!AbstractTreeFeature.isDirtOrGrassBlockOrFarmland(p_227241_1_, p_227241_3_.down())) {
            -1
        } else {
            val i = makeOrCheckBranch(
                p_227241_1_,
                p_227241_3_,
                p_227241_3_.up(p_227241_4_ - 1),
                false,
                p_227241_5_,
                p_227241_6_,
                p_227241_7_
            )
            if (i == -1) {
                p_227241_4_
            } else {
                if (i < 6) -1 else i
            }
        }
    }

    internal class ExtendedPos(pos: BlockPos, val endY: Int) : BlockPos(pos)
}