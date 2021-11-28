package thedarkcolour.hardcoredungeons.entity

import com.google.common.collect.ImmutableSet
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraftforge.fmllegacy.network.FMLPlayMessages
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
    factory: EntityFactory<T>,
    classification: MobCategory,
    serializable: Boolean = false,
    canSummon: Boolean = true,
    immuneToFire: Boolean = false,
    spawnNaturally: Boolean = true,
    specialSpawns: ImmutableSet<Block> = ImmutableSet.of(),
    size: EntityDimensions = EntityDimensions.scalable(0.6F, 1.8F),
    velocityUpdateSpecial: Predicate<EntityType<*>> = DEFAULT_VELOCITY,
    trackingRangeSpecial: ToIntFunction<EntityType<*>> = ToIntFunction { 5 },
    updateIntervalSpecial: ToIntFunction<EntityType<*>> = ToIntFunction { 3 },
    clientFactorySpecial: BiFunction<FMLPlayMessages.SpawnEntity, Level, T>? = null,
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
    interface HEntity {
        fun getAttributes(): AttributeSupplier.Builder
    }

    companion object {
        val DEFAULT_VELOCITY = Predicate<EntityType<*>> { type ->
            type != PLAYER && type != LLAMA_SPIT && type != WITHER && type != BAT && type != ITEM_FRAME && type != LEASH_KNOT && type != PAINTING && type != END_CRYSTAL && type != EVOKER_FANGS
        }
    }
}