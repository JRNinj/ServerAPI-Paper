package de.jrninj.serverapipaper.commands;

import de.jrninj.serverapipaper.ServerAPI;
import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.serverapipaper.coins.CustomPlayer;
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
                    if (args[0].equalsIgnoreCase("me")) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            this.args[0] = player.getName();
                        } else
                            sender.sendMessage(ServerAPI.getPrefix() + "§4Diesen Befehl kann nur ein Spieler verwenden!");
                    } else {
                        sender.sendMessage(ServerAPI.getPrefix() + "§4Der Spieler §6" + args[0] + " §4existiert nicht!");
                        return false;
                    }
                }

                CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(this.args[0])));

                sender.sendMessage(ServerAPI.getPrefix() + "§7§l|| Spieler: §6" + args[0] + "\n" +
                        ServerAPI.getPrefix() + "§7§l|| Coins: §2" + customTarget.getCoins() + "\n" +
                        ServerAPI.getPrefix() + "§7§l|| Euro: §2" + customTarget.getEuros() + "\n" +
                        ServerAPI.getPrefix() + "§7§l|| Spielt seit dem §6" + customTarget.getFirstTimeJoined());

            } else
                sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/info [Name/me]");
        } else if (sender.hasPermission("serverapi.info.me")) {
            if (sender instanceof Player) {
                if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("me")) {
                        Player player = (Player) sender;
                        args[0] = player.getName();
                        CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(args[0])));

                        sender.sendMessage(ServerAPI.getPrefix() + "§7§l|| Spieler: §6" + args[0] + "\n" +
                                ServerAPI.getPrefix() + "§7§l|| Coins: §2" + customTarget.getCoins() + "\n" +
                                ServerAPI.getPrefix() + "§7§l|| Euro: §2" + customTarget.getEuros() + "\n" +
                                ServerAPI.getPrefix() + "§7§l|| Spielt seit dem §6" + customTarget.getFirstTimeJoined());
                    } else
                        sender.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du keiner Rechte!");
                } else
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/info me");
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
                FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getFile());

                for (String uuid : config.getConfigurationSection("Players").getKeys(false)) {
                    tc.add(config.getString("Players." + uuid + ".name"));
                }

                tc.add("me");

                return tc.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
            } else if (sender.hasPermission("serverapi.info.me")) {
                tc.add("me");
                return tc.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
            }

        }

        tc.clear();
        return tc;
    }
}
