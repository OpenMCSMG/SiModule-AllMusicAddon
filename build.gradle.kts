val funcName = "allmusicaddon"
val group = "cn.cyanbukkit.${funcName}"
val version = "0.1"
val mainPlugin = "SiModuleGame"

bukkit {
    name = rootProject.name
    description = "put fun name here"
    authors = listOf("Your Name")
    website = "https://cyanbukkit.cn"
    main = "${group}.cyanlib.launcher.CyanPluginLauncher"
    loadBefore = listOf(mainPlugin)
}

plugins {
    java
    kotlin("jvm") version "1.9.20"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

repositories {
    maven("https://nexus.cyanbukkit.cn/repository/maven-public/")
    maven("https://maven.elmakers.com/repository")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")
    compileOnly(fileTree("libs") { include("*.jar") })
    compileOnly(fileTree("E:\\我的世界互动\\2.动画互游主\\服务端\\plugins\\SiModuleGame-Bukkit-24.5.1.jar"))
}

kotlin {
    jvmToolchain(8)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
    }
}
