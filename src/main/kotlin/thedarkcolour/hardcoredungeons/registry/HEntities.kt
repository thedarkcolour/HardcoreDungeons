package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.client.event.RegisterEntitySpectatorShadersEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.network.PlayMessages
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.renderer.entity.*
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherRenderer
import thedarkcolour.hardcoredungeons.entity.projectile.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.legacy.ObjectHolderDelegate
import thedarkcolour.hardcoredungeons.util.modLoc
import java.util.function.BiFunction

@Suppress("MemberVisibilityCanBePrivate")
object HEntities : HRegistry<EntityType<*>>(ForgeRegistries.Keys.ENTITY_TYPES) {
    val FRAYED_SOUL by type("frayed_soul", ::FrayedSoulEntity, MobCategory.MONSTER, size = EntityDimensions.scalable(0.4f, 0.4f))
    //val VOID_RUNNER by type("void_runner", ::VoidRunnerEntity, MobCategory.MONSTER, size = EntityDimensions.scalable(0.5f, 0.8f))
    val CASTLETON_DEER by type("castleton_deer", ::CastletonDeerEntity, MobCategory.CREATURE, size = EntityDimensions.scalable(0.8f, 1.8f))
    val DEER by type("deer", ::DeerEntity, MobCategory.CREATURE)
    val KNIGHTLY_JUGGERNAUT by type("knightly_juggernaut", ::KnightlyJuggernautEntity, MobCategory.CREATURE, size = EntityDimensions.scalable(1.5f, 2.7f))
    //val MAZE_BOSS by type("maze_boss", ::MazeBossEntity, MobCategory.MONSTER)
    val MUSHROOM_ARCHER by type("mushroom_archer", ::MushroomArcherEntity, MobCategory.MONSTER)

    // projectile
    val SMALL_BULLET by type("small_bullet", ::SmallBulletEntity, MobCategory.MISC, size = EntityDimensions.scalable(0.2f, 0.2f), velocityUpdates = true)
    val MAGIC_BOLT by type("magic_bolt", ::MagicBoltEntity, MobCategory.MISC, size = EntityDimensions.scalable(0.2f, 0.2f), velocityUpdates = true)

    val BLUE_EYES_SHADER = modLoc("shaders/post/blue_eyes.json")

    fun registerEntityAttributes(event: EntityAttributeCreationEvent) {
        event.put(FRAYED_SOUL, FrayedSoulEntity.ATTRIBUTES.build())
        //event.put(VOID_RUNNER, VoidRunnerEntity.ATTRIBUTES.build())
        event.put(CASTLETON_DEER, DeerEntity.DEFAULT_ATTRIBUTES.build())
        event.put(DEER, DeerEntity.DEFAULT_ATTRIBUTES.build())
        event.put(KNIGHTLY_JUGGERNAUT, KnightlyJuggernautEntity.ATTRIBUTES.build())
        //event.put(RAINBOWLAND_SHEEP, RainbowlandSheepEntity.ATTRIBUTES.build())
        //event.put(MAZE_BOSS, MazeBossEntity.ATTRIBUTES.build())
        event.put(MUSHROOM_ARCHER, MushroomArcherEntity.ATTRIBUTES.build())
    }

    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        //event.registerEntityRenderer(MAZE_BOSS, ::MazeBossRenderer)
        event.registerEntityRenderer(MUSHROOM_ARCHER, ::MushroomArcherRenderer)
        event.registerEntityRenderer(DEER, ::DeerRenderer)
        event.registerEntityRenderer(FRAYED_SOUL, ::FrayedSoulRenderer)
        //event.registerEntityRenderer(VOID_RUNNER, ::VoidRunnerRenderer)
        event.registerEntityRenderer(CASTLETON_DEER, ::DeerRenderer)
        event.registerEntityRenderer(KNIGHTLY_JUGGERNAUT, ::KnightlyJuggernautRenderer)
        event.registerEntityRenderer(SMALL_BULLET) { ctx -> ThrownItemRenderer(ctx, 1.0f, true) }
        event.registerEntityRenderer(MAGIC_BOLT, ::MagicBoltRenderer)
    }

    fun registerEntityShaders(event: RegisterEntitySpectatorShadersEvent) {
        event.register(FRAYED_SOUL, BLUE_EYES_SHADER)
        //event.register(VOID_RUNNER, BLUE_EYES_SHADER)
        event.register(CASTLETON_DEER, BLUE_EYES_SHADER)
        event.register(KNIGHTLY_JUGGERNAUT, BLUE_EYES_SHADER)
    }

    fun <T : Entity> type(
        id: String,
        factory: EntityType.EntityFactory<T>,
        classification: MobCategory,
        fireImmune: Boolean = false,
        size: EntityDimensions? = null,
        velocityUpdates: Boolean = false,
        trackingRange: Int = 5,
        updateInterval: Int = 3,
        clientFactorySpecial: BiFunction<PlayMessages.SpawnEntity, Level, T>? = null
    ): ObjectHolderDelegate<EntityType<T>> {
        val builder = EntityType.Builder.of(factory, classification)

        if (fireImmune)
            builder.fireImmune()
        if (size != null)
            builder.sized(size.width, size.height)
        if (velocityUpdates)
            builder.setShouldReceiveVelocityUpdates(velocityUpdates)
        if (clientFactorySpecial != null)
            builder.setCustomClientFactory(clientFactorySpecial)

        builder.setTrackingRange(trackingRange)
        builder.setUpdateInterval(updateInterval)

        return register(id) { builder.build(HardcoreDungeons.ID + ":" + id) }
    }
}