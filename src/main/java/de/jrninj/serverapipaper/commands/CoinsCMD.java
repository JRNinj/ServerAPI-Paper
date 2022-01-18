package de.jrninj.serverapipaper.commands;

import de.jrninj.serverapipaper.ServerAPI;
import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.serverapipaper.coins.CustomPlayer;
import de.jrninj.serverapipaper.mysql.MySQL;
import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CoinsCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("serverapi.coins")) {
            if(args.length == 3) {

                if (PlayerData.getUUID(args[0]) == null) {
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Der Spieler §6" + args[0] + " §4existiert nicht!");
                    return true;
                }

                try {
                    Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Diese Formatierung ist nicht erlaubt!");
                    return false;
                }

                CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(args[0])));

                double vorbetrag = Double.valueOf(args[2]);
                double betrag = Double.valueOf(args[2]);
                betrag = Math.pow(betrag, 2);
                betrag = Math.sqrt(betrag);

                if (vorbetrag != betrag) {
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Du kannst keine negativen Zahlen benutzen!");
                    return false;
                }

                if (args[1].equalsIgnoreCase("set")) {

                    customTarget.setCoins((int) betrag);

                    sender.sendMessage(ServerAPI.getPrefix() + "§7Du hast das Konto von §6" + args[0] + " §7auf §2" + args[2] + " §2Coins §7gesetzt!");

                } else if (args[1].equalsIgnoreCase("add")) {

                    customTarget.addCoins((int) betrag);

                    sender.sendMessage(ServerAPI.getPrefix() + "§7Du hast §2" + args[2] + " §2Coins §7auf das Konto von §6" + args[0] + " §7hinzugefügt!");

                } else if (args[1].equalsIgnoreCase("remove")) {

                    if ((customTarget.getCoins() - betrag < 0)) {

                        sender.sendMessage(ServerAPI.getPrefix() + "§7Du hast §2" + customTarget.getCoins() + " §2Coins §7vom Konto von §6" + args[0] + " §7abgehoben!");

                        customTarget.setCoins(0);
                        return true;
                    }

                    customTarget.addCoins((int) (-1 * betrag));

                    sender.sendMessage(ServerAPI.getPrefix() + "§7Du hast §2" + args[2] + " §2Coins §7vom Konto von §6" + args[0] + " §7abgehoben!");

                } else
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/euro [Name] [set/add/remove] [Amount]");

            } else if (args.length == 0) {
                if (sender instanceof Player) {

                    Player player = (Player) sender;
                    CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(player.getName())));

                    sender.sendMessage(ServerAPI.getPrefix() + "§7§l|| Spieler: §6" + player.getName() + "\n" +
                            ServerAPI.getPrefix() + "§7§l|| Coins: §2" + customTarget.getCoins());

                } else
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Diesen Befehl kann nur ein Spieler verwenden!");
            } else
                sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/euro [Name] [set/add/remove] [Amount]");
        } else
            sender.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du keiner Rechte!");

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        ArrayList<String> tc = new ArrayList<>();
        if(args.length == 1) {

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

        } else if (args.length == 2) {
            tc.add("set");
            tc.add("add");
            tc.add("remove");
            return tc.stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        } else if (args.length == 3) {
            tc.add("5");
            tc.add("10");
            tc.add("20");
            tc.add("50");
            tc.add("100");
            tc.add("200");
            tc.add("500");
            tc.add("1000");
            tc.add("5000");
            return tc.stream().filter(s -> s.startsWith(args[2])).collect(Collectors.toList());
        }

        tc.clear();
        return tc;
    }
}
