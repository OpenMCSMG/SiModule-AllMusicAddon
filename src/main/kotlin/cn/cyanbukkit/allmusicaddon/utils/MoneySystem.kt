package cn.cyanbukkit.allmusicaddon.utils

import cn.cyanbukkit.allmusicaddon.cyanlib.launcher.CyanPluginLauncher.cyanPlugin

object MoneySystem {

    fun addMoney(user: String, money: Int) : Boolean {
        return try {
            val new = cyanPlugin.userData.getInt(user) +  money
            cyanPlugin.userData.set(user, new)
            cyanPlugin.saveUserData()
            true
        } catch (e: Exception) {
            cyanPlugin.logger.warning("Error while adding money to $user: ${e.message}")
            false
        }
    }

    fun removeMoney(user: String, money: Int) : Boolean {
        return try {
            val new = cyanPlugin.userData.getInt(user) -  money
            if (new < 0) return false
            cyanPlugin.userData.set(user, new)
            cyanPlugin.saveUserData()
            true
        } catch (e: Exception) {
            cyanPlugin.logger.warning("Error while removing money from $user: ${e.message}")
            false
        }
    }

    fun getMoney(user: String): Int {
        if (cyanPlugin.userData.contains(user)) {
            return cyanPlugin.userData.getInt(user)
        }
        return 0
    }

    fun setMoney(user: String, money: Int) {
        cyanPlugin.userData.set(user, money)
        cyanPlugin.saveUserData()
    }

}