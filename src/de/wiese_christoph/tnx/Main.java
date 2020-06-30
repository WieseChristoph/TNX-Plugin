// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx;

import de.wiese_christoph.tnx.listeners.EssentialsListener;
import de.wiese_christoph.tnx.listeners.PollListen;
import de.wiese_christoph.tnx.listeners.StatsListen;
import de.wiese_christoph.tnx.listeners.SleepListen;
import de.wiese_christoph.tnx.listeners.CreeperExplosionListen;
import de.wiese_christoph.tnx.listeners.DeathListen;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import de.wiese_christoph.tnx.listeners.VoteListen;
import org.bukkit.command.TabCompleter;
import de.wiese_christoph.tnx.commands.TabComplete;
import de.wiese_christoph.tnx.commands.DeathInventory;
import de.wiese_christoph.tnx.commands.PollCmd;
import de.wiese_christoph.tnx.commands.SleepCmd;
import de.wiese_christoph.tnx.commands.BossBroadcastCmd;
import de.wiese_christoph.tnx.commands.CreeperExplosionCmd;
import de.wiese_christoph.tnx.commands.RocketCmd;
import org.bukkit.command.CommandExecutor;
import de.wiese_christoph.tnx.commands.VoteCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    public static String Name;
    private static Main instance;
    
    static {
        Main.Name = "§8[§5TNX§8] ";
    }
    
    public static Main getInstance() {
        return Main.instance;
    }
    
    public void onEnable() {
        (Main.instance = this).initConfig();
        this.getCommand("vote").setExecutor((CommandExecutor)new VoteCmd(this));
        this.getCommand("tv").setExecutor((CommandExecutor)new VoteCmd(this));
        this.getCommand("rocket").setExecutor((CommandExecutor)new RocketCmd(this));
        this.getCommand("ce").setExecutor((CommandExecutor)new CreeperExplosionCmd(this));
        this.getCommand("bb").setExecutor((CommandExecutor)new BossBroadcastCmd(this));
        this.getCommand("bp").setExecutor((CommandExecutor)new SleepCmd(this));
        this.getCommand("volksabstimmung").setExecutor((CommandExecutor)new PollCmd(this));
        this.getCommand("di").setExecutor((CommandExecutor)new DeathInventory());
        this.getCommand("vote").setTabCompleter((TabCompleter)new TabComplete());
        this.getCommand("ce").setTabCompleter((TabCompleter)new TabComplete());
        this.getServer().getPluginManager().registerEvents((Listener)new VoteListen(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new DeathListen(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new CreeperExplosionListen(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SleepListen(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new StatsListen(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PollListen(this), (Plugin)this);
        if (this.getServer().getPluginManager().getPlugin("Essentials") != null) {
            this.getServer().getPluginManager().registerEvents((Listener)new EssentialsListener(), (Plugin)this);
            System.out.println(String.valueOf(Main.Name) + "§6Essentials found and loaded!");
        }
        else {
            System.out.println(String.valueOf(Main.Name) + "§6Essentials not found! Features wont be used!");
        }
    }
    
    public void onDisable() {
    }
    
    private void initConfig() {
        this.reloadConfig();
        this.getConfig().addDefault("CreeperExplosion.enabled", (Object)true);
        this.getConfig().addDefault("BedPercentage.enabled", (Object)true);
        this.getConfig().addDefault("TimeVote.enabled", (Object)true);
        this.getConfig().addDefault("CreeperExplosion.SlowRegen", (Object)true);
        this.getConfig().addDefault("DB.username", (Object)"");
        this.getConfig().addDefault("DB.password", (Object)"");
        this.getConfig().addDefault("DB.databaseName", (Object)"");
        this.getConfig().addDefault("Death.Sound", (Object)true);
        this.getConfig().addDefault("Death.Coordinates", (Object)true);
        this.getConfig().addDefault("Death.Firework", (Object)true);
        this.getConfig().addDefault("Death.Inventory", (Object)true);
        this.getConfig().addDefault("Poll.excludePollStarter", (Object)true);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
}
