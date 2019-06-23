package de.wiese_christoph.tnx.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Rocket implements CommandExecutor {
	
	Main plugin;
	
	public Rocket(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		// get command request and check for Permission and if the sender is a Player
		if(cmd.getName().equalsIgnoreCase("rocket")&&sender.hasPermission("tnx.rocket")&&sender instanceof Player) {
			Player p = (Player) sender;
			
			//if no argument is given, the executing player is chosen
			if(args.length == 0) {
				Location pl = p.getLocation();
				p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000, 1));
				p.sendTitle(ChatColor.DARK_RED + "RIP",ChatColor.GOLD + p.getDisplayName(), 2, 35, 2);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						
						pl.add(0,1,0);
						p.teleport(pl);
						pl.getWorld().spawnParticle(Particle.DRAGON_BREATH, pl, 300);
						
						if(pl.getY()>=110) {
							pl.getWorld().createExplosion(pl, 6);
							this.cancel();
						}
						
					}
				}.runTaskTimer(plugin, 0, 0);
			return true;
				
			//If a player is given, the player is used
			}else if(args.length == 1) {
				Player tp = Bukkit.getServer().getPlayer(args[0]);
				if(tp != null) {
					Location tl = tp.getLocation();
					tp.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000, 1));
					tp.sendTitle(ChatColor.DARK_RED + "RIP",ChatColor.GOLD + tp.getDisplayName(), 2, 35, 2);
					
					new BukkitRunnable() {
						@Override
						public void run() {
							
							tl.add(0,1,0);
							tp.teleport(tl);
							tl.getWorld().spawnParticle(Particle.DRAGON_BREATH, tl, 300);
							
							if(tl.getY()>=110) {
								tl.getWorld().createExplosion(tl, 6);
								this.cancel();
							}
							
						}
					}.runTaskTimer(plugin, 0, 0);
				return true;
				
				}else {
					//if the argument isn't a player
					p.sendMessage("§4Argument needs to be a Player");
					return false;
				}
			}else {
				//if there is more than one argument
				p.sendMessage("§4Too many Arguments");
				return false;
			}	
		}else {
			sender.sendMessage("§4Error!");
			return false;
		}
		
	}

}
