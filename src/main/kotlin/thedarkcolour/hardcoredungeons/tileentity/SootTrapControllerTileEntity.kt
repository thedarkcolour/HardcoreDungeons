package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.ListNBT
import net.minecraft.nbt.NBTUtil
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.util.Constants
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HTileEntities

class SootTrapControllerTileEntity : TileEntity(HTileEntities.SOOT_TRAP_CONTROLLER) {
    val paths = arrayListOf<BlockPos>()

    fun activate() {
        paths.forEachIndexed { i, pos ->
            level!!.blockTicks.scheduleTick(pos, HBlocks.SOOT, 30 + (i * 5))
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