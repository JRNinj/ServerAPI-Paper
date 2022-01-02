package de.jrninj.serverapipaper.api;

import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

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

    public static String getMySQLHost() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Information.MySQL Host") == null) {
            return "§4Kein Config Eintrag gefunden!";
        }

        return config.getString("Information.MySQL Host");
    }

    public static String getMySQLUser() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Information.MySQL Benutzername") == null) {
            return "§4Kein Config Eintrag gefunden!";
        }

        return config.getString("Information.MySQL Benutzername");
    }

    public static String getMySQLPassword() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Information.MySQL Passwort") == null) {
            return "§4Kein Config Eintrag gefunden!";
        }

        return config.getString("Information.MySQL Passwort");
    }

    public static String getMySQLDatabase() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Information.MySQL Datenbank") == null) {
            return "§4Kein Config Eintrag gefunden!";
        }

        return config.getString("Information.MySQL Datenbank");
    }

    public static String getMySQLTable() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Information.MySQL Tabelle") == null) {
            return "§4Kein Config Eintrag gefunden!";
        }

        return config.getString("Information.MySQL Tabelle");
    }

}
