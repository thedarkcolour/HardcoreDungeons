package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.tileentity.CampfireTileEntity
import thedarkcolour.hardcoredungeons.registry.HTileEntities

class LumlightCampfireTileEntity : CampfireTileEntity() {
    init {
        // override the type passed into super by CampfireTileEntity
        type = HTileEntities.LUMLIGHT_CAMPFIRE
    }
}