package de.wiese_christoph.tnxutils;

import de.wiese_christoph.tnxutils.managers.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import de.wiese_christoph.tnxutils.commands.BossBroadcastCmd;
import de.wiese_christoph.tnxutils.commands.CreeperExplosionProtectionCmd;
import de.wiese_christoph.tnxutils.commands.DeathInventory;
import de.wiese_christoph.tnxutils.commands.PollCmd;
import de.wiese_christoph.tnxutils.commands.RocketCmd;
import de.wiese_christoph.tnxutils.commands.TimeWeatherVoteCmd;
import de.wiese_christoph.tnxutils.commands.TabComplete;
import de.wiese_christoph.tnxutils.listeners.CreeperExplosionListen;
import de.wiese_christoph.tnxutils.listeners.DeathListen;
import de.wiese_christoph.tnxutils.listeners.PollListen;
import de.wiese_christoph.tnxutils.listeners.TimeWeatherVoteListen;


public class TnxUtils extends JavaPlugin {
	public static String Name = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "TNX-Utils" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;
	private BossBroadcastManager bossBroadcastManager;
	private DeathManager deathManager;
	private PollManager pollManager;
	private RocketManager rocketManager;
	private TimeWeatherVoteManager timeWeatherVoteManager;
	
	@Override
	public void onEnable() {
		initConfig();

		bossBroadcastManager = new BossBroadcastManager(this);
		deathManager = new DeathManager(this);
		pollManager = new PollManager(this, bossBroadcastManager);
		rocketManager = new RocketManager(this);
		timeWeatherVoteManager = new TimeWeatherVoteManager(0.75f, 320);
		
		// Init commands
		getCommand("vote").setExecutor(new TimeWeatherVoteCmd(this, timeWeatherVoteManager));
		getCommand("rocket").setExecutor(new RocketCmd(this, rocketManager));
		getCommand("cep").setExecutor(new CreeperExplosionProtectionCmd(this));
		getCommand("twv").setExecutor(new TimeWeatherVoteCmd(this, timeWeatherVoteManager));
		getCommand("bb").setExecutor(new BossBroadcastCmd(this, bossBroadcastManager));
		getCommand("poll").setExecutor(new PollCmd(this, pollManager));
        getCommand("di").setExecutor(new DeathInventory(this, deathManager));
		
		// Tab completer
		getCommand("vote").setTabCompleter(new TabComplete());
		getCommand("cep").setTabCompleter(new TabComplete());
		getCommand("twv").setTabCompleter(new TabComplete());
		
		// Init events
		getServer().getPluginManager().registerEvents(new TimeWeatherVoteListen(timeWeatherVoteManager), this);
		getServer().getPluginManager().registerEvents(new DeathListen(this, deathManager), this);
		getServer().getPluginManager().registerEvents(new CreeperExplosionListen(this), this);
		getServer().getPluginManager().registerEvents(new PollListen(this, pollManager), this);
	}

	@Override
	public void onDisable() {
	}
	
	private void initConfig() {
		reloadConfig();
		getConfig().addDefault("CreeperExplosionProtection.enabled", true);
		getConfig().addDefault("CreeperExplosionProtection.SlowRegen", true);
		getConfig().addDefault("TimeWeatherVote.enabled", true);
		getConfig().addDefault("Death.Sound", true);
		getConfig().addDefault("Death.Coordinates", true);
		getConfig().addDefault("Death.Firework", true);
        getConfig().addDefault("Death.Inventory", true);
		getConfig().addDefault("Poll.excludePollStarter", true);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
