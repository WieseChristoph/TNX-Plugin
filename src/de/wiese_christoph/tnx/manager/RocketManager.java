// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.manager;

import org.bukkit.plugin.Plugin;
import de.wiese_christoph.tnx.Main;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;

public class RocketManager
{
    public void start(final Player p) {
        final Location pl = p.getLocation();
        p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000, 1));
        p.sendTitle(ChatColor.DARK_RED + "RIP", ChatColor.GOLD + p.getDisplayName(), 2, 35, 2);
        new BukkitRunnable() {
            public void run() {
                pl.add(0.0, 1.0, 0.0);
                p.teleport(pl);
                pl.getWorld().spawnParticle(Particle.DRAGON_BREATH, pl, 300);
                if (pl.getY() >= 110.0) {
                    pl.getWorld().createExplosion(pl, 6.0f);
                    p.setHealth(0.0);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 0L);
    }
}
