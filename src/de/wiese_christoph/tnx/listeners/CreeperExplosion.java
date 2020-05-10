package de.wiese_christoph.tnx.listeners;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;

public class CreeperExplosion implements Listener, CommandExecutor {
	
	Main plugin;

	public CreeperExplosion(Main main) {
		this.plugin = main;
	}
	
	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent e) throws InterruptedException{
		
		//check if explosion is from creeper
		if(e.getEntity() instanceof Creeper && plugin.getConfig().getBoolean("CreeperExplosion.enabled") == true) {
			
			if(plugin.getConfig().getBoolean("CreeperExplosion.SlowRegen") == true) {
			
			//initialize block state list
			ArrayList<BlockState> state = new ArrayList<>();
			
			//remove chest from list, to prevent explosion of them
			for(Block b : e.blockList().toArray(new Block[e.blockList().size()])) {	
				if(b.getType() == Material.CHEST || b.getType() == Material.FURNACE) {
					e.blockList().remove(b);
				}
			}
			
			//change the blocks to air an set the state into the list
			for(Block b : e.blockList().toArray(new Block[e.blockList().size()])) {	
				state.add(b.getState());
				b.setType(Material.AIR);
			}
			
			//change the state of each block
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					if(state.size() > 0) {
						state.get(0).update(true, false);
			            state.remove(0);
					}else {
						this.cancel();
					}
				}
			}.runTaskTimer(plugin, 100, 2);
				
		} else {
			e.blockList().clear();
		}
			
		}
		
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ce") && sender.hasPermission("tnx.ce")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("true")) {
					plugin.getConfig().set("CreeperExplosion.enabled", true);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Creeper Explosion Protection enabled!");
					
					return true;
				}else if(args[0].equalsIgnoreCase("false")) {
					plugin.getConfig().set("CreeperExplosion.enabled", false);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Creeper Explosion Protection disabled!");
					
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
