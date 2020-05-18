package de.wiese_christoph.tnx.manager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;

public class RocketManager {
	
	
	public void start(Player p) {
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
		}.runTaskTimer(Main.getInstance(), 0, 0);
	}
}
