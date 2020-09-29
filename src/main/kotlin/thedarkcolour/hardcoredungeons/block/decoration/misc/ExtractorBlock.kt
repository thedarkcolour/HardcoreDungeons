package thedarkcolour.hardcoredungeons.block.decoration.misc

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HContainers
import thedarkcolour.hardcoredungeons.tileentity.ExtractorTileEntity

/**
 * @author TheDarkColour
 */
class ExtractorBlock(properties: HProperties) : HorizontalBlock(properties) {
    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }

    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity? {
        return ExtractorTileEntity()
    }

    override fun onBlockActivated(
        state: BlockState, worldIn: World, pos: BlockPos, playerIn: PlayerEntity,
        handIn: Hand, result: BlockRayTraceResult
    ): ActionResultType {
        return if (worldIn.isRemote()) {
            ActionResultType.SUCCESS
        } else {
            return HContainers.EXTRACTOR.openContainer(playerIn)
            // playerIn.openContainer(SimpleNamedContainerProvider(
            //     IContainerProvider { id, playerInv, _ ->
            //         ExtractorContainer(id, playerInv, worldIn.getTileEntity(pos) as ExtractorTileEntity)
            //     }, NAME
            // ))
            // ActionResultType.SUCCESS
        }
    }

    companion object {
        val NAME = TranslationTextComponent("hardcoredungeons.container.extractor")
    }
}