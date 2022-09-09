package thedarkcolour.hardcoredungeons.legacy

import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// Too lazy to rewrite all the models.
// Now, use some Kotlin magic to make 1.16 code compile with minimal changes.

/**
 * Creates a new model configuration defining the parts and children of a model.
 * @return A new model configuration
 */
inline fun modelConfigure(configuration: ModelConfiguration.() -> Unit): ModelConfiguration {
    val cfg = ModelConfiguration()
    cfg.configuration()
    return cfg
}

private fun camelToSnakeCase(string: String): String {
    return (string as java.lang.String).replaceAll("([a-z])([A-Z]+)", "$1_$2").lowercase()
}

/**
 * Model configuration that can be written in a manner similar to pre-1.17 models.
 */
class ModelConfiguration {
    private val renderers = hashMapOf<String, ModelRendererConfigure>()
    var texWidth = 0
    var texHeight = 0

    // camel case -> snake case
    operator fun provideDelegate(thisRef: Nothing?, prop: KProperty<*>): ReadOnlyProperty<Nothing?, ModelRendererConfigure> {
        val name = camelToSnakeCase(prop.name)
        val renderer = ModelRendererConfigure(name)
        this.renderers[name] = renderer
        return ReadOnlyProperty { _, _ -> renderer }
    }

    fun asLayerDef(): LayerDefinition {
        val mesh = MeshDefinition()
        val root = mesh.root

        for (renderer in renderers.values) {
            addToParent(root, renderer)
        }

        return LayerDefinition.create(mesh, texWidth, texHeight)
    }

    fun String.addChild(renderer: ModelRendererConfigure) {
        renderers[camelToSnakeCase(this)]!!.addChild(renderer)
    }

    /**
     * Configures this model configuration again.
     */
    inline fun then(configuration: ModelConfiguration.() -> Unit): ModelConfiguration {
        this.configuration()
        return this
    }
}

/**
 * Recursively adds a child model to the model hierarchy of the given model renderer
 */
private fun addToParent(root: PartDefinition, renderer: ModelRendererConfigure) {
    val cubes = CubeListBuilder.create()
    for (box in renderer.boxes) {
        val deformation = if (box.scale == 0f) CubeDeformation.NONE else CubeDeformation(box.scale)
        cubes.texOffs(box.texOffX, box.texOffY)
        cubes.mirror(box.mirror)
        cubes.addBox(box.x, box.y, box.z, box.xSize, box.ySize, box.zSize, deformation)
    }
    val pose = PartPose.offsetAndRotation(renderer.x, renderer.y, renderer.z, renderer.xRot, renderer.yRot, renderer.zRot)
    val part = root.addOrReplaceChild(renderer.name, cubes, pose)

    for (child in renderer.children) {
        addToParent(part, child)
    }
}

class ModelRendererConfigure(val name: String) {
    private var texOffX = 0
    private var texOffY = 0

    var x = 0f
    var y = 0f
    var z = 0f

    var xRot = 0f
    var yRot = 0f
    var zRot = 0f

    val boxes = arrayListOf<ModelBox>()

    private var hasParent = false
    val children = arrayListOf<ModelRendererConfigure>()

    fun setPos(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun addBox(
        x: Float,
        y: Float,
        z: Float,
        xSize: Float,
        ySize: Float,
        zSize: Float,
        growth: Float,
        mirror: Boolean
    ) {
        val b = ModelBox(x, y, z, xSize, ySize, zSize, texOffX, texOffY, growth, mirror)
        boxes.add(b)
    }

    fun addChild(child: ModelRendererConfigure) {
        if (child.hasParent) {
            throw IllegalStateException("Child is already parented")
        }
        child.hasParent = true
        children.add(child)
    }

    fun texOffs(x: Int, y: Int): ModelRendererConfigure {
        this.texOffX = x
        this.texOffY = y

        return this
    }
}

class ModelBox(
    val x: Float,
    val y: Float,
    val z: Float,
    val xSize: Float,
    val ySize: Float,
    val zSize: Float,
    val texOffX: Int,
    val texOffY: Int,
    val scale: Float,
    val mirror: Boolean,
)
