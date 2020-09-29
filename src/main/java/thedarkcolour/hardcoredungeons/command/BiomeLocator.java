package thedarkcolour.hardcoredungeons.command;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

public class BiomeLocator {

    /**
     * Finds a biome by searching a square area determined by the {radius}.
     * Moves the cursor by {grid} number of block positions each time a position is checked.
     *
     * @param biomes the biome(s) to look for when checking the world
     *
     * @return a nullable position of a biome in {@code biomes}
     */
    @Nullable
    public static BlockPos locateBiome(BiomeProvider biomeProvider, int x, int y, int z, int radius, int grid, Set<Biome> biomes, Random random, boolean bl) {
        int j = x >> 2;
        int k = z >> 2;
        int l = radius >> 2;
        int m = y >> 2;
        BlockPos blockPos = null;
        int n = 0;
        int o = bl ? 0 : l;

        for (int p = o; p <= l; p += grid) {
            for (int q = -p; q <= p; q += grid) {
                boolean bl2 = Math.abs(q) == p;

                for (int r = -p; r <= p; r += grid) {
                    if (bl) {
                        boolean bl3 = Math.abs(r) == p;
                        if (!bl3 && !bl2) {
                            continue;
                        }
                    }

                    int s = j + r;
                    int t = k + q;
                    if (biomes.contains(biomeProvider.getNoiseBiome(s, m, t))) {
                        if (blockPos == null || random.nextInt(n + 1) == 0) {
                            blockPos = new BlockPos(s << 2, y, t << 2);
                            if (bl) {
                                return blockPos;
                            }
                        }

                        ++n;
                    }
                }
            }
        }

        return blockPos;
    }
}
