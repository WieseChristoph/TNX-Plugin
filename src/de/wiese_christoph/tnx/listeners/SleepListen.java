// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerBedEnterEvent;
import de.wiese_christoph.tnx.manager.SleepManager;
import de.wiese_christoph.tnx.Main;
import org.bukkit.event.Listener;

public class SleepListen implements Listener
{
    Main plugin;
    SleepManager sleepMng;
    
    public SleepListen(final Main plugin) {
        this.sleepMng = new SleepManager();
        this.plugin = Main.getInstance();
    }
    
    @EventHandler
    public void onBedEnter(final PlayerBedEnterEvent e) {
        final Player p = e.getPlayer();
        if (!e.isCancelled() && e.getBedEnterResult().equals((Object)PlayerBedEnterEvent.BedEnterResult.OK) && this.plugin.getConfig().getBoolean("BedPercentage.enabled")) {
            final int min = Math.round(Bukkit.getOnlinePlayers().size() * this.sleepMng.minPlayerPercent);
            this.sleepMng.SleepingPlayers.add(p);
            Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.RED + this.sleepMng.SleepingPlayers.size() + ChatColor.GOLD + " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " sind im Bett!");
            if (this.sleepMng.SleepingPlayers.size() >= min) {
                this.sleepMng.sleepComplete();
            }
        }
    }
    
    @EventHandler
    public void onBedEnter(final PlayerBedLeaveEvent e) {
        final Player p = e.getPlayer();
        if (this.sleepMng.SleepingPlayers.contains(p) && this.plugin.getConfig().getBoolean("BedPercentage.enabled")) {
            this.sleepMng.SleepingPlayers.remove(p);
            Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.RED + this.sleepMng.SleepingPlayers.size() + ChatColor.GOLD + " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " sind im Bett!");
        }
    }
}
