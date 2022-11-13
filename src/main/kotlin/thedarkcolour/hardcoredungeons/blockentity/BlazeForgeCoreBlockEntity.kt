package thedarkcolour.hardcoredungeons.blockentity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

// 3x3 multi block constructed with nether brick and obsidian
// fast smelter and used to craft castleton stone bricks for the portal frame
// outer blocks should be tile entities that expose the inner core for a capability
// the inner block should be a ticker tile entity
class BlazeForgeCoreBlockEntity(pPos: BlockPos, pBlockState: BlockState) : BlockEntity(TODO(), pPos, pBlockState) {
}