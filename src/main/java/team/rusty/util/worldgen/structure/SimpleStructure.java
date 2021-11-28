package team.rusty.util.worldgen.structure;

import com.google.common.base.Suppliers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import java.util.function.Supplier;

/**
 * @author TheDarkColour
 */
public class SimpleStructure extends StructureFeature<NoneFeatureConfiguration> {
    /** Configured version of this structure for use in biomes */
    private final Supplier<ConfiguredStructureFeature<NoneFeatureConfiguration, SimpleStructure>> configured = Suppliers.memoize(() -> new ConfiguredStructureFeature<>(this, NoneFeatureConfiguration.INSTANCE));

    public SimpleStructure() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public ConfiguredStructureFeature<NoneFeatureConfiguration, SimpleStructure> configured() {
        return configured.get();
    }

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return Start::new;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    public static class Start extends StructureStart<NoneFeatureConfiguration> {
        public Start(StructureFeature<NoneFeatureConfiguration> structure, ChunkPos pos, int refs, long seed) {
            super(structure, pos, refs, seed);
        }

        @Override
        public void generatePieces(RegistryAccess dynamicRegistry, ChunkGenerator generator, StructureManager manager, ChunkPos chunkPos, Biome biome, NoneFeatureConfiguration config, LevelHeightAccessor level) {
            var x = chunkPos.x << 4;
            var z = chunkPos.z << 4;

            var baseHeight = generator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, level);

            // Try not to place in water
            if (baseHeight < 65) {
                return;
            }

            var centerPos = new BlockPos(x, 0, z);

            JigsawPlacement.addPieces(
                    dynamicRegistry,
                    new JigsawConfiguration(() -> {
                        var templateRegistry = dynamicRegistry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
                        return templateRegistry.get(new ResourceLocation(getFeature().getFeatureName() + "/pool"));
                    }, 10),
                    PoolElementStructurePiece::new,
                    generator,
                    manager,
                    centerPos,
                    this,
                    random,
                    false,
                    true,
                    level);

            // do we even need this?
            for (var piece : pieces) {
                piece.move(0, 1, 0);
                piece.getBoundingBox().move(0, -1, 0);
            }
        }
    }
}
