package thedarkcolour.hardcoredungeons.client.color

import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.client.renderer.color.IBlockColor
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.world.item.ItemStack
import net.minecraft.core.BlockPos
import net.minecraft.world.IBlockDisplayReader
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.api.distmarker.OnlyIns

// did it anyway 😔
@OnlyIns(
    OnlyIn(value = Dist.CLIENT, _interface = BlockColor::class),
    OnlyIn(value = Dist.CLIENT, _interface = ItemColor::class)
)
interface HColor : BlockColor, ItemColor {
    override fun getColor(state: BlockState?, worldIn: BlockAndTintGetter?, pos: BlockPos?, tintIndex: Int): Int

    override fun getColor(stack: ItemStack, tintIndex: Int): Int
}