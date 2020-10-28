package thedarkcolour.hardcoredungeons.entity

import com.google.common.collect.ImmutableSet
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntitySize
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.network.FMLPlayMessages
import java.util.function.BiFunction
import java.util.function.Predicate
import java.util.function.ToIntFunction

/**
 * Special EntityType
 * 
 * @author TheDarkColour
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HEntityType<T>(
    factory: IFactory<T>,
    classification: EntityClassification,
    id: ResourceLocation,
    serializable: Boolean = false,
    canSummon: Boolean = true,
    immuneToFire: Boolean = false,
    spawnNaturally: Boolean = true,
    specialSpawns: ImmutableSet<Block> = ImmutableSet.of(),
    size: EntitySize = EntitySize.flexible(0.6F, 1.8F),
    velocityUpdateSpecial: Predicate<EntityType<*>> = DEFAULT_VELOCITY,
    trackingRangeSpecial: ToIntFunction<EntityType<*>> = ToIntFunction { 5 },
    updateIntervalSpecial: ToIntFunction<EntityType<*>> = ToIntFunction { 3 },
    clientFactorySpecial: BiFunction<FMLPlayMessages.SpawnEntity, World, T>? = null,
) : EntityType<T>(
    factory,
    classification,
    serializable,
    canSummon,
    immuneToFire,
    !spawnNaturally,
    specialSpawns,
    size,
    0, // unused
    0, // unused
    velocityUpdateSpecial,
    trackingRangeSpecial,
    updateIntervalSpecial,
    clientFactorySpecial
) where T : HEntityType.HEntity, T : Entity {
    init {
        registryName = id
    }

    operator fun invoke(worldIn: World): T {
        return create(worldIn)!!
    }

    interface HEntity {
        fun getAttributes(): AttributeModifierMap.MutableAttribute
    }

    companion object {
        val DEFAULT_VELOCITY = Predicate<EntityType<*>> { type ->
            type != PLAYER && type != LLAMA_SPIT && type != WITHER && type != BAT && type != ITEM_FRAME && type != LEASH_KNOT && type != PAINTING && type != END_CRYSTAL && type != EVOKER_FANGS
        }
    }
}