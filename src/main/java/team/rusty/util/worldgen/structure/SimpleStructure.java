package team.rusty.util.worldgen.structure;

import com.google.common.base.Suppliers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.function.Supplier;

/**
 * @author TheDarkColour
 */
public class SimpleStructure extends Structure<NoFeatureConfig> {
    /** Configured version of this structure for use in biomes */
    private final Supplier<StructureFeature<NoFeatureConfig, SimpleStructure>> configured = Suppliers.memoize(() -> new StructureFeature<>(this, NoFeatureConfig.INSTANCE));

    public SimpleStructure() {
        super(NoFeatureConfig.CODEC);
    }

    public StructureFeature<NoFeatureConfig, SimpleStructure> configured() {
        return configured.get();
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox bounds, int refs, long seed) {
            super(structure, chunkX, chunkZ, bounds, refs, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistry, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            int x = chunkX << 4;
            int z = chunkZ << 4;

            int baseHeight = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);

            // Try not to place in water
            if (baseHeight < 65) {
                return;
            }

            BlockPos centerPos = new BlockPos(x, 0, z);

            JigsawManager.addPieces(
                    dynamicRegistry,
                    new VillageConfig(() -> {
                        MutableRegistry<JigsawPattern> templateRegistry = dynamicRegistry.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
                        return templateRegistry.get(new ResourceLocation(getFeature().getFeatureName() + "/pool"));
                    }, 10),
                    AbstractVillagePiece::new,
                    generator,
                    manager,
                    centerPos,
                    pieces,
                    random,
                    false,
                    true);

            // do we even need this?
            for (StructurePiece piece : pieces) {
                piece.move(0, 1, 0);
                piece.getBoundingBox().move(0, -1, 0);
            }

            calculateBoundingBox();
        }
    }
}
