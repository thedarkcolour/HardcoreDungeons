package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.Block

class TeleporterRuneBlock(properties: Properties) : Block(properties) {
    enum class Variant {
        BLANK, ROOK, COIN
    }
}