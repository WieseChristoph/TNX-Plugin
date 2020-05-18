package de.wiese_christoph.tnx.manager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class VoteManager {
	
	// minimum players to vote
	public static float minPlayerPercent = 0.75f;
	
	//Create list of players who voted
	public static ArrayList<String> onplD = new ArrayList<String>();
	public static ArrayList<String> onplN = new ArrayList<String>();
	public static ArrayList<String> onplC = new ArrayList<String>();
	public static ArrayList<String> onplR = new ArrayList<String>();
	
	
	private static LocalDateTime lastVoteTime = LocalDateTime.now().minusMinutes(2);
	private static LocalDateTime lastVoteWeather = LocalDateTime.now().minusMinutes(2);
	
	//get the world
	private static World world = Bukkit.getServer().getWorld("world");
	
	
	public static Boolean voteDay(Player p) {
		int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*minPlayerPercent);
		
		// 2 Min Cooldown
		if(LocalDateTime.now().isBefore(lastVoteTime.plusMinutes(2))){
			p.sendMessage(Main.Name + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(lastVoteTime.plusMinutes(2), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
			return true;
		}
		
		if(!onplD.contains(p.getDisplayName())) {
			onplD.add(p.getDisplayName());
			
			//Make clickable Command
			TextComponent message = new TextComponent( Main.Name + ChatColor.RED + onplD.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD+ " haben für Tag gevoted! - " );
			TextComponent vc = new TextComponent("/vote day" );
			vc.setColor(ChatColor.LIGHT_PURPLE);
			vc.setUnderlined(true);
			vc.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/vote day" ) );
			vc.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatColor.RED + "Click to Vote!" ).create() ) );
			
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED +p.getDisplayName() + "§6 der Spacko hat für Tag gevoted!");
			p.getServer().spigot().broadcast(message, vc);
			
			if(onplD.size() >= min) {
				setDay();
				return true;
			}
			return true;
		}else {
			p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
			return true;
		}
	}
	
	
	public static Boolean voteNight(Player p) {
		int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*minPlayerPercent);
		
		// 2 Min Cooldown
		if(LocalDateTime.now().isBefore(lastVoteTime.plusMinutes(2))){
			p.sendMessage(Main.Name + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(lastVoteTime.plusMinutes(2), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
			return true;
		}
		
		if(!onplN.contains(p.getDisplayName())) {
			onplN.add(p.getDisplayName());
			
			//Make clickable Command
			TextComponent message = new TextComponent( Main.Name + ChatColor.RED + onplN.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD+ " haben für Nacht gevoted! - " );
			TextComponent vc = new TextComponent("/vote night" );
			vc.setColor(ChatColor.LIGHT_PURPLE);
			vc.setUnderlined(true);
			vc.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/vote night" ) );
			vc.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatColor.RED + "Click to Vote!" ).create() ) );
			
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED +p.getDisplayName() + "§6 das Kellerkind hat für Nacht gevoted!");
			p.getServer().spigot().broadcast(message, vc);
			

			if(onplN.size() >= min) {
				setNight();
				return true;
			}
			return true;					
		}else {
			p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
			return true;
		}
	}
	
	
	public static Boolean voteClear(Player p) {
		int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*minPlayerPercent);
		
		// 2 Min Cooldown
		if(LocalDateTime.now().isBefore(lastVoteWeather.plusMinutes(2))){
			p.sendMessage(Main.Name + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(lastVoteWeather.plusMinutes(2), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
			return true;
		}
		
		if(!onplC.contains(p.getDisplayName())) {
			onplC.add(p.getDisplayName());
			
			//Make clickable Command
			TextComponent message = new TextComponent( Main.Name + ChatColor.RED + onplC.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD+ " haben für klaren Himmel gevoted! - " );
			TextComponent vc = new TextComponent("/vote clear" );
			vc.setColor(ChatColor.LIGHT_PURPLE);
			vc.setUnderlined(true);
			vc.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/vote clear" ) );
			vc.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatColor.RED + "Click to Vote!" ).create() ) );
			
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED +p.getDisplayName() + "§6 hat für klaren Himmel gevoted!");
			p.getServer().spigot().broadcast(message, vc);
			
			if(onplC.size() >= min) {
				setClear();
				return true;
			}
			return true;
		}else {
			p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
			return true;
		}
	}
	
	
	public static Boolean voteRain(Player p) {
		int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*minPlayerPercent);
		
		// 2 Min Cooldown
		if(LocalDateTime.now().isBefore(lastVoteWeather.plusMinutes(2))){
			p.sendMessage(Main.Name + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(lastVoteWeather.plusMinutes(2), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
			return true;
		}
		
		if(!onplR.contains(p.getDisplayName())) {
			onplR.add(p.getDisplayName());
			
			//Make clickable Command
			TextComponent message = new TextComponent( Main.Name + ChatColor.RED + onplR.size() + ChatColor.GOLD+ " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD+ " haben für Regen gevoted! - " );
			TextComponent vc = new TextComponent("/vote rain" );
			vc.setColor(ChatColor.LIGHT_PURPLE);
			vc.setUnderlined(true);
			vc.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/vote rain" ) );
			vc.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatColor.RED + "Click to Vote!" ).create() ) );
			
			Bukkit.broadcastMessage(Main.Name + ChatColor.RED +p.getDisplayName() + "§6 das Kellerkind hat für Regen gevoted!");
			p.getServer().spigot().broadcast(message, vc);
			
			if(onplR.size() >= min) {
				setRain();
				return true;
			}
			return true;
		}else {
			p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
			return true;
		}
	}
	
	public static void setDay() {
		world.setTime(0);
		Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
		onplD.clear();
		onplN.clear();
		lastVoteTime = LocalDateTime.now();
	}
	
	
	public static void setNight() {
		world.setTime(15000);
		Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
		onplD.clear();
		onplN.clear();
		lastVoteTime = LocalDateTime.now();
	}


	public static void setClear() {
		world.setThundering(false);
		world.setStorm(false);
		Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
		onplC.clear();
		onplR.clear();
		lastVoteWeather = LocalDateTime.now();
	}


	public static void setRain() {
		world.setThundering(true);
		world.setStorm(true);
		Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
		onplC.clear();
		onplR.clear();
		lastVoteWeather = LocalDateTime.now();
	}
}
