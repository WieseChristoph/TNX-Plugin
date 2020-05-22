package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.SleepManager;
import net.md_5.bungee.api.ChatColor;

public class SleepListen implements Listener{
	
	Main plugin;
	
	public SleepListen(Main plugin) {
		this.plugin = Main.getInstance();
	}
	
	SleepManager sleepMng = new SleepManager();

	
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e) {
		
		Player p = e.getPlayer();
		
		// check if bp is enabled in config and the player is in full sleep
		if(!e.isCancelled() && e.getBedEnterResult().equals(BedEnterResult.OK) && plugin.getConfig().getBoolean("BedPercentage.enabled")) {
			int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*sleepMng.minPlayerPercent);
			
			// add the player to the sleeping list
			sleepMng.SleepingPlayers.add(p);
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED + sleepMng.SleepingPlayers.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " sind im Bett!");
			
			// if enough players sleep, skip night
			if(sleepMng.SleepingPlayers.size() >= min)
			{
				sleepMng.sleepComplete();
			}
		}
	}
	
	@EventHandler
	public void onBedEnter(PlayerBedLeaveEvent e) {	
		
		Player p = e.getPlayer();
		
		// check if bp is enabled in config and the player is in list
		if(sleepMng.SleepingPlayers.contains(p) && plugin.getConfig().getBoolean("BedPercentage.enabled")) {
			// remove the player from the list
			sleepMng.SleepingPlayers.remove(p);
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED + sleepMng.SleepingPlayers.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " sind im Bett!");
		}
	}
	
}
