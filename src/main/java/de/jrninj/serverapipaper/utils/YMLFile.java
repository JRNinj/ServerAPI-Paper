package de.jrninj.serverapipaper.utils;

import de.jrninj.serverapipaper.ServerAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YMLFile {

    private static File primalConfig = new File(ServerAPI.getPlugin().getDataFolder().getPath(), "config.yml");

    public static void fileCreations() {

        try {
            if (!ServerAPI.getPlugin().getDataFolder().exists()) {
                ServerAPI.getPlugin().getDataFolder().mkdir();
            }

            //Primal Config
            if (!primalConfig.exists()) {
                primalConfig.createNewFile();

                FileConfiguration config = YamlConfiguration.loadConfiguration(primalConfig);

                config.set("Paths.Speicherpfad (vom ServerAPI Pluginordner ausgesehen)", "/../../configs");

                config.save(primalConfig);
            }

            //Create Directory
            Path saveDirectory = Paths.get(ServerAPI.getPlugin().getDataFolder().getPath() + YamlConfiguration.loadConfiguration(primalConfig).getString("Paths.Speicherpfad (vom ServerAPI Pluginordner ausgesehen)"));
            if (!Files.exists(saveDirectory)) {
                Files.createDirectories(saveDirectory);
            }

            //File
            if (!getFile().exists()) {
                getFile().createNewFile();
            }

            //Messages
            if (!getMessagesFile().exists()) {
                getMessagesFile().createNewFile();

                FileConfiguration config = YamlConfiguration.loadConfiguration(getMessagesFile());

                config.set("Messages.Server Prefix", "&6Time&cTravel &0>> &7");
                config.set("Messages.Keine Rechte (Fehler)", "&4Dafür hast du keine Rechte!");
                config.set("Information.MySQL Host", "xxx.mysql.de");
                config.set("Information.MySQL Benutzername", "Max Mustermann");
                config.set("Information.MySQL Passwort", "12345678");
                config.set("Information.MySQL Datenbank", "DBX35Q");
                config.set("Information.MySQL Tabelle", "table");

                config.save(getMessagesFile());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static File getFile() {
        return new File(ServerAPI.getPlugin().getDataFolder().getPath() + YamlConfiguration.loadConfiguration(primalConfig).getString("Paths.Speicherpfad (vom ServerAPI Pluginordner ausgesehen)"), "players.yml");
    }

    public static File getMessagesFile() {
        return new File(ServerAPI.getPlugin().getDataFolder().getPath() + YamlConfiguration.loadConfiguration(primalConfig).getString("Paths.Speicherpfad (vom ServerAPI Pluginordner ausgesehen)"), "messages.yml");
    }
}
