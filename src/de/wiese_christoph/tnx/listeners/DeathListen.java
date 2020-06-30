// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import de.wiese_christoph.tnx.manager.DeathManager;
import de.wiese_christoph.tnx.Main;
import org.bukkit.event.Listener;

public class DeathListen implements Listener
{
    Main plugin;
    DeathManager deathMng;
    
    public DeathListen(final Main plugin) {
        this.deathMng = new DeathManager();
        this.plugin = Main.getInstance();
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        final Entity pe = (Entity)e.getEntity();
        if (pe instanceof Player) {
            final Player p = (Player)pe;
            if (this.plugin.getConfig().getBoolean("Death.Sound")) {
                this.deathMng.deathSound();
            }
            if (this.plugin.getConfig().getBoolean("Death.Coordinates")) {
                this.deathMng.deathCoords(p);
            }
            if (this.plugin.getConfig().getBoolean("Death.Firework")) {
                this.deathMng.deathRockets(p);
            }
            if (this.plugin.getConfig().getBoolean("Death.Inventory")) {
                DeathManager.addInv(p);
            }
        }
    }
    
    @EventHandler
    public void onFireworkExplode(final EntityExplodeEvent e) throws InterruptedException {
        if (this.plugin.getConfig().getBoolean("Death.Firework") && e.getEntity() instanceof Firework) {
            e.blockList().clear();
        }
    }
    
    @EventHandler
    public void onEntityDamageEntity(final EntityDamageByEntityEvent e) {
        if (this.plugin.getConfig().getBoolean("Death.Firework") && e.getEntity() instanceof Firework) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!this.plugin.getConfig().getBoolean("Death.Inventory")) {
            return;
        }
        if (e.getView().getTitle() == DeathManager.invName && !e.getWhoClicked().isOp()) {
            e.setCancelled(true);
        }
    }
}
