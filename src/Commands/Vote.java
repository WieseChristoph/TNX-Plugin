package Commands;

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
import Main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Vote implements CommandExecutor, Listener{
	
	//Create list of players who voted
	static ArrayList<String> onplD = new ArrayList<String>();
	static ArrayList<String> onplN = new ArrayList<String>();
	static ArrayList<String> onplC = new ArrayList<String>();
	static ArrayList<String> onplR = new ArrayList<String>();
	
	//get the world
	static World world = Bukkit.getServer().getWorld("world"); 
	
	
	//command executor
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("vote")) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if(p.hasPermission("tnx.vote")) {
					if(args.length > 0) {
						int min = Bukkit.getOnlinePlayers().size()/2;
						
						//Time
						//Day
						if(args[0].equalsIgnoreCase("day")) {
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
									return true;
								}
								return true;
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
						//Night
						}else if (args[0].equalsIgnoreCase("night")) {
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
									onplN.clear();
									return true;
								}
								return true;					
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
							
						//Weather
						//Clear
						}else if (args[0].equalsIgnoreCase("clear")) {
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
									return true;
								}
								return true;
							}else {
								p.sendMessage(Main.Name + "§4Du hast schon gevoted!");
								return true;
							}
							
						//Rain
						}else if (args[0].equalsIgnoreCase("rain")) {
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
									onplR.clear();
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
		
		return false;
	}
	
	//if a player leaves, he gets removed from the list
	 @EventHandler
	 public void onPlayerQuit(PlayerQuitEvent event)
     {
		Player lp = (Player)event.getPlayer();
		int min = Bukkit.getOnlinePlayers().size()/2;
		
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