package de.wiese_christoph.tnx;

import org.bukkit.plugin.java.JavaPlugin;

import de.wiese_christoph.tnx.commands.BossBroadcastCmd;
import de.wiese_christoph.tnx.commands.CreeperExplosionCmd;
import de.wiese_christoph.tnx.commands.PollCmd;
import de.wiese_christoph.tnx.commands.RocketCmd;
import de.wiese_christoph.tnx.commands.SleepCmd;
import de.wiese_christoph.tnx.commands.VoteCmd;
import de.wiese_christoph.tnx.commands.TabComplete;
import de.wiese_christoph.tnx.listeners.CreeperExplosionListen;
import de.wiese_christoph.tnx.listeners.DeathListen;
import de.wiese_christoph.tnx.listeners.EssentialsListener;
import de.wiese_christoph.tnx.listeners.PollListen;
import de.wiese_christoph.tnx.listeners.SleepListen;
import de.wiese_christoph.tnx.listeners.StatsListen;
import de.wiese_christoph.tnx.listeners.VoteListen;


public class Main extends JavaPlugin{
	
	public static String Name = "§8[§5TNX§8] ";
	
	private static Main instance;
	public static Main getInstance(){
	    return instance;
	}
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		initConfig();
		
		//Init Commands
		getCommand("vote").setExecutor(new VoteCmd(this));
		getCommand("tv").setExecutor(new VoteCmd(this));
		getCommand("rocket").setExecutor(new RocketCmd(this));
		getCommand("ce").setExecutor(new CreeperExplosionCmd(this));
		getCommand("bb").setExecutor(new BossBroadcastCmd(this));
		getCommand("bp").setExecutor(new SleepCmd(this));
		getCommand("volksabstimmung").setExecutor(new PollCmd(this));
		
		//TabCompleter
		getCommand("vote").setTabCompleter(new TabComplete());
		getCommand("ce").setTabCompleter(new TabComplete());
		
		//Init Events
		getServer().getPluginManager().registerEvents(new VoteListen(), this);
		getServer().getPluginManager().registerEvents(new DeathListen(this), this);
		getServer().getPluginManager().registerEvents(new CreeperExplosionListen(this), this);
		getServer().getPluginManager().registerEvents(new SleepListen(this), this);
		getServer().getPluginManager().registerEvents(new StatsListen(this), this);
		getServer().getPluginManager().registerEvents(new PollListen(this), this);
		
		if(getServer().getPluginManager().getPlugin("Essentials")!=null) {
			getServer().getPluginManager().registerEvents(new EssentialsListener(), this);
			System.out.println(Name + "§6Essentials found and loaded!");
		} else System.out.println(Name + "§6Essentials not found! Features wont be used!");
		
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
		getConfig().addDefault("Poll.excludePollStarter", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	

}
