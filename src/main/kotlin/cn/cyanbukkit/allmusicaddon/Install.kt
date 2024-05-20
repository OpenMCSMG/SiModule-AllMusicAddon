package cn.cyanbukkit.allmusicaddon

import cn.cyanbukkit.allmusicaddon.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.cyanbukkit.allmusicaddon.utils.Scoreboard

object Install {

    fun init() {
        Scoreboard.init(cyanPlugin)
    }

}