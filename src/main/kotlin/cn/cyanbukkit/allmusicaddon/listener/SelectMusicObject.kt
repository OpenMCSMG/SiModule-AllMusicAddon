package cn.cyanbukkit.allmusicaddon.listener

import cn.cyanbukkit.allmusicaddon.cyanlib.launcher.CyanPluginLauncher.cyanPlugin
import cn.cyanbukkit.allmusicaddon.cyanlib.launcher.Function
import cn.cyanbukkit.allmusicaddon.utils.MoneySystem
import cn.cyanbukkit.allmusicaddon.utils.show
import com.coloryr.allmusic.server.core.AllMusic
import com.coloryr.allmusic.server.core.command.CommandEX
import com.coloryr.allmusic.server.core.music.play.PlayMusic
import com.coloryr.allmusic.server.core.objs.music.MusicObj
import org.bukkit.entity.Player

object SelectMusicObject {

    lateinit var defaultPlayer: Player

    fun run(name: String, sender: String) {
        // cost 系统
        val need = cyanPlugin.config.getInt("Need")
        if (!MoneySystem.removeMoney(sender, need)) {
            show(defaultPlayer, listOf("§c${sender}余额不足, 无法点歌").toMutableList())
            return
        }
        val obj = AllMusic.getMusicApi().search(arrayOf(name), true)
        val id = arrayOf<String>(obj.getSong(obj.page * 10 + (1 - 1)))
        addMusic(defaultPlayer, name, id)
        show(defaultPlayer, listOf("§a已添加歌曲: §e$name", "§7已经消耗${need}").toMutableList())
    }

    /**
     * TODO: 下一首
     */
    fun cutMusic(sender: Any, args: Array<String>) {

    }

    /**
     * Add music
     *
     * @param sender Sender
     * @param name   Username
     * @param args   Arguments
     */
    private fun addMusic(sender: Any, name: String, args: Array<String>) {
        val musicID = when {
            args[0].contains("id=") && !args[0].contains("/?userid") -> {
                if (args[0].contains("&user"))
                    Function.getString(args[0], "id=", "&user")
                else
                    Function.getString(args[0], "id=", null)
            }

            args[0].contains("song/") -> {
                if (args[0].contains("/?userid"))
                    Function.getString(args[0], "song/", "/?userid")
                else
                    Function.getString(args[0], "song/", null)
            }

            else -> args[0]
        }
        if (Function.isInteger(musicID)) {
            when {
                PlayMusic.getListSize() >= AllMusic.getConfig().maxPlayList -> {
                    AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.listFull)
                }

                AllMusic.getConfig().banMusic.contains(musicID) -> {
                    AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.banMusic)
                }

                PlayMusic.haveMusic(musicID) -> {
                    AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.existMusic)
                }

                PlayMusic.havePlayer(name) -> {
                    AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.playerToMany)
                }

                AllMusic.getConfig().banPlayer.contains(name) -> {
                    AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.playerBan)
                }

                else -> {
                    AllMusic.getConfig().RemoveNoMusicPlayer(name)
                    val obj = MusicObj().apply {
                        this.sender = sender
                        id = musicID
                        this.name = name
                        isDefault = false
                    }
                    if (AllMusic.side.onMusicAdd(sender, obj)) {
                        AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.cancel)
                        return
                    }

                    PlayMusic.addTask(obj)
                    AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.success)
                }
            }
        } else
            AllMusic.side.sendMessage(sender, AllMusic.getMessage().addMusic.noID)
    }


}