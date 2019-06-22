package Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import Main.Main;

public class BossBroadcast implements CommandExecutor {
	
	Main plugin;

	public BossBroadcast(Main main) {
		this.plugin = main;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("bb") && sender.hasPermission("tnx.bb")) {
			if(args.length != 0) {
				String msg = "";
				for(String a : args) {
					msg = msg + " " + a;
				}
		
				BossBar bar = Bukkit.createBossBar(ChatColor.GOLD + "" + ChatColor.BOLD + msg, BarColor.RED, BarStyle.SOLID);
				bar.setProgress(0.0);
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					bar.addPlayer(p);
				}
				
				new BukkitRunnable() {
					int i = 0;
					
					@Override
					public void run() {
						i++;
						bar.setProgress((double)i/100);
						
						if(i >= 100) {
							bar.removeAll();
							cancel();
						}
						
					}
				}.runTaskTimer(plugin, 0, 12);
				
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

}
