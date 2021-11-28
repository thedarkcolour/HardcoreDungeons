package thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HEntities

class KnightlyJuggernautEntity(type: EntityType<KnightlyJuggernautEntity>, level: Level) : Animal(type, level) {
    override fun getBreedOffspring(worldIn: ServerLevel, ageable: AgeableMob): KnightlyJuggernautEntity {
        return HEntities.KNIGHTLY_JUGGERNAUT.create(level)!!
    }

    companion object {
        val ATTRIBUTES: AttributeSupplier.Builder = AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH, 80.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
            .add(Attributes.MOVEMENT_SPEED)
            .add(Attributes.ARMOR)
            .add(Attributes.ARMOR_TOUGHNESS)
            .add(ForgeMod.SWIM_SPEED.get())
            .add(ForgeMod.NAMETAG_DISTANCE.get())
            .add(ForgeMod.ENTITY_GRAVITY.get())
            .add(Attributes.FOLLOW_RANGE, 16.0)
            .add(Attributes.ATTACK_KNOCKBACK)
    }
}