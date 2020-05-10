package de.wiese_christoph.tnx.commands;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Vote implements CommandExecutor, Listener{
	
	Main plugin;
	
	public Vote(Main plugin) {
		this.plugin = plugin;
	}
	
	// minimum players to vote
	float minPlayerPercent = 0.75f;
	
	//Create list of players who voted
	static ArrayList<String> onplD = new ArrayList<String>();
	static ArrayList<String> onplN = new ArrayList<String>();
	static ArrayList<String> onplC = new ArrayList<String>();
	static ArrayList<String> onplR = new ArrayList<String>();
	
	
	LocalDateTime lastVoteTime = LocalDateTime.now().minusMinutes(2);
	LocalDateTime lastVoteWeather = LocalDateTime.now().minusMinutes(2);
	
	//get the world
	static World world = Bukkit.getServer().getWorld("world"); 
	
	
	//command executor
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("vote")) {
			
			int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*minPlayerPercent);
			
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if(p.hasPermission("tnx.vote")) {
					if(args.length > 0) {
						
						//Time
						//Day
						if(args[0].equalsIgnoreCase("day") && plugin.getConfig().getBoolean("TimeVote.enabled")) {
							
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
									world.setTime(0);
									Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
									onplD.clear();
									onplN.clear();
									lastVoteTime = LocalDateTime.now();
									return true;
								}
								return true;
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
						//Night with 2 min cooldown
						}else if (args[0].equalsIgnoreCase("night") && plugin.getConfig().getBoolean("TimeVote.enabled")) {
							
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
									world.setTime(15000);
									Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
									onplD.clear();
									onplN.clear();
									lastVoteTime = LocalDateTime.now();
									return true;
								}
								return true;					
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
							
						//Weather
						//Clear with 2 min cooldown
						}else if (args[0].equalsIgnoreCase("clear")) {
							
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
									world.setThundering(false);
									world.setStorm(false);
									Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
									onplC.clear();
									onplR.clear();
									lastVoteWeather = LocalDateTime.now();
									return true;
								}
								return true;
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
						//Rain with 2 min cooldown
						}else if (args[0].equalsIgnoreCase("rain")) {
							
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
									world.setThundering(true);
									world.setStorm(true);
									Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
									onplC.clear();
									onplR.clear();
									lastVoteWeather = LocalDateTime.now();
									return true;
								}
								return true;
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
						}else {
							p.sendMessage(Main.Name + "§4Nope.");
							return false;
						}
					}else {
						p.sendMessage(Main.Name + "§4Ja was vote man?!");
						return false;
					}
				}else {
					p.sendMessage(Main.Name + "§4No Permissions!");
					return false;
				}
			}

		}
		

		// Edit Config
		else if(cmd.getName().equalsIgnoreCase("tv") && sender.hasPermission("tnx.tv")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("true")) {
					plugin.getConfig().set("TimeVote.enabled", true);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Time Vote enabled!");
					
					return true;
				}else if(args[0].equalsIgnoreCase("false")) {
					plugin.getConfig().set("TimeVote.enabled", false);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Time Vote disabled!");
					
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
		
		
		return false;
	}
	
	//if a player leaves, he gets removed from the list
	 @EventHandler
	 public void onPlayerQuit(PlayerQuitEvent event)
     {
		Player lp = (Player)event.getPlayer();
		int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*0.75);
		
		//Time
		//Day
		if(onplD.contains(lp.getDisplayName())) {
			onplD.remove(lp.getDisplayName());
			
			if(onplD.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
				world.setTime(0);
				Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
				onplD.clear();
			}
			
		//Night
		}else if(onplN.contains(lp.getDisplayName())) {
			onplN.remove(lp.getDisplayName());
			
			if(onplN.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
				world.setTime(15000);
				Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
				onplN.clear();
			}
			
		//Weather
		//Clear
		}else if(onplC.contains(lp.getDisplayName())) {
			onplC.remove(lp.getDisplayName());
			
			if(onplC.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
				world.setThundering(false);
				Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
				onplC.clear();
			}
		//Rain
		}else if(onplR.contains(lp.getDisplayName())) {
			onplR.remove(lp.getDisplayName());
			
			if(onplR.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
				world.setThundering(true);
				Bukkit.broadcastMessage(Main.Name + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
				onplR.clear();
			}
		}
		
     }
}