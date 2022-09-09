package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HTileEntities

class SootTrapControllerTileEntity : BlockEntity(HTileEntities.SOOT_TRAP_CONTROLLER) {
    val paths = arrayListOf<BlockPos>()

    fun activate() {
        paths.forEachIndexed { i, pos ->
            level!!.scheduleTick(pos, HBlocks.SOOT, 30 + (i * 5))
        }
    }

    override fun load(state: BlockState, tag: CompoundNBT) {
        super.load(state, tag)

        paths.clear()

        if (tag.contains("Paths", Constants.NBT.TAG_LIST)) {
            for (posNbt in tag.getList("Paths", Constants.NBT.TAG_COMPOUND)) {
                paths.add(NBTUtil.readBlockPos(posNbt as CompoundNBT))
            }
        }
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        val listNBT = ListNBT()

        for (pos in paths) {
            listNBT.add(NBTUtil.writeBlockPos(pos))
        }

        nbt.put("Paths", listNBT)

        return super.save(nbt)
    }
}