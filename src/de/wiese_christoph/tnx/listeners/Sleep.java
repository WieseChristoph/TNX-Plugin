package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.Listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class Sleep implements Listener, CommandExecutor{
	
	Main plugin;
	
	public Sleep(Main plugin) {
		this.plugin = plugin;
	}
	
	// minimum players to vote
	float minPlayerPercent = 0.5f;
	
	static ArrayList<Player> SleepingPlayers = new ArrayList<Player>();

	
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent e) {
		
		Player p = e.getPlayer();
		
		if(!e.isCancelled() && e.getBedEnterResult().equals(BedEnterResult.OK) && plugin.getConfig().getBoolean("BedPercentage")) {
			int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*minPlayerPercent);
			
			SleepingPlayers.add(p);
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED + SleepingPlayers.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " sind im Bett!");
			
			if(SleepingPlayers.size() >= min)
			{
				Bukkit.getWorlds().get(0).setTime(0);
				Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug geschlafen.");
				SleepingPlayers.clear();
			}
		}
	}
	
	@EventHandler
	public void onBedEnter(PlayerBedLeaveEvent e) {	
		
		Player p = e.getPlayer();
		
		if(SleepingPlayers.contains(p) && plugin.getConfig().getBoolean("BedPercentage")) {
			SleepingPlayers.remove(p);
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED + SleepingPlayers.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " sind im Bett!");
		}
	}
	
	
	// Edit Config
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bp") && sender.hasPermission("tnx.bp")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("true")) {
					plugin.getConfig().set("BedPercentage", true);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Bed Percentage night skip enabled!");
					
					return true;
				}else if(args[0].equalsIgnoreCase("false")) {
					plugin.getConfig().set("BedPercentage", false);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Bed Percentage night skip disabled!");
					
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else {
			sender.sendMessage("&4No Permissions!");
			return false;
		}
	}
}
