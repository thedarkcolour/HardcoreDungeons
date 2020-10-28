package thedarkcolour.hardcoredungeons.tileentity

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity
import thedarkcolour.hardcoredungeons.container.Extractor
import thedarkcolour.hardcoredungeons.registry.HRecipes
import thedarkcolour.hardcoredungeons.util.DarkInventory
import thedarkcolour.hardcoredungeons.util.IDarkInventory

/**
 * @author TheDarkColour, genericrandom64
 */
class ExtractorTileEntity : TileEntity(TODO("Add the tile entity type here")), ITickableTileEntity, Extractor, IDarkInventory.Tile {
    private var burnTime = 0
    private var progress = 0

    override val inventory = object : DarkInventory(3) {
        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            if (slot == 1) {
                return isValidFuel(world!!, stack)
            }
            return super.isItemValid(slot, stack)
        }
    }

    override fun canInteractWith(playerIn: PlayerEntity): Boolean {
        return isTileWithinDistance(this, playerIn)
    }

    override fun tick() {
        if (!inventory[0].isEmpty && !inventory[1].isEmpty) {
            // poll for recipe
            if (isValidInput(world!!, inventory[0])) {
                // check if burning fuel
                if (isBurning()) {
                    // decrement burn time
                    --burnTime

                    ++progress
                    if (progress == 300) {
                        // we need to do the thing here
                        // ctrl click on extract
                        extract()
                    }
                } else {
                    val stack = inventory[1]

                    if (isValidFuel(world!!, stack)) {
                        burnTime = stack.burnTime
                        stack.shrink(1)
                    }
                }
            }
        }
    }

    fun fuelAnimation() {

    }

    //

    //
    private fun extract() {
        // since we already check if this is not null
        val recipe = world!!.recipeManager.getRecipes(HRecipes.EXTRACTION, inventory.getVanillaInventory(), world!!).first()
        // we can just call recipeOutput and store that
        val output = recipe.recipeOutput
        // assign = =
        inventory[2] = output

        // i lern
        // eae
        inventory[0].shrink(1)
    }

    fun isBurning(): Boolean {
        // good
        return burnTime != 0
    }
}