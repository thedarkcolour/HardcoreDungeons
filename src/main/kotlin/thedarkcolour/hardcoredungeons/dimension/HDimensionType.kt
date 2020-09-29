package thedarkcolour.hardcoredungeons.dimension

/*
import net.minecraft.world.World
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.common.DimensionManager
import java.util.function.BiFunction

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HDimensionType(private val supplier: (World, DimensionType) -> Dimension) : net.minecraftforge.common.ModDimension() {
    var type: DimensionType? = null

    override fun getFactory(): BiFunction<World, DimensionType, Dimension> {
        return BiFunction(supplier::invoke)
    }

    fun register() {
        type = if (DimensionType.byName(registryName) == null) {
            DimensionManager.registerDimension(registryName, this, null, true)!!
        } else {
            DimensionType.byName(registryName)!!
        }
    }
}*/