package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.FlowerPotBlock
import java.util.function.Supplier

fun interface CustomFlowerPot {
    fun makeFlowerPot(plant: Supplier<Block>): FlowerPotBlock
}