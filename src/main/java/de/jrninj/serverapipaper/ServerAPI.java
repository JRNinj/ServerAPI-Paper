package de.jrninj.serverapipaper;

import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerAPI extends JavaPlugin {

    private static ServerAPI plugin;

    @Override
    public void onEnable() {
        plugin = this;

        YMLFile.fileCreations();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ServerAPI getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Messages.Server Prefix") == null) {
            return "§6Time§cTravel §0>> §7";
        }

        String s = config.getString("Messages.Server Prefix");
        s = s.replace("&", "§");
        return s;
    }
}
