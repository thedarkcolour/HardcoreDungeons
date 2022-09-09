package thedarkcolour.hardcoredungeons.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer

@Suppress("SpellCheckingInspection")
object ReloadModelsCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(Commands.literal("reloadmodels").executes {
            try {
                ReloadableRenderer.reloadables.forEach(ReloadableRenderer<*, *>::reload)
            } catch (ignored: ClassNotFoundException) {
            } catch (t: Throwable) {
                t.printStackTrace()
            }
            1
        })
    }
}