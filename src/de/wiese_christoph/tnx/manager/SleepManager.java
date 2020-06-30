// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.manager;

import net.md_5.bungee.api.ChatColor;
import de.wiese_christoph.tnx.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class SleepManager
{
    public float minPlayerPercent;
    public ArrayList<Player> SleepingPlayers;
    
    public SleepManager() {
        this.minPlayerPercent = 0.5f;
        this.SleepingPlayers = new ArrayList<Player>();
    }
    
    public void sleepComplete() {
        Bukkit.getWorlds().get(0).setTime(0L);
        Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Anscheinend haben genug geschlafen.");
        this.SleepingPlayers.clear();
    }
}
