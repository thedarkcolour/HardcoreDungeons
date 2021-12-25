package team.rusty.util.worldgen.biome;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A tool to warn you if your biome is missing features.
 *
 * @author TheDarkColour
 */
public final class BiomeChecker {
    // Immutable map because Java 8 doesn't have Map.of
    private static final Map<String, String> WARNINGS = ImmutableMap.<String, String>builder()
            .put("addDefaultOres", "Missing overworld ores")
            .put("addDefaultUndergroundVariety", "Missing granite, andesite, diorite")
            .put("addDefaultSoftDisks", "Missing clay and sand disks")
            .put("addDefaultCarvers", "Missing overworld caves and ravines")
            .put("addDefaultLakes", "Missing overworld water lakes")
            .put("addDefaultCrystalFormations", "Missing overworld geodes")
            .put("addDefaultMonsterRoom", "Missing overworld dungeons")
            .put("addDefaultSprings", "Missing overworld water and lava springs")
            .put("commonSpawns", "Missing overworld hostile/cave mob spawns")
            .put("farmAnimals", "Missing overworld farm animal spawns").build();

    public static void checkClass(AbstractBiome biome, ResourceLocation id) {
        InputStream contents = biome.getClass().getResourceAsStream(biome.getClass().getSimpleName() + ".class");

        try {
            ClassReader reader = new ClassReader(contents);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            boolean configured = false;

            for (MethodNode method : node.methods) {
                // noinspection AssignmentUsedAsCondition
                if (configured = method.name.equals("configure")) {
                    List<MethodInsnNode> methodCalls = StreamSupport.stream(method.instructions.spliterator(), false)
                            .filter(MethodInsnNode.class::isInstance)
                            .map(MethodInsnNode.class::cast)
                            .filter(insn -> insn.owner.equals("team/rusty/rpg/worldgen/BiomeDefaults")).collect(Collectors.toList());
                    for (Map.Entry<String, String> warning : WARNINGS.entrySet()) {
                        if (methodCalls.stream().noneMatch(insn -> insn.name.equals(warning.getKey()))) {
                            System.out.printf("BiomeChecker: %s (use `%s`) for biome \"%s\"\n", warning.getValue(), warning.getKey(), id);
                        }
                    }
                    break;
                }
            }

            if (!configured) {
                System.out.println("BiomeChecker: Missing `configure` method on biome " + id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
