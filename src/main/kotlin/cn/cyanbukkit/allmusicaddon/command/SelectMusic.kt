package cn.cyanbukkit.allmusicaddon.command

import cn.cyanbukkit.allmusicaddon.listener.SelectMusicObject
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SelectMusic : Command("selectmusic") {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c只有玩家才能执行此命令")
            return true
        }
        SelectMusicObject.run(args.joinToString(" "), sender.name)
        return true
    }


}
