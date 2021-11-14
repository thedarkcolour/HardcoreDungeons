package thedarkcolour.hardcoredungeons.client.color

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockDisplayReader
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.api.distmarker.OnlyIns

// did it anyway 😔
@OnlyIns(
    OnlyIn(value = Dist.CLIENT, _interface = IBlockColor::class),
    OnlyIn(value = Dist.CLIENT, _interface = IItemColor::class)
)
interface HColor : IBlockColor, IItemColor {
    override fun getColor(state: BlockState?, worldIn: IBlockDisplayReader?, pos: BlockPos?, tintIndex: Int): Int

    override fun getColor(stack: ItemStack, tintIndex: Int): Int
}