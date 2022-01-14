package de.jrninj.serverapipaper.mysql;

import de.jrninj.serverapipaper.ServerAPI;
import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.sql.*;

public class MySQL {

    private static Connection connection;

    //Info

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

    public static String getMySQLPort() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Information.MySQL Port") == null) {
            return "§4Kein Config Eintrag gefunden!";
        }

        return config.getString("Information.MySQL Port");
    }

    //Connection

    public static void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + getMySQLHost() + ":" + getMySQLPort() + "/" + getMySQLDatabase(), getMySQLUser(), getMySQLPassword());
                Bukkit.getConsoleSender().sendMessage(ServerAPI.getPrefix() + "§2Die MySQL Verbindung wurde erfolgreich hergestellt!");
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ServerAPI.getPrefix() + "§4Bei der Verbindung zur MySQL Datenbank ist ein Fehler aufgetreten §7--> bitte üperprüfe ob deine Datenbank gestartet und alle Werte richtig in die Config eingegeben wurden!");
                Bukkit.getServer().shutdown();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                Bukkit.getConsoleSender().sendMessage(ServerAPI.getPrefix() + "§2Die MySQL Verbindung wurde erfolgreich unterbrochen!");
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ServerAPI.getPrefix() + "§4Die MySQL Datenbank konnte nicht entkoppelt werden §7--> bitte üperprüfe ob deine Datenbank gestartet und alle Werte richtig in die Config eingegeben wurden!");
            }
        }
    }

    public static boolean isConnected() {
        return  (connection != null);
    }

    public static void update(String query) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResult(String query) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() {
        return connection;
    }

    //Other

    public static Boolean isEnabled() {
        return YamlConfiguration.loadConfiguration(YMLFile.primalConfig).getBoolean("Settings.MySQL");
    }
}
