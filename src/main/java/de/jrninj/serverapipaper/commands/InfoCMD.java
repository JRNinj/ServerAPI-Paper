package de.jrninj.serverapipaper.commands;

import de.jrninj.serverapipaper.ServerAPI;
import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.serverapipaper.coins.CustomPlayer;
import de.jrninj.serverapipaper.mysql.MySQL;
import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class InfoCMD implements CommandExecutor, TabCompleter {

    public String[] args;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    this.args = args;

        if (sender.hasPermission("serverapi.info")) {
            if (args.length == 1) {

                if (PlayerData.getUUID(args[0]) == null) {
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Der Spieler §6" + args[0] + " §4existiert nicht!");
                    return false;
                }

                CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(this.args[0])));

                sender.sendMessage(ServerAPI.getPrefix() + "§7§l|| Spieler: §6" + args[0] + "\n" +
                        ServerAPI.getPrefix() + "§7§l|| Coins: §2" + customTarget.getCoins() + "\n" +
                        ServerAPI.getPrefix() + "§7§l|| Euro: §2" + customTarget.getEuros() + "\n" +
                        ServerAPI.getPrefix() + "§7§l|| Spielt seit dem §6" + customTarget.getFirstTimeJoined());

            } else if (args.length == 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(player.getName())));

                    sender.sendMessage(ServerAPI.getPrefix() + "§7§l|| Spieler: §6" + player.getName() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Coins: §2" + customTarget.getCoins() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Euro: §2" + customTarget.getEuros() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Spielt seit dem §6" + customTarget.getFirstTimeJoined());

                } else
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Diesen Befehl kann nur ein Spieler verwenden!");
            } else
                sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/info [Name]");
        } else if (sender.hasPermission("serverapi.info.me")) {
            if (sender instanceof Player) {
                if (args.length == 0) {

                    Player player = (Player) sender;
                    CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(player.getName())));

                    sender.sendMessage(ServerAPI.getPrefix() + "§7§l|| Spieler: §6" + player.getName() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Coins: §2" + customTarget.getCoins() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Euro: §2" + customTarget.getEuros() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Spielt seit dem §6" + customTarget.getFirstTimeJoined());

                } else
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/info");
            } else
                sender.sendMessage(ServerAPI.getPrefix() + "§4Diesen Befehl kann nur ein Spieler verwenden!");
        } else
            sender.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du keiner Rechte!");



        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        ArrayList<String> tc = new ArrayList<>();

        if (args.length == 1) {

            if (sender.hasPermission("serverapi.info")) {
                try {
                    PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT USERNAME FROM `players`;");
                    statement.executeQuery();

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        tc.add(resultSet.getString("USERNAME"));
                    }

                    return tc.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        tc.clear();
        return tc;
    }
}
