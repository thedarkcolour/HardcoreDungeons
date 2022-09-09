package team.rusty.util.structure;

import com.mojang.datafixers.util.Function7;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

/**
 * Thanks to TelepathicGrunt for their continued structure tutorials
 *
 * @author TheDarkColour
 */
public abstract class SimpleStructure extends Structure {
    protected static <T extends SimpleStructure> Codec<T> makeCodec(Function7<StructureSettings, Holder<StructureTemplatePool>, Optional<ResourceLocation>, Integer, HeightProvider, Optional<Heightmap.Types>, Integer, T> factory) {
        return RecordCodecBuilder.create((codec) -> {
            return codec.group(
                    Structure.settingsCodec(codec),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_to_start_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(codec, factory);
        });
    }

    protected final Holder<StructureTemplatePool> startPool;
    protected final Optional<ResourceLocation> startJigsawName;
    protected final int size;
    protected final HeightProvider startHeight;
    protected final Optional<Heightmap.Types> projectStartToHeightmap;
    protected final int maxDistanceFromCenter;

    public SimpleStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(settings);

        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (canSpawn(context)) {
            BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

            return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.size, blockpos, false, this.projectStartToHeightmap, this.maxDistanceFromCenter);
        } else {
            return Optional.empty();
        }
    }

    protected boolean canSpawn(GenerationContext context) {
        var pos = context.chunkPos().getWorldPosition();
        var level = context.heightAccessor();

        var landHeight = context.chunkGenerator().getFirstOccupiedHeight(pos.getX(), pos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, level, context.randomState());

        // Don't spawn on water
        var columnOfBlocks = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), level, context.randomState());
        var topBlock = columnOfBlocks.getBlock(landHeight);

        return topBlock.getFluidState().isEmpty();
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String toString() {
        return type().toString();
    }
}
