package de.jrninj.serverapipaper.api;

import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {

    public static String getUUID(String username) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getFile());

        for(String s : config.getConfigurationSection("Players").getKeys(false)) {

            if(config.getString("Players." + s + ".name").equals(username)) {
                return s;
            }

        }

        return null;
    }

    public static String getUsername(String uuid) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getFile());

        if(!config.contains("Players." + uuid + ".name")) {
            return null;
        }

        return config.getString("Players." + uuid + ".name");
    }

}
