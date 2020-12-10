package thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut

import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.registry.HEntities

class KnightlyJuggernautEntity(type: EntityType<KnightlyJuggernautEntity>, worldIn: World) : AnimalEntity(type, worldIn) {
    override fun func_241840_a(worldIn: ServerWorld, ageable: AgeableEntity): KnightlyJuggernautEntity {
        return HEntities.KNIGHTLY_JUGGERNAUT.create(world)!!
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 80.0)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.5)
            .createMutableAttribute(Attributes.MOVEMENT_SPEED)
            .createMutableAttribute(Attributes.ARMOR)
            .createMutableAttribute(Attributes.ARMOR_TOUGHNESS)
            .createMutableAttribute(ForgeMod.SWIM_SPEED.get())
            .createMutableAttribute(ForgeMod.NAMETAG_DISTANCE.get())
            .createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get())
            .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0)
            .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
    }
}