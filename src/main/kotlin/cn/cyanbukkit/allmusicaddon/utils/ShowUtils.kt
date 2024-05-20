package cn.cyanbukkit.allmusicaddon.utils

import cn.cyanbukkit.allmusicaddon.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.cyanbukkit.allmusicaddon.listener.SelectMusicObject
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

fun show(p: Player, list: MutableList<String>) {
    if (cyanPlugin.config.getBoolean("Show.Scoreboard")) {
        val sb = Scoreboard(
            SelectMusicObject.defaultPlayer, listOf("§8", "§8","§8", "§8", "§8", "§8", "§8", "§8", "§8", "§8"), 20
        )
        var i = list.size - 1
        list.forEach {
            sb.set(it, i)
            i--
        }

        Bukkit.getScheduler().runTaskLater(cyanPlugin, {
            sb.close()
        }, 1 * 60 * 20L)
    }
    if (cyanPlugin.config.getBoolean("Show.Title")) {
        when (list.size) {
            1 -> p.sendTitle(list[0], "",5, 1 * 60 * 20,5)
            2 -> p.sendTitle(list[0], list[1], 5, 1 * 60 * 20,5)
            3 -> {
                p.sendTitle(list[0], list[1], 5, 1 * 60 * 20,5)
                var second = 60
                object : BukkitRunnable() {
                    override fun run() {
                        if (second <= 0) {
                            cancel()
                            return
                        }
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(list[2]))
                    }
                }.runTaskTimer(cyanPlugin, 0, 20)
            }
        }
    }
}