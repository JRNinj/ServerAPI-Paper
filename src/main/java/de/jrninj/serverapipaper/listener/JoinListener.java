package de.jrninj.serverapipaper.listener;

import de.jrninj.serverapipaper.utils.YMLFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class JoinListener implements Listener {

    @EventHandler
    public void postLogin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        FileConfiguration config = YamlConfiguration.loadConfiguration(YMLFile.getFile());

        if(config.get("Players." + player.getUniqueId().toString()) == null) {

            config.set("Players." + player.getUniqueId().toString() + ".name", player.getName());

            try {
                config.save(YMLFile.getFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        if(!config.getString("Players." + player.getUniqueId().toString() + ".name").equals(player.getName())) {

            config.set("Players." + player.getUniqueId().toString() + ".name", player.getName());

        }

    }

}
