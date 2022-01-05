package de.jrninj.serverapipaper.api;

import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DefaultValues {

    public static String getNoPerms() {

        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Messages.Keine Rechte (Fehler)") == null) {
            return "&4Dafür hast du keine Rechte!";
        }

        String s = config.getString("Messages.Keine Rechte (Fehler)");
        s = s.replace("&", "§");
        return s;
    }

}
