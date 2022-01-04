package thedarkcolour.hardcoredungeons.registry

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.SpriteRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntitySize
import net.minecraft.entity.EntityType
import net.minecraft.entity.EntityType.Builder
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.network.FMLPlayMessages
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.client.renderer.entity.*
import thedarkcolour.hardcoredungeons.entity.castleton.frayedsoul.FrayedSoulEntity
import thedarkcolour.hardcoredungeons.entity.castleton.knightlyjuggernaut.KnightlyJuggernautEntity
import thedarkcolour.hardcoredungeons.entity.castleton.voidrunner.VoidRunnerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.CastletonDeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.deer.DeerEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mazeboss.MazeBossRenderer
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherEntity
import thedarkcolour.hardcoredungeons.entity.overworld.mushroomarcher.MushroomArcherRenderer
import thedarkcolour.hardcoredungeons.entity.projectile.bullet.SmallBulletEntity
import thedarkcolour.hardcoredungeons.entity.projectile.magic.MagicBoltEntity
import thedarkcolour.hardcoredungeons.util.modLoc
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import java.util.function.BiFunction

@Suppress("MemberVisibilityCanBePrivate")
object HEntities : HRegistry<EntityType<*>>(ForgeRegistries.ENTITIES) {
    // you ever walk into a bush and burn to death? well this guy walked into a bush and burned to death.
    val FRAYED_SOUL by type("frayed_soul", ::FrayedSoulEntity, EntityClassification.MONSTER, size = EntitySize.scalable(0.4f, 0.4f))
    val VOID_RUNNER by type("void_runner", ::VoidRunnerEntity, EntityClassification.MONSTER)
    val CASTLETON_DEER by type("castleton_deer", ::CastletonDeerEntity, EntityClassification.CREATURE)
    val DEER by type("deer", ::DeerEntity, EntityClassification.CREATURE)
    val KNIGHTLY_JUGGERNAUT by type("knightly_juggernaut", ::KnightlyJuggernautEntity, EntityClassification.CREATURE)
    val MAZE_BOSS by type("maze_boss", ::MazeBossEntity, EntityClassification.MONSTER)
    val MUSHROOM_ARCHER by type("mushroom_archer", ::MushroomArcherEntity, EntityClassification.MONSTER)

    //val RAINBOWLAND_SHEEP = Builder
    //    .create(::RainbowlandSheepEntity, EntityClassification.CREATURE)
    //    .build(modLoc("rainbowland_sheep"))

    // projectile
    val SMALL_BULLET by type("small_bullet", ::SmallBulletEntity, EntityClassification.MISC, size = EntitySize.scalable(0.2f, 0.2f), velocityUpdates = true)
    val MAGIC_BOLT by type("magic_bolt", ::MagicBoltEntity, EntityClassification.MISC, size = EntitySize.scalable(0.2f, 0.2f), velocityUpdates = true)

    val BLUE_EYES_SHADER = modLoc("shaders/post/blue_eyes.json")

    // preserves generic type and bundles build + setRegistryName into one call
    private fun <T : Entity> Builder<T>.build(name: ResourceLocation): EntityType<T> {
        val type = build(name.toString())
        type.registryName = name
        return type
    }

    fun registerEntityAttributes(event: EntityAttributeCreationEvent) {
        event.put(FRAYED_SOUL, FrayedSoulEntity.ATTRIBUTES.build())
        event.put(VOID_RUNNER, VoidRunnerEntity.ATTRIBUTES.build())
        event.put(CASTLETON_DEER, DeerEntity.DEFAULT_ATTRIBUTES.build())
        event.put(DEER, DeerEntity.DEFAULT_ATTRIBUTES.build())
        event.put(KNIGHTLY_JUGGERNAUT, KnightlyJuggernautEntity.ATTRIBUTES.build())
        //event.put(RAINBOWLAND_SHEEP, RainbowlandSheepEntity.ATTRIBUTES.build())
        event.put(MAZE_BOSS, MazeBossEntity.ATTRIBUTES.build())
        event.put(MUSHROOM_ARCHER, MushroomArcherEntity.ATTRIBUTES.build())
    }

    fun registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(MAZE_BOSS, ::MazeBossRenderer)
        RenderingRegistry.registerEntityRenderingHandler(MUSHROOM_ARCHER, ::MushroomArcherRenderer)
        RenderingRegistry.registerEntityRenderingHandler(DEER, ::DeerRenderer)
        RenderingRegistry.registerEntityRenderingHandler(FRAYED_SOUL, ::FrayedSoulRenderer)
        RenderingRegistry.registerEntityRenderingHandler(VOID_RUNNER, ::VoidRunnerRenderer)
        //RenderingRegistry.registerEntityRenderingHandler(RAINBOWLAND_SHEEP, ::SheepRenderer)
        RenderingRegistry.registerEntityRenderingHandler(CASTLETON_DEER, ::DeerRenderer)
        RenderingRegistry.registerEntityRenderingHandler(KNIGHTLY_JUGGERNAUT, ::KnightlyJuggernautRenderer)
        RenderingRegistry.registerEntityRenderingHandler(SMALL_BULLET) { SpriteRenderer(it, Minecraft.getInstance().itemRenderer) }
        RenderingRegistry.registerEntityRenderingHandler(MAGIC_BOLT, ::MagicBoltRenderer)
    }

    fun registerEntityShaders() {
        ClientRegistry.registerEntityShader(FrayedSoulEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(VoidRunnerEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(CastletonDeerEntity::class.java, BLUE_EYES_SHADER)
        ClientRegistry.registerEntityShader(KnightlyJuggernautEntity::class.java, BLUE_EYES_SHADER)
    }

    fun <T : Entity> type(
        id: String,
        factory: EntityType.IFactory<T>,
        classification: EntityClassification,
        //serializable: Boolean = true,
        //canSummon: Boolean = true,
        fireImmune: Boolean = false,
        //spawnNaturally: Boolean = true,
        //specialSpawns: ImmutableSet<Block> = ImmutableSet.of(),
        size: EntitySize? = null,
        velocityUpdates: Boolean = false,
        trackingRange: Int = 5,
        updateInterval: Int = 3,
        clientFactorySpecial: BiFunction<FMLPlayMessages.SpawnEntity, World, T>? = null
    ): ObjectHolderDelegate<EntityType<T>> {
        val builder = Builder.of(factory, classification)

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