// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import de.wiese_christoph.tnx.objects.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import de.wiese_christoph.tnx.manager.StatsManager;
import de.wiese_christoph.tnx.Main;
import org.bukkit.event.Listener;

public class StatsListen implements Listener
{
    Main plugin;
    StatsManager statMng;
    
    public StatsListen(final Main plugin) {
        this.statMng = new StatsManager();
        this.plugin = Main.getInstance();
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        final Entity pe = (Entity)e.getEntity();
        if (pe instanceof Player) {
            final Player p = (Player)pe;
            if (this.statMng.cache.containsKey(p.getUniqueId().toString())) {
                this.statMng.cache.get(p.getUniqueId().toString()).addDeath();
            }
            else {
                final PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
                nPlayer.addDeath();
                this.statMng.cache.put(p.getUniqueId().toString(), nPlayer);
            }
            if (p.getKiller() instanceof Player && p.getKiller() != p) {
                final Player killer = p.getKiller();
                if (this.statMng.cache.containsKey(killer.getUniqueId().toString())) {
                    this.statMng.cache.get(killer.getUniqueId().toString()).addPvpKills();
                }
                else {
                    final PlayerStats nPlayer2 = new PlayerStats(killer.getDisplayName(), killer.getUniqueId().toString());
                    nPlayer2.addPvpKills();
                    this.statMng.cache.put(killer.getUniqueId().toString(), nPlayer2);
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            final LivingEntity entity = e.getEntity();
            if (entity.getKiller() instanceof Player) {
                final Player p = entity.getKiller();
                if (this.statMng.cache.containsKey(p.getUniqueId().toString())) {
                    this.statMng.cache.get(p.getUniqueId().toString()).addPveKills();
                }
                else {
                    final PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
                    nPlayer.addPveKills();
                    this.statMng.cache.put(p.getUniqueId().toString(), nPlayer);
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        if (this.statMng.cache.containsKey(p.getUniqueId().toString())) {
            this.statMng.cache.get(p.getUniqueId().toString()).addBrokenBlocks();
        }
        else {
            final PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
            nPlayer.addBrokenBlocks();
            this.statMng.cache.put(p.getUniqueId().toString(), nPlayer);
        }
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (this.statMng.cache.containsKey(p.getUniqueId().toString())) {
            this.statMng.cache.get(p.getUniqueId().toString()).addPlacedBlocks();
        }
        else {
            final PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
            nPlayer.addPlacedBlocks();
            this.statMng.cache.put(p.getUniqueId().toString(), nPlayer);
        }
    }
}
