package de.jrninj.serverapipaper;

import de.jrninj.serverapipaper.mysql.MySQL;
import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerAPI extends JavaPlugin {

    private static ServerAPI plugin;

    @Override
    public void onEnable() {
        plugin = this;

        YMLFile.fileCreations();

        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.primalConfig);
        if(config.getBoolean("Settings.MySQL")) {
            MySQL.connect();
        }

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "§2Die ServerAPI wurde erfolgreich aktiviert!");

    }

    @Override
    public void onDisable() {

        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.primalConfig);
        if(config.getBoolean("Settings.MySQL")) {
            MySQL.disconnect();
        }

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cDie ServerAPI wurde deaktiviert!");

    }

    public static ServerAPI getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Messages.Server Prefix") == null) {
            return "§5Shulker§Games §0>> §7";
        }

        String s = config.getString("Messages.Server Prefix");
        s = s.replace("&", "§");
        return s;
    }
}
