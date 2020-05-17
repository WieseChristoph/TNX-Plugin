package de.wiese_christoph.tnx;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.wiese_christoph.tnx.commands.BossBroadcast;
import de.wiese_christoph.tnx.commands.Rocket;
import de.wiese_christoph.tnx.commands.Vote;
import de.wiese_christoph.tnx.commands.TabComplete;
import de.wiese_christoph.tnx.listeners.CreeperExplosion;
import de.wiese_christoph.tnx.listeners.Death;
import de.wiese_christoph.tnx.listeners.Sleep;
import de.wiese_christoph.tnx.listeners.Stats;
import de.wiese_christoph.tnx.utils.Functions;


public class Main extends JavaPlugin{
	
	public static String Name = "§8[§5TNX§8] ";

	@Override
	public void onEnable() {
		
		initConfig();
		
		//Init Commands
		getCommand("vote").setExecutor(new Vote(this));
		getCommand("tv").setExecutor(new Vote(this));
		getCommand("rocket").setExecutor(new Rocket(this));
		getCommand("ce").setExecutor(new CreeperExplosion(this));
		getCommand("bb").setExecutor(new BossBroadcast(this));
		getServer().getPluginManager().registerEvents(new Vote(this), this);
		getCommand("bp").setExecutor(new Sleep(this));
		
		//TabCompleter
		getCommand("vote").setTabCompleter(new TabComplete());
		getCommand("ce").setTabCompleter(new TabComplete());
		
		//Init Events
		getServer().getPluginManager().registerEvents(new Death(this), this);
		getServer().getPluginManager().registerEvents(new CreeperExplosion(this), this);
		getServer().getPluginManager().registerEvents(new Sleep(this), this);
		getServer().getPluginManager().registerEvents(new Stats(this), this);
		
		
		//Save and Broadcast at restart
		Functions.scheduleRepeatAtTime(this, new Runnable()
	    {
	        public void run()
	        {
	        	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-all");
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bb Restart in 1 Min");
	        }
	    }, 0);
		
	}

	@Override
	public void onDisable() {
	}
	
	
	private void initConfig() {
		reloadConfig();
		getConfig().addDefault("CreeperExplosion.enabled", true);
		getConfig().addDefault("BedPercentage.enabled", true);
		getConfig().addDefault("TimeVote.enabled", true);
		getConfig().addDefault("CreeperExplosion.SlowRegen", true);
		getConfig().addDefault("DB.username", "");
		getConfig().addDefault("DB.password", "");
		getConfig().addDefault("DB.databaseName", "");
		getConfig().addDefault("Death.Sound", true);
		getConfig().addDefault("Death.Coordinates", true);
		getConfig().addDefault("Death.Firework", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	

}
