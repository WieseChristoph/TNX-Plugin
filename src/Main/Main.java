package Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import Commands.BossBroadcast;
import Commands.Rocket;
import Commands.Vote;
import Event.CreeperExplosion;
import Event.Death;


public class Main extends JavaPlugin{
	
	public static String Name = "§8[§5TNX§8] ";

	@Override
	public void onEnable() {
		
		initConfig();
		
		//Init Commands
		getCommand("vote").setExecutor(new Vote());
		getCommand("rocket").setExecutor(new Rocket(this));
		getCommand("ce").setExecutor(new CreeperExplosion(this));
		getCommand("bb").setExecutor(new BossBroadcast(this));
		getServer().getPluginManager().registerEvents(new Vote(), this);
		
		//Init Events
		getServer().getPluginManager().registerEvents(new Death(this), this);
		getServer().getPluginManager().registerEvents(new CreeperExplosion(this), this);
		
		
		//Save and Broadcast at restart
		Functions.scheduleRepeatAtTime(this, new Runnable()
	    {
	        public void run()
	        {
	        	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-all");
	            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "bb Restart in 1 Min");
	        }
	    }, 12);
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
		getConfig().addDefault("CreeperExplosion", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	

}
