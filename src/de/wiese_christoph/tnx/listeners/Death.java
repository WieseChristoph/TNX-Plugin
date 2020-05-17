package de.wiese_christoph.tnx.listeners;


import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class Death implements Listener {
	
	Main plugin;
	
	public Death(Main plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Entity pe = (Entity)e.getEntity();
		if(pe instanceof Player) {
			final Player p = (Player)pe;
			final Location pl = p.getLocation();
			
			// Play death sound to everyone
			if(plugin.getConfig().getBoolean("Death.Sound") == true) {
				for (Player player : Bukkit.getOnlinePlayers())
				{
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VEX_DEATH, 3.0F, 0.5F);
				}
			}
			
			// Write Coordinates of death in chat
			if(plugin.getConfig().getBoolean("Death.Coordinates") == true) {
				Bukkit.broadcastMessage(Main.Name + ChatColor.GOLD + "Todespunkt von " + ChatColor.RED +p.getDisplayName() + "§6: X: §c" + Math.round(pl.getX()) + "§6 Y: §c" + Math.round(pl.getY()) + "§6 Z: §c" + Math.round(pl.getZ()));
			}
			
			// Spawn firework on death point
			if(plugin.getConfig().getBoolean("Death.Firework") == true) {
				new BukkitRunnable() {
					int i = 0;
				    public void run() {
				    	i++;
				    	
				    	Firework f = (Firework) p.getWorld().spawn(pl, Firework.class);
				   	     
				    	FireworkMeta fm = f.getFireworkMeta();
				    	fm.addEffect(FireworkEffect.builder()
			    		    .flicker(false)
			    		    .trail(true)
			    		    .with(Type.BALL_LARGE)
			    		    .withColor(Color.ORANGE)
			    		    .withColor(Color.BLUE)
			    		    .withFade(Color.BLUE)
			    		    .build());
				    	fm.setPower(2);
				    	f.setFireworkMeta(fm);
				    	
				    	// cancel if 100 fireworks have blown up
				    	if(i >= 100) {
				    		cancel();
				    	}
				    	// cancel if a player is in reach
				    	else if(p.getWorld().getNearbyEntities(pl, 15, 15, 15).size() > 0) {
				        	Collection<Entity> entities = p.getWorld().getNearbyEntities(pl, 5, 5, 5);
				        	for(Entity entity : entities) {
				        		if(entity instanceof Player && !entity.isDead()) {
				        			cancel();
				        		}
				        	}
				        }
				    }
				}.runTaskTimer(plugin, 0, 200);
			}
		}
	}
	
	
	@EventHandler
	public void onFireworkExplode(EntityExplodeEvent e) throws InterruptedException{
		
		//check if explosion is from firework
		if(plugin.getConfig().getBoolean("Death.Firework") == true && e.getEntity() instanceof Firework) {
			// cancel damage to items from fireworks
			e.blockList().clear();
		}
		
	}
	
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		
		//check if explosion is from firework
		if(plugin.getConfig().getBoolean("Death.Firework") == true && e.getEntity() instanceof Firework) {
			// cancel the damage
			e.setCancelled(true);
		}
	}
	
}
	
