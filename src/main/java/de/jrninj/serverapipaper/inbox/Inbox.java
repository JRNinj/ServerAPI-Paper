package de.jrninj.serverapipaper.inbox;

import de.jrninj.serverapipaper.api.PlayerData;
import de.jrninj.serverapipaper.mysql.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Inbox {

    public static void add(UUID uuid, String item) {

        if (PlayerData.getUsername(uuid.toString()) == null) {
            return;
        }

        try {
            String items;

            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT UUID,ITEMS FROM `inbox` WHERE UUID = ?;");

            statement.setString(1, uuid.toString());
            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                items = resultSet.getString("ITEMS");

                items = items + "``" + item;

                PreparedStatement statement1 = MySQL.getConnection().prepareStatement("UPDATE `inbox` SET ITEMS = '" + items + "' WHERE UUID = '" + uuid + "';");
                statement1.executeUpdate();
            } else {
                PreparedStatement statement1 = MySQL.getConnection().prepareStatement("INSERT INTO `inbox` (`ID`, `UUID`, `ITEMS`) VALUES (default, '" + uuid + "', '" + item + "');");
                statement1.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void clear(UUID uuid) {

        if (PlayerData.getUsername(uuid.toString()) == null) {
            return;
        }

        try {

            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT UUID,ITEMS FROM `inbox` WHERE UUID = ?;");


            statement.setString(1,uuid.toString());
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {

                PreparedStatement statement1 = MySQL.getConnection().prepareStatement("UPDATE `inbox` SET ITEMS = NULL WHERE UUID ='" + uuid + "';");
                statement1.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> get(UUID uuid) {

        try {
            String items;

            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT UUID,ITEMS FROM `inbox` WHERE UUID = ?;");

            statement.setString(1, uuid.toString());
            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> sl = new ArrayList<>();

            if (resultSet.next()) {

                if (resultSet.getString("ITEMS") == null) {
                    return null;
                }

                String s = resultSet.getString("ITEMS");

                for (String x : s.split("``")) {
                    sl.add(x);
                }

                return sl;

            } else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
