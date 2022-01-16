package de.jrninj.serverapipaper.listener;

import de.jrninj.serverapipaper.mysql.MySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDataListener implements Listener {

    @EventHandler
    public void postLogin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();

        try {

            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT USERNAME FROM players WHERE UUID = ?;");


            statement.setString(1, player.getUniqueId().toString());
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {

                if (!rs.getString("USERNAME").equals(player.getName())) {
                    PreparedStatement statement1 = MySQL.getConnection().prepareStatement("UPDATE players SET USERNAME = '" + player.getName() + "' WHERE UUID ='" + player.getUniqueId() + "';");
                    statement1.executeUpdate();
                }


            } else {

                Long datetime = System.currentTimeMillis();
                Date timestamp = new Date(datetime);
                PreparedStatement statement1 = MySQL.getConnection().prepareStatement("INSERT INTO players (ID,USERNAME,UUID,COINS,EUROS,FIRST_JOINED) VALUES (default,'" + player.getName() + "','" + player.getUniqueId() + "',10,20,'" + timestamp + "');");
                statement1.executeUpdate();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}