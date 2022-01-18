package de.jrninj.serverapipaper;

import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.serverapipaper.coins.PlayerManager;
import de.jrninj.serverapipaper.commands.*;
import de.jrninj.serverapipaper.listener.PlayerDataListener;
import de.jrninj.serverapipaper.mysql.MySQL;
import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.RowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class ServerAPI extends JavaPlugin {

    private static ServerAPI plugin;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        plugin = this;

        YMLFile.fileCreations();

        register();

        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.primalConfig);
        if(config.getBoolean("Settings.MySQL")) {
            MySQL.connect();
        } else
            Bukkit.getConsoleSender().sendMessage(ServerAPI.getPrefix() + "§4MySQL ist derzeit deaktiviert, gehe in die Config um es zu aktivieren!");

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

    private void register() {
        //Other Stuff
        playerManager = new PlayerManager();

        //Listener
        Bukkit.getPluginManager().registerEvents(new PlayerDataListener(), this);

        //Commands
        Bukkit.getPluginCommand("pay").setExecutor(new PayCMD());
        Bukkit.getPluginCommand("pay").setExecutor(new PayCMD());
        Bukkit.getPluginCommand("euro").setExecutor(new EuroCMD());
        Bukkit.getPluginCommand("euro").setExecutor(new EuroCMD());
        Bukkit.getPluginCommand("coins").setExecutor(new CoinsCMD());
        Bukkit.getPluginCommand("coins").setExecutor(new CoinsCMD());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCMD());
        Bukkit.getPluginCommand("money").setExecutor(new MoneyCMD());
        Bukkit.getPluginCommand("info").setExecutor(new InfoCMD());
        Bukkit.getPluginCommand("info").setExecutor(new InfoCMD());
    }

    public static ServerAPI getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getMessagesFile());

        if(config.get("Messages.Server Prefix") == null) {
            return "§5Shulker§6Games §0>> §7";
        }

        String s = config.getString("Messages.Server Prefix");
        s = s.replace("&", "§");
        return s;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
