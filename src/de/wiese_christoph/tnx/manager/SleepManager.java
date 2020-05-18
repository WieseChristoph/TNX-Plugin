package de.wiese_christoph.tnx.manager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class SleepManager {
	
	// minimum players to sleep
	public float minPlayerPercent = 0.5f;
		
	public ArrayList<Player> SleepingPlayers = new ArrayList<Player>();
	
	public void sleepComplete() {
		Bukkit.getWorlds().get(0).setTime(0);
		Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug geschlafen.");
		SleepingPlayers.clear();
	}
}
