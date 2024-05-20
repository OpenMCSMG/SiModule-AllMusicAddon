package cn.cyanbukkit.allmusicaddon.cyanlib.launcher;

import cn.cyanbukkit.allmusicaddon.Install;
import cn.cyanbukkit.allmusicaddon.cyanlib.loader.KotlinBootstrap;
import cn.cyanbukkit.allmusicaddon.command.SelectMusic;
import cn.cyanbukkit.allmusicaddon.listener.DefaultProcess;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;

/**
 * 嵌套框架
 */

public class CyanPluginLauncher extends JavaPlugin {

    public static CyanPluginLauncher cyanPlugin;
    public File yaml;
    public File user;
    public YamlConfiguration config;
    public YamlConfiguration userData;
    public String mainPlugin = "SiModuleGame";
    public String me = getDescription().getName();

    public CyanPluginLauncher() {
        cyanPlugin = this;
        KotlinBootstrap.init();
    }

    public void registerCommand(Command command) {
        Class<?> pluginManagerClass = cyanPlugin.getServer().getPluginManager().getClass();
        try {
            Field field = pluginManagerClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) field.get(cyanPlugin.getServer().getPluginManager());
            commandMap.register(cyanPlugin.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public File getDataFold() {
        return new File("plugins/" + mainPlugin + "/addon/" + me );
    }


    @Override
    public void saveDefaultConfig() {
        if (!getDataFold().exists()) { //plugins/SiModuleGame/addon/AllMusicAddon
            if (!getDataFold().getParentFile().exists()) { //plugins/SiModuleGame/addon
                if (!getDataFold().getParentFile().getParentFile().exists()) { //plugins/SiModuleGame/
                    getDataFold().getParentFile().getParentFile().mkdir();
                }
                getDataFold().getParentFile().mkdir();
            }
            getDataFold().mkdir();
        }
        yaml = new File(getDataFold(), "config.yml");
        if (!yaml.exists()) {
            try {
                InputStream is = getResource("config.yml");
                if (is != null) {
                    Files.copy(is, yaml.toPath());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(yaml);

        user = new File(getDataFold(), "userdata.yml");
        if (!user.exists()) {
            try {
                user.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        userData = YamlConfiguration.loadConfiguration(user);
    }


    public void saveUserData() {
        try {
            userData.save(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConfig() {
        try {
            config.save(yaml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(yaml);
    }


    @Override
    public FileConfiguration getConfig() {
        return config;
    }


    @Override
    protected File getFile() {
        return yaml;
    }


    @Override
    public void onLoad() {
    }


    @Override
    public void onEnable() {
        registerCommand(SelectMusic.INSTANCE);
        saveDefaultConfig();
        Install.INSTANCE.init();
        if (config.getBoolean("EnableDefaultProcessing")) {
            getServer().getPluginManager().registerEvents(DefaultProcess.INSTANCE, this);
        }
    }

    @Override
    public void onDisable() {

    }


}