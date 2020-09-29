package thedarkcolour.hardcoredungeons.biome.overworld

/**
 * The Mushroom Cliffs. A high and steep mushroom style island biome.
 *
 * @author TheDarkColour
 */
/*
class MushroomCliffsBiome : HBiome(Properties()
    .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.MYCELIUM_DIRT_GRAVEL_CONFIG)
    .precipitation(RainType.RAIN)
    .category(Category.MUSHROOM)
    .depth(0.3625f)
    .scale(1.225f)
    .temperature(0.9f)
    .downfall(1.0f)
    .waterColor(4159204)
    .waterFogColor(329011)
) {

    init {
        addStructure(Feature.MINESHAFT.withConfiguration(MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL)))
        addStructure(Feature.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG))
        DefaultBiomeFeatures.addCarvers(this)
        DefaultBiomeFeatures.addStructures(this)
        DefaultBiomeFeatures.addMonsterRooms(this)
        DefaultBiomeFeatures.addStoneVariants(this)
        DefaultBiomeFeatures.addOres(this)
        DefaultBiomeFeatures.addSedimentDisks(this)
        DefaultBiomeFeatures.addHugeMushrooms(this)
        DefaultBiomeFeatures.addMushrooms(this)
        DefaultBiomeFeatures.addReedsAndPumpkins(this)

        HFeatures.withShroomyBoulders(this)

        addSpawn(EntityClassification.CREATURE, SpawnListEntry(EntityType.MOOSHROOM, 8, 4, 8))
        addSpawn(EntityClassification.AMBIENT, SpawnListEntry(EntityType.BAT, 10, 8, 8))

        //BIOMES.add(this)
    }

    @Suppress("DEPRECATION")
    object Layer : IBishopTransformer {
        private val MUSHROOM_CLIFFS = Registry.BIOME.getId(HBiomes.MUSHROOM_CLIFFS)

        override fun apply(p0: INoiseRandom, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int): Int {
            return if (LayerUtil.isShallowOcean(p5) && LayerUtil.isShallowOcean(p4) && LayerUtil.isShallowOcean(
                    p1
                ) && LayerUtil.isShallowOcean(p3) && LayerUtil.isShallowOcean(p2) && p0.random(
                    100
                ) == 0
            ) MUSHROOM_CLIFFS else p5
        }
    }
}*/