package de.jrninj.serverapipaper.coins;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private HashMap<UUID,CustomPlayer> customPlayers = new HashMap<UUID,CustomPlayer>();

    public CustomPlayer getCustomPLayer(UUID uuid) {

        return customPlayers.get(uuid);
    }
    public void addCustomPlayer(UUID uuid,CustomPlayer player){
        customPlayers.put(uuid,player);

    }
    public void removeCustomPlayer(UUID uuid){

        customPlayers.remove(uuid);



    }

}
