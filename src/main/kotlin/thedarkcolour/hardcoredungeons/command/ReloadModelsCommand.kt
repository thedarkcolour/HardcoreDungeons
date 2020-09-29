package thedarkcolour.hardcoredungeons.command

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import thedarkcolour.hardcoredungeons.entity.ReloadableRenderer

@Suppress("SpellCheckingInspection")
object ReloadModelsCommand {
    fun register(dispatcher: CommandDispatcher<CommandSource>) {
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