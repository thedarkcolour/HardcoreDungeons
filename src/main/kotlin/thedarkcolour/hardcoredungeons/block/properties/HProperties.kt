package thedarkcolour.hardcoredungeons.block.properties

class HProperties private constructor() : BlockProperties<HProperties>() {
    companion object : Factory<HProperties>() {
        override fun createProperties(): HProperties {
            return HProperties()
        }
    }

    override fun getFactory(): Factory<HProperties> {
        return Companion
    }
}