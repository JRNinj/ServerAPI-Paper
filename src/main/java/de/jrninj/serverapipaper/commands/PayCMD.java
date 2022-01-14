package de.jrninj.serverapipaper.commands;

import de.jrninj.serverapipaper.ServerAPI;
import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.serverapipaper.coins.CustomPlayer;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PayCMD implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("serverapi.pay")) {
                if(args.length == 2) {
                    if (PlayerData.getUUID(args[0]) == null) {
                        player.sendMessage(ServerAPI.getPrefix() + "§4Der Spieler §6" + args[0] + " §4existiert nicht!");
                        return false;
                    }

                    if (PlayerData.getUUID(args[0]).equalsIgnoreCase(String.valueOf(player.getUniqueId()))) {
                        player.sendMessage(ServerAPI.getPrefix() + "§4Du kannst dir nicht selbst Geld bezahlen!");
                        return false;
                    }

                    CustomPlayer customPlayer = new CustomPlayer(ServerAPI.getPlugin(), player.getUniqueId());

                    try {
                        Integer.parseInt(args[1]);
                    } catch (Exception ex) {
                        sender.sendMessage(ServerAPI.getPrefix() + "§4Diese Formatierung ist nicht erlaubt!");
                        return false;
                    }

                    if (!(customPlayer.getCoins() >= Integer.valueOf(args[1]))) {
                        player.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du nicht genug Coins!");
                        return false;
                    }

                    CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(args[0])));

                    double vorbetrag = Double.valueOf(args[1]);
                    double betrag = Double.valueOf(args[1]);
                    betrag = Math.pow(betrag, 2);
                    betrag = Math.sqrt(betrag);

                    if (vorbetrag != betrag) {
                        player.sendMessage(ServerAPI.getPrefix() + "§4Du kannst keine negativen Zahlen benutzen!");
                        return false;
                    }

                    customPlayer.addCoins((int) (-1 * betrag));
                    customTarget.addCoins((int) betrag);

                    player.sendMessage(ServerAPI.getPrefix() + "§c|| §7§lRechnung §4" + (int) (-1 * betrag) + "\n" +
                            ServerAPI.getPrefix() + "§c|| §7§lDeine Coins §6" + customPlayer.getCoins() + " §6Coins");

                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[0]);

                        target.sendMessage(ServerAPI.getPrefix() + "§c|| §7§lRechnung §1- §2" + (int) betrag + "\n" +
                                ServerAPI.getPrefix() + "§c|| §7§lDeine Coins §1- §6" + customTarget.getCoins() + " §6Coins");
                    }
                } else if (args.length == 3) {
                    if(args[2].equalsIgnoreCase("Coins")) {

                        if (PlayerData.getUUID(args[0]) == null) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Der Spieler §6" + args[0] + " §4existiert nicht!");
                            return false;
                        }

                        if (PlayerData.getUUID(args[0]).equalsIgnoreCase(String.valueOf(player.getUniqueId()))) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Du kannst dir nicht selbst Geld bezahlen!");
                            return false;
                        }

                        CustomPlayer customPlayer = new CustomPlayer(ServerAPI.getPlugin(), player.getUniqueId());

                        try {
                            Integer.parseInt(args[1]);
                        } catch (Exception ex) {
                            sender.sendMessage(ServerAPI.getPrefix() + "§4Diese Formatierung ist nicht erlaubt!");
                            return false;
                        }

                        if (!(customPlayer.getCoins() >= Integer.valueOf(args[1]))) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du nicht genug Coins!");
                            return false;
                        }

                        CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(args[0])));

                        double vorbetrag = Double.valueOf(args[1]);
                        double betrag = Double.valueOf(args[1]);
                        betrag = Math.pow(betrag, 2);
                        betrag = Math.sqrt(betrag);

                        if (vorbetrag != betrag) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Du kannst keine negativen Zahlen benutzen!");
                            return false;
                        }

                        customPlayer.addCoins((int) (-1 * betrag));
                        customTarget.addCoins((int) betrag);

                        player.sendMessage(ServerAPI.getPrefix() + "§c|| §7§lRechnung §4" + (int) (-1 * betrag) + "\n" +
                                ServerAPI.getPrefix() + "§c|| §7§lDeine Coins §6" + customPlayer.getCoins() + " §6Coins");

                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player target = Bukkit.getPlayer(args[0]);

                            target.sendMessage(ServerAPI.getPrefix() + "§c|| §7§lRechnung §1- §2" + (int) betrag + "\n" +
                                    ServerAPI.getPrefix() + "§c|| §7§lDeine Coins §1- §6" + customTarget.getCoins() + " §6Coins");
                        }

                    } else if(args[2].equalsIgnoreCase("Euro")) {

                        if (PlayerData.getUUID(args[0]) == null) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Der Spieler §6" + args[0] + " §4existiert nicht!");
                            return false;
                        }

                        if (PlayerData.getUUID(args[0]).equalsIgnoreCase(String.valueOf(player.getUniqueId()))) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Du kannst dir nicht selbst Geld bezahlen!");
                            return false;
                        }

                        CustomPlayer customPlayer = new CustomPlayer(ServerAPI.getPlugin(), player.getUniqueId());

                        try {
                            Integer.parseInt(args[1]);
                        } catch (Exception ex) {
                            sender.sendMessage(ServerAPI.getPrefix() + "§4Diese Formatierung ist nicht erlaubt!");
                            return false;
                        }

                        if (!(customPlayer.getCoins() >= Integer.valueOf(args[1]))) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du nicht genug Geld!");
                            return false;
                        }

                        CustomPlayer customTarget = new CustomPlayer(ServerAPI.getPlugin(), UUID.fromString(PlayerData.getUUID(args[0])));

                        double vorbetrag = Double.valueOf(args[1]);
                        double betrag = Double.valueOf(args[1]);
                        betrag = Math.pow(betrag, 2);
                        betrag = Math.sqrt(betrag);

                        if (vorbetrag != betrag) {
                            player.sendMessage(ServerAPI.getPrefix() + "§4Du kannst keine negativen Zahlen benutzen!");
                            return false;
                        }

                        customPlayer.addEuros((int) (-1 * betrag));
                        customTarget.addEuros((int) betrag);

                        player.sendMessage(ServerAPI.getPrefix() + "§c|| §7§lRechnung §4" + (int) (-1 * betrag) + "\n" +
                                ServerAPI.getPrefix() + "§c|| §7§lDeine Coins §6" + customPlayer.getEuros() + " §6Euro");

                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player target = Bukkit.getPlayer(args[0]);

                            target.sendMessage(ServerAPI.getPrefix() + "§c|| §7§lRechnung §1- §2" + (int) betrag + "\n" +
                                    ServerAPI.getPrefix() + "§c|| §7§lDeine Coins §1- §6" + customTarget.getEuros() + " §6Coins");
                        }

                    } else
                        player.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/pay [Name] [Amount] [Euro/Coins]");
                } else
                    sender.sendMessage(ServerAPI.getPrefix() + "§4Bitte benutze: §6/pay [Name] [Amount] [Euro/Coins]");
            } else
                sender.sendMessage(ServerAPI.getPrefix() + "§4Dafür hast du keiner Rechte!");
        } else
            sender.sendMessage(ServerAPI.getPrefix() + "§4Diesen Befehl kann nur ein Spieler verwenden!");

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        ArrayList<String> tc = new ArrayList<>();
        if(args.length == 1) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getFile());

            for (String uuid : config.getConfigurationSection("Players").getKeys(false)) {
                tc.add(config.getString("Players." + uuid + ".name"));
            }
            return tc.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        } else if (args.length == 2) {
            tc.add("5");
            tc.add("10");
            tc.add("20");
            tc.add("50");
            tc.add("100");
            tc.add("200");
            tc.add("500");
            tc.add("1000");
            tc.add("5000");
            return tc.stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        } else if (args.length == 3) {
            tc.add("Euro");
            tc.add("Coins");
            return tc.stream().filter(s -> s.startsWith(args[2])).collect(Collectors.toList());
        }

        tc.clear();
        return tc;
    }
}
