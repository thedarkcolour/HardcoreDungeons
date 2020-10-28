package thedarkcolour.hardcoredungeons.asm

import net.minecraft.util.registry.DynamicRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeRegistry
import net.minecraft.world.gen.IExtendedNoiseRandom
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.area.IAreaFactory
import net.minecraftforge.coremod.api.ASMAPI
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import java.util.function.LongFunction

/**
 * **All methods should be [JvmStatic]**
 *
 * ASM hooks used by coremods in HCD.
 *
 * @author TheDarkColour
 */
@Suppress("unused")
object ASMHooks {

    @JvmStatic // unused
    fun patchBiomeLayer(method: MethodNode): MethodNode {
        val insnList = method.instructions
        for (insn in insnList) {
            if (insn.opcode == ILOAD && insn.previous.opcode == IFNE) {
                val a = insn.nextInsn(insnList, 1)

                // add patch while original code is still in list
                insnList.insertBefore(a, MethodInsnNode(INVOKESTATIC, "thedarkcolour/hardcoredungeons/asm/ASMHooks", "isMushroomBiome", "(I)Z", false))

                // remove first node
                insnList.remove(a)

                // modify the other node since getting correct label is difficult
                (insn.nextInsn(insnList, 2) as JumpInsnNode).opcode = IFNE

                return method
            }
        }

        throw IllegalStateException("Failed to patch method ${method.name} with descriptor ${method.desc}")
    }

    @JvmStatic // unused
    fun patchShoreLayer(method: MethodNode): MethodNode {
        val insnList = method.instructions
        for (insn in insnList) {
            if (insn.opcode == ILOAD && insn.previous.opcode == IFNE) {
                val a = insn.nextInsn(insnList, 1)

                // add patch while original code is still in list
                insnList.insertBefore(a, MethodInsnNode(INVOKESTATIC, "thedarkcolour/hardcoredungeons/asm/ASMHooks", "isMushroomBiome", "(I)Z", false))

                // remove first node
                insnList.remove(a)

                // modify the other node since getting correct label is difficult
                (insn.nextInsn(insnList, 2) as JumpInsnNode).opcode = IFNE

                // end early
                return method
            }
        }

        throw IllegalStateException("Failed to patch method ${method.name} with descriptor ${method.desc}")
    }

    @JvmStatic // unused
    fun patchLayerUtil(method: MethodNode): MethodNode {
        val insnList = method.instructions

        for (insn in insnList) {
            if (insn is MethodInsnNode && insn.owner == "net/minecraft/world/gen/layer/AddMushroomIslandLayer") {
                // instead of doing next we need the one ahead
                // because we are inserting after the end of
                // the mushroom biome island layer
                val nextNext = insn.nextInsn(insnList, 2)

                val toAdd = InsnList()
                toAdd.add(VarInsnNode(ALOAD, 2)) // param2
                toAdd.add(LdcInsnNode(43L)) // long for LongFunction
                toAdd.add(MethodInsnNode(INVOKEINTERFACE, "java/util/function/LongFunction", ASMAPI.mapMethod("func_202713_a"), "(J)Ljava/lang/Object;", true)) // invoke the LongFunction
                toAdd.add(TypeInsnNode(CHECKCAST, "net/minecraft/world/gen/IExtendedNoiseRandom")) // generic cast for C
                toAdd.add(VarInsnNode(ALOAD, 3)) // iAreaFactory
                toAdd.add(MethodInsnNode(INVOKESTATIC, "thedarkcolour/hardcoredungeons/asm/ASMHooks", "applyToWorld", "(Lnet/minecraft/world/gen/IExtendedNoiseRandom;Lnet/minecraft/world/gen/area/IAreaFactory;)Lnet/minecraft/world/gen/area/IAreaFactory;", false)) // call hook
                toAdd.add(VarInsnNode(ASTORE, 3)) // store result

                // apply the patch
                insnList.insertBefore(nextNext, toAdd)

                // end early
                return method
            }
        }

        throw IllegalStateException("Failed to patch method ${method.name} with descriptor ${method.desc}")
    }

    /**
     * Utility method for getting an `AbstractInsnNode` relative to this one in an `InsnList`.
     *
     * @return the next Opcode in the list after this one.
     */
    @JvmStatic
    private fun AbstractInsnNode.nextInsn(insnList: InsnList, aheadIndex: Int = 1): AbstractInsnNode {
        return insnList[insnList.indexOf(this) + aheadIndex]
    }

    /**
     * Used to patch the biome generation layer.
     *
     * @return if a biome id represents a Mushroom biome.
     */
    @JvmStatic
    fun isMushroomBiome(biomeId: Int): Boolean {
        val b = DynamicRegistries.func_239770_b_().getRegistry(Registry.BIOME_KEY).getValueForKey(BiomeRegistry.getKeyFromID(biomeId))

        return b?.category == Biome.Category.MUSHROOM
    }

    /**
     * Applies the Mushroom Cliffs biome to a world.
     *
     * Used to patch the `LayerUtil` class.
     */
    @Suppress("NAME_SHADOWING", "CanBeVal", "UnnecessaryVariable")
    @JvmStatic
    fun <T : IArea> applyToWorld(
        context: LongFunction<out IExtendedNoiseRandom<T>>,
        area: IAreaFactory<T>
    ): IAreaFactory<T> {
        var area = MushroomCliffsBiomeLayer.apply(context.apply(4L), area)
        return area
    }
/*
    @JvmStatic
    fun organizeRegistries(keys: MutableList<ResourceLocation>) {
        // Register before Biomes
        keys.remove(GameData.SURFACE_BUILDERS)
        keys.remove(GameData.FEATURES)
        keys.remove(GameData.BLOCK_PLACER_TYPES)
        keys.remove(GameData.ENTITIES)

        val i = keys.indexOf(GameData.BIOMES)

        // later
        keys.add(i, GameData.SURFACE_BUILDERS)
        keys.add(i, GameData.FEATURES)
        keys.add(i, GameData.BLOCK_PLACER_TYPES)
        keys.add(i, GameData.ENTITIES)
        // earlier
    }
 */
}