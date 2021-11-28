package thedarkcolour.hardcoredungeons.block.combo

import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class VariantCombo(props: HProperties, name: String, vararg variants: String) {
    val variants = Array(variants.size) { i ->
        HBlockMaker.cubeAllWithItem(variants[i] + "_" + name, props)
    }
}