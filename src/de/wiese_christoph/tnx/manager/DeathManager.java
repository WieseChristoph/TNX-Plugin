package de.wiese_christoph.tnx.manager;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class DeathManager {
	
	
	public void deathSound() {
		// play sound for everyone
		for (Player player : Bukkit.getOnlinePlayers())
		{
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VEX_DEATH, 3.0F, 0.5F);
		}
	}
	
	// show death coordinates in chat
	public void deathCoords(Player p) {
		final Location pl = p.getLocation();
		Bukkit.broadcastMessage(Main.Name + ChatColor.GOLD + "Todespunkt von " + ChatColor.RED +p.getDisplayName() + "§6: X: §c" + Math.round(pl.getX()) + "§6 Y: §c" + Math.round(pl.getY()) + "§6 Z: §c" + Math.round(pl.getZ()));
	}
	
	
	// spawn rockets at death point
	public void deathRockets(Player p) {
		
		final Location pl = p.getLocation();
		
		new BukkitRunnable() {
			int i = 0;
			@Override
		    public void run() {
		    	i++;
		    	
		    	// setup firework
		    	Firework f = (Firework) pl.getWorld().spawn(pl, Firework.class);
		   	     
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
		    	
		    	// cancel if 30 fireworks have blown up
		    	if(i >= 30) {
		    		cancel();
		    	}
		    	// cancel if a player is in reach
		    	else if(pl.getWorld().getNearbyEntities(pl, 15, 15, 15).size() > 0) {
		        	Collection<Entity> entities = pl.getWorld().getNearbyEntities(pl, 10, 10, 10);
		        	for(Entity entity : entities) {
		        		if(entity instanceof Player && !entity.isDead()) {
		        			cancel();
		        		}
		        	}
		        }
		    }
		}.runTaskTimer(Main.getInstance(), 0, 40);
	}
}
