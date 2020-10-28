package thedarkcolour.hardcoredungeons.entity.misc.cheeky

import net.minecraft.command.arguments.EntityAnchorArgument
import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.world.World
import net.minecraftforge.common.ForgeMod
import thedarkcolour.hardcoredungeons.entity.HEntityType

@Suppress("SpellCheckingInspection")
class CheekyEntity(type: EntityType<out CreatureEntity>, worldIn: World) : CreatureEntity(type, worldIn), HEntityType.HEntity {
    var isSpeaking = false
    var speech = "aaaaaaaaaaaaa"

    override fun getAttributes(): AttributeModifierMap.MutableAttribute {
        return ATTRIBUTES
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    override fun livingTick() {
        super.livingTick()

        val target = world.getClosestPlayer(this, 100.0) ?: return

        lookAt(EntityAnchorArgument.Type.EYES, target.positionVec)
    }

    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 10.0)
            .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.2)
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