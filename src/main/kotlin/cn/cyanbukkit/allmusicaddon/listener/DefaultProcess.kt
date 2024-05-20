package cn.cyanbukkit.allmusicaddon.listener

import cn.cyanbukkit.allmusicaddon.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.cyanbukkit.allmusicaddon.utils.MoneySystem
import cn.nostmc.pixgame.api.LiveMessageEvent
import cn.nostmc.pixgame.api.data.Chat
import cn.nostmc.pixgame.api.data.Gift
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * 默认处理监听直播的事件
 */
object DefaultProcess : Listener {

    @EventHandler
    fun onMessage(e: LiveMessageEvent) {
        when (e.user) {
            is Chat -> {
                val use = e.user as Chat
                if (use.context.startsWith("点歌")) {
                    use.context.let {
                        SelectMusicObject.run(it.replace("点歌", "").trim(), use.user.name)
                    }
                }
            }

            is Gift -> {
                val gift = e.user as Gift
                cyanPlugin.config.getInt("CostTable.${gift.id}").let {
                    MoneySystem.addMoney(gift.user.name, it)
                }
            }
        }
    }


    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        if (e.player.name == cyanPlugin.config.getString("DefaultPlayer")) {
            SelectMusicObject.defaultPlayer = e.player
        }
    }

}