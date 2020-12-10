package thedarkcolour.hardcoredungeons.entity.rainbowland.sheep

import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.passive.SheepEntity
import net.minecraft.world.World
import net.minecraftforge.common.ForgeMod

class RainbowlandSheepEntity(type: EntityType<out SheepEntity>, worldIn: World) : SheepEntity(type, worldIn) {
    companion object {
        val ATTRIBUTES: AttributeModifierMap.MutableAttribute = AttributeModifierMap.createMutableAttribute()
            .createMutableAttribute(Attributes.MAX_HEALTH, 16.0)
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