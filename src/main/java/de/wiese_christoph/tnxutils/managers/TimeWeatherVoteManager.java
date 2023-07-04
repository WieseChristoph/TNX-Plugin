package de.wiese_christoph.tnxutils.managers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.wiese_christoph.tnxutils.TnxUtils;

public class TimeWeatherVoteManager {
	public enum Vote {DAY, NIGHT, CLEAR, RAIN}
	private final float minPlayerPercent;
	private int cooldownSeconds;
	private HashMap<Vote, ArrayList<String>> votes = new HashMap<>();
	private LocalDateTime lastVoteTime;
	private LocalDateTime lastVoteWeather;

	private World world = Bukkit.getServer().getWorld("world");

	public TimeWeatherVoteManager(float minPlayerPercent, int cooldownSeconds) {
		this.minPlayerPercent = minPlayerPercent;
		this.cooldownSeconds = cooldownSeconds;
		lastVoteTime = LocalDateTime.now().minusSeconds(cooldownSeconds);
		lastVoteWeather = LocalDateTime.now().minusSeconds(cooldownSeconds);
		for (Vote v : Vote.values()) {
			votes.put(v, new ArrayList<>());
		}
	}
	
	public void vote(Player p, Vote vote) {
		LocalDateTime lastVote = vote.equals(Vote.DAY) || vote.equals(Vote.NIGHT) ? lastVoteTime.plusSeconds(cooldownSeconds) : lastVoteWeather.plusSeconds(cooldownSeconds);

		// Cooldown
		if(LocalDateTime.now().isBefore(lastVote)){
			p.sendMessage(
					TnxUtils.Name + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(lastVote, ChronoUnit.SECONDS) + ChatColor.RED + " Seconds"
			);
			return;
		}
		
		if(votes.get(vote).contains(p.getDisplayName())) {
			p.sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "You already voted!");
			return;
		}

		votes.get(vote).add(p.getDisplayName());

		// Make clickable Command
		TextComponent message = new TextComponent(
				TnxUtils.Name + ChatColor.RED + votes.get(vote).size() + ChatColor.GOLD+ " of " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD+ " have voted for " + vote.name().toLowerCase() + "! - "
		);
		TextComponent vc = new TextComponent("/vote " + vote.name().toLowerCase());
		vc.setColor(ChatColor.LIGHT_PURPLE);
		vc.setUnderlined(true);
		vc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + vote.name().toLowerCase()));
		vc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatColor.RED + "Click to Vote!" ).create() ) );

		Bukkit.broadcastMessage(TnxUtils.Name + ChatColor.RED +p.getDisplayName() + ChatColor.GOLD + " has voted for " + vote.name().toLowerCase() + "!");
		p.getServer().spigot().broadcast(message, vc);

		checkVotePass();
	}

	public void removeVote(Player player) {
		for (Collection<String> l : votes.values()) {
			l.remove(player.getDisplayName());
		}

		checkVotePass();
	}
	
	private void setTime(Vote time) {
		world.setTime(time == Vote.DAY ? 0 : 15000);
		Bukkit.broadcastMessage(TnxUtils.Name + ChatColor.DARK_GREEN + "Enough people voted. Changing time to " + (time == Vote.DAY ? "day" : "night") + ".");
		votes.get(Vote.DAY).clear();
		votes.get(Vote.NIGHT).clear();
		lastVoteTime = LocalDateTime.now();
	}

	private void setWeather(Vote weather) {
		world.setThundering(weather == Vote.RAIN);
		world.setStorm(weather == Vote.RAIN);
		Bukkit.broadcastMessage(TnxUtils.Name + ChatColor.DARK_GREEN + "Enough people voted. Changing weather to " + (weather == Vote.CLEAR ? "clear" : "rain") + ".");
		votes.get(Vote.CLEAR).clear();
		votes.get(Vote.RAIN).clear();
		lastVoteWeather = LocalDateTime.now();
	}
	
	private void checkVotePass() {
		int onlinePlayers = Bukkit.getOnlinePlayers().size();

		for (Map.Entry<Vote, ArrayList<String>> e : votes.entrySet()) {
			if (onlinePlayers != 0 && e.getValue().size() >= (onlinePlayers * minPlayerPercent)) {
				if (e.getKey().equals(Vote.DAY) || e.getKey().equals(Vote.NIGHT)) setTime(e.getKey());
				else setWeather(e.getKey());
				break;
			}
		}
	}
}
