package de.jrninj.serverapipaper.coins;

import de.jrninj.serverapipaper.ServerAPI;
import de.jrninj.serverapipaper.mysql.MySQL;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class CustomPlayer {

    public UUID uuid;
    private Date timestamp;
    private int coins;
    private int euros;
    private ServerAPI plugin;

    public CustomPlayer(ServerAPI plugin,UUID uuid) {
        try {
            this.plugin = plugin;


            this.uuid = uuid;



            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT EUROS,COINS,FIRST_JOINED FROM players WHERE UUID = ?;");


            statement.setString(1,uuid.toString());
            statement.executeQuery();
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                timestamp = rs.getDate("FIRST_JOINED");
                coins = rs.getInt("COINS");
                euros = rs.getInt("EUROS");


            }else {
                Long datetime = System.currentTimeMillis();
                timestamp = new Date(datetime);
                coins = 10;
                euros = 20;
                PreparedStatement statement1 = MySQL.getConnection().prepareStatement("INSERT INTO players (ID,UUID,COINS,EUROS,FIRST_JOINED) VALUES ("+
                        "default," +
                        "'" + uuid +"',"+
                        coins+"," +
                        euros+"," +
                        "'"+timestamp+"'"+
                        ");");
                statement1.executeUpdate();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getCoins() {
        return coins;
    }

    public int getEuros() {
        return euros;
    }

    public Date getFirstTimeJoined() {
        return timestamp;
    }

    public void setCoins(int coins) {
        this.coins = coins;

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE players SET COINS = '" + coins +"' WHERE UUID ='" + uuid + "';");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addCoins(int coins) {
        coins = this.coins + coins;
        this.coins = coins;

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE players SET COINS = '" + coins +"' WHERE UUID ='" + uuid + "';");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEuros(int euros) {
        this.euros = euros;
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE players SET EUROS = '" + euros +"' WHERE UUID ='" + uuid + "';");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEuros(int euros) {
        euros = this.euros + euros;
        this.euros = euros;

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE players SET EUROS = '" + euros +"' WHERE UUID ='" + uuid + "';");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
