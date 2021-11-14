package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.util.Constants
import thedarkcolour.hardcoredungeons.registry.HBlockEntities
import thedarkcolour.hardcoredungeons.registry.HBlocks

class SootTrapControllerTileEntity(pos: BlockPos, state: BlockState) : BlockEntity(HBlockEntities.SOOT_TRAP_CONTROLLER, pos, state) {
    val paths = arrayListOf<BlockPos>()

    fun activate() {
        paths.forEachIndexed { i, pos ->
            level!!.blockTicks.scheduleTick(pos, HBlocks.SOOT, 30 + (i * 5))
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        paths.clear()

        if (tag.contains("Paths", Constants.NBT.TAG_LIST)) {
            for (posNbt in tag.getList("Paths", Constants.NBT.TAG_COMPOUND)) {
                paths.add(NbtUtils.readBlockPos(posNbt as CompoundTag))
            }
        }
    }

    override fun save(nbt: CompoundTag): CompoundTag {
        val listNBT = ListTag()

        for (pos in paths) {
            listNBT.add(NbtUtils.writeBlockPos(pos))
        }

        nbt.put("Paths", listNBT)

        return super.save(nbt)
    }
}