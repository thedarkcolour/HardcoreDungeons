package thedarkcolour.hardcoredungeons.client.color

import net.minecraft.block.BlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ILightReader

interface HColor : IBlockColor, IItemColor {
    override fun getColor(state: BlockState?, worldIn: ILightReader?, pos: BlockPos?, tintIndex: Int): Int

    override fun getColor(stack: ItemStack, tintIndex: Int): Int
}