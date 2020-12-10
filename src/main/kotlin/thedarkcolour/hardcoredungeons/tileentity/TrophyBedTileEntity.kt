package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType

class TrophyBedTileEntity(type: TileEntityType<*> = TODO()/*HTileEntities.TROPHY_BED*/) : TileEntity(type) {
    private lateinit var pattern: Pattern

    constructor(pattern: Pattern) : this() {

    }

    override fun getUpdatePacket(): SUpdateTileEntityPacket? {
        return SUpdateTileEntityPacket(pos, -1, updateTag)
    }

    override fun onDataPacket(net: NetworkManager?, pkt: SUpdateTileEntityPacket) {
        read(world!!.getBlockState(pos), pkt.nbtCompound)
    }

    enum class Pattern {

    }
}