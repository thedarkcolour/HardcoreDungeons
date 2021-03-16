package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.WoodType

class HWoodType(nameIn: String) : WoodType(nameIn) {
    companion object {
        val LUMLIGHT = HWoodType("lumlight")
    }
}