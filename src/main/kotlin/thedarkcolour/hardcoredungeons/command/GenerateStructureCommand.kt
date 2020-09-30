package thedarkcolour.hardcoredungeons.command
/*
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.command.ISuggestionProvider
import net.minecraft.command.arguments.BlockPosArgument
import net.minecraft.command.arguments.ResourceLocationArgument
import net.minecraft.command.arguments.SuggestionProviders
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.MutableBoundingBox
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraftforge.registries.ForgeRegistries

object GenerateStructureCommand {
    private val ALL_STRUCTURES = SuggestionProviders.register<CommandSource>(ResourceLocation("all_structures")) { _, builder ->
        ISuggestionProvider.suggestIterable(ForgeRegistries.FEATURES.entries.filter {
                (_, obj) -> obj is Structure<*>
        }.map { (a, _) -> a.registryName }, builder)
    }

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        dispatcher.register(Commands.literal("generate_structure").requires {
            it.hasPermissionLevel(2)
        }.then(Commands.argument("structure", ResourceLocationArgument.resourceLocation()).suggests(ALL_STRUCTURES).executes { ctx ->
            val source = ctx.source
            val pos = BlockPos(source.pos)

            execute(source, pos, source.world.getBiome(pos), suggestStructure(ctx))
        }.then(Commands.argument("pos", BlockPosArgument.blockPos()).executes { ctx ->
            val source = ctx.source
            val pos = ctx.getArgument("pos", BlockPos::class.java)

            execute(source, pos, source.world.getBiome(pos), suggestStructure(ctx))
        }.then(Commands.argument("biome", ResourceLocationArgument.resourceLocation()).suggests(LocateBiomeCommand.ALL_BIOMES).executes { ctx ->
            execute(ctx.source, ctx.getArgument("pos", BlockPos::class.java), LocateBiomeCommand.suggestBiome(ctx), suggestStructure(ctx))
        }))))
    }

    private fun suggestStructure(ctx: CommandContext<CommandSource>): Structure<*> {
        val location = ctx.getArgument("structure", ResourceLocation::class.java)

        return ForgeRegistries.FEATURES.getValue(location) as Structure<*>
    }

    private fun execute(source: CommandSource, pos: BlockPos, biome: Biome, structure: Structure<*>): Int {
        val world = source.world
        val chunkX = pos.x shr 4
        val chunkZ = pos.z shr 4
        val start = structure.startFactory.create(structure, chunkX, chunkZ, MutableBoundingBox.getNewBoundingBox(), 0, world.seed)
        val generator = world.chunkProvider.chunkGenerator

        start.init(generator, world.structureTemplateManager, chunkX, chunkZ, biome)
        start.generateStructure(world, generator, world.rand, MutableBoundingBox.getNewBoundingBox(), ChunkPos(chunkX, chunkZ))
        return 0
    }
}*/