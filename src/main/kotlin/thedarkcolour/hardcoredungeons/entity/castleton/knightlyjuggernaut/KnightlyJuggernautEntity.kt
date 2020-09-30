package thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut

import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import thedarkcolour.hardcoredungeons.registry.HEntities

class KnightlyJuggernautEntity(type: EntityType<KnightlyJuggernautEntity>, worldIn: World) : AnimalEntity(type, worldIn) {
    override fun func_241840_a(worldIn: ServerWorld, ageable: AgeableEntity): KnightlyJuggernautEntity {
        return HEntities.KNIGHTLY_JUGGERNAUT.create(world)!!
    }
}