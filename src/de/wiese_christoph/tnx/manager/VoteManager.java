// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.manager;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import java.time.temporal.TemporalUnit;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import net.md_5.bungee.api.ChatColor;
import de.wiese_christoph.tnx.Main;
import java.time.chrono.ChronoLocalDateTime;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.World;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VoteManager
{
    public static float minPlayerPercent;
    public static ArrayList<String> onplD;
    public static ArrayList<String> onplN;
    public static ArrayList<String> onplC;
    public static ArrayList<String> onplR;
    private static LocalDateTime lastVoteTime;
    private static LocalDateTime lastVoteWeather;
    private static World world;
    public static int afkPlayers;
    
    static {
        VoteManager.minPlayerPercent = 0.666f;
        VoteManager.onplD = new ArrayList<String>();
        VoteManager.onplN = new ArrayList<String>();
        VoteManager.onplC = new ArrayList<String>();
        VoteManager.onplR = new ArrayList<String>();
        VoteManager.lastVoteTime = LocalDateTime.now().minusMinutes(2L);
        VoteManager.lastVoteWeather = LocalDateTime.now().minusMinutes(2L);
        VoteManager.world = Bukkit.getServer().getWorld("world");
        VoteManager.afkPlayers = 0;
    }
    
    public static Boolean voteDay(final Player p) {
        if (LocalDateTime.now().isBefore(VoteManager.lastVoteTime.plusMinutes(2L))) {
            p.sendMessage(String.valueOf(Main.Name) + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(VoteManager.lastVoteTime.plusMinutes(2L), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
            return true;
        }
        if (!VoteManager.onplD.contains(p.getDisplayName())) {
            VoteManager.onplD.add(p.getDisplayName());
            final TextComponent message = new TextComponent(String.valueOf(Main.Name) + ChatColor.RED + VoteManager.onplD.size() + ChatColor.GOLD + " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " haben f\u00fcr Tag gevoted! - ");
            final TextComponent vc = new TextComponent("/vote day");
            vc.setColor(ChatColor.LIGHT_PURPLE);
            vc.setUnderlined(Boolean.valueOf(true));
            vc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote day"));
            vc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Click to Vote!").create()));
            Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.RED + p.getDisplayName() + "§6 der Spacko hat f\u00fcr Tag gevoted!");
            p.getServer().spigot().broadcast(new BaseComponent[] { (BaseComponent)message, (BaseComponent)vc });
            checkVotePass();
            return true;
        }
        p.sendMessage(String.valueOf(Main.Name) + "§4Du hast schon gevoted!");
        return true;
    }
    
    public static Boolean voteNight(final Player p) {
        if (LocalDateTime.now().isBefore(VoteManager.lastVoteTime.plusMinutes(2L))) {
            p.sendMessage(String.valueOf(Main.Name) + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(VoteManager.lastVoteTime.plusMinutes(2L), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
            return true;
        }
        if (!VoteManager.onplN.contains(p.getDisplayName())) {
            VoteManager.onplN.add(p.getDisplayName());
            final TextComponent message = new TextComponent(String.valueOf(Main.Name) + ChatColor.RED + VoteManager.onplN.size() + ChatColor.GOLD + " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " haben f\u00fcr Nacht gevoted! - ");
            final TextComponent vc = new TextComponent("/vote night");
            vc.setColor(ChatColor.LIGHT_PURPLE);
            vc.setUnderlined(Boolean.valueOf(true));
            vc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote night"));
            vc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Click to Vote!").create()));
            Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.RED + p.getDisplayName() + "§6 das Kellerkind hat f\u00fcr Nacht gevoted!");
            p.getServer().spigot().broadcast(new BaseComponent[] { (BaseComponent)message, (BaseComponent)vc });
            checkVotePass();
            return true;
        }
        p.sendMessage(String.valueOf(Main.Name) + "§4Du hast schon gevoted!");
        return true;
    }
    
    public static Boolean voteClear(final Player p) {
        if (LocalDateTime.now().isBefore(VoteManager.lastVoteWeather.plusMinutes(2L))) {
            p.sendMessage(String.valueOf(Main.Name) + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(VoteManager.lastVoteWeather.plusMinutes(2L), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
            return true;
        }
        if (!VoteManager.onplC.contains(p.getDisplayName())) {
            VoteManager.onplC.add(p.getDisplayName());
            final TextComponent message = new TextComponent(String.valueOf(Main.Name) + ChatColor.RED + VoteManager.onplC.size() + ChatColor.GOLD + " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " haben f\u00fcr klaren Himmel gevoted! - ");
            final TextComponent vc = new TextComponent("/vote clear");
            vc.setColor(ChatColor.LIGHT_PURPLE);
            vc.setUnderlined(Boolean.valueOf(true));
            vc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote clear"));
            vc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Click to Vote!").create()));
            Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.RED + p.getDisplayName() + "§6 hat f\u00fcr klaren Himmel gevoted!");
            p.getServer().spigot().broadcast(new BaseComponent[] { (BaseComponent)message, (BaseComponent)vc });
            checkVotePass();
            return true;
        }
        p.sendMessage(String.valueOf(Main.Name) + "§4Du hast schon gevoted!");
        return true;
    }
    
    public static Boolean voteRain(final Player p) {
        if (LocalDateTime.now().isBefore(VoteManager.lastVoteWeather.plusMinutes(2L))) {
            p.sendMessage(String.valueOf(Main.Name) + ChatColor.RED + "Cooldown: " + ChatColor.GOLD + LocalDateTime.now().until(VoteManager.lastVoteWeather.plusMinutes(2L), ChronoUnit.SECONDS) + ChatColor.RED + " Seconds");
            return true;
        }
        if (!VoteManager.onplR.contains(p.getDisplayName())) {
            VoteManager.onplR.add(p.getDisplayName());
            final TextComponent message = new TextComponent(String.valueOf(Main.Name) + ChatColor.RED + VoteManager.onplR.size() + ChatColor.GOLD + " von " + ChatColor.RED + Bukkit.getOnlinePlayers().size() + ChatColor.GOLD + " haben f\u00fcr Regen gevoted! - ");
            final TextComponent vc = new TextComponent("/vote rain");
            vc.setColor(ChatColor.LIGHT_PURPLE);
            vc.setUnderlined(Boolean.valueOf(true));
            vc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote rain"));
            vc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Click to Vote!").create()));
            Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.RED + p.getDisplayName() + "§6 das Kellerkind hat f\u00fcr Regen gevoted!");
            p.getServer().spigot().broadcast(new BaseComponent[] { (BaseComponent)message, (BaseComponent)vc });
            checkVotePass();
            return true;
        }
        p.sendMessage(String.valueOf(Main.Name) + "§4Du hast schon gevoted!");
        return true;
    }
    
    public static void setDay() {
        VoteManager.world.setTime(0L);
        Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
        VoteManager.onplD.clear();
        VoteManager.onplN.clear();
        VoteManager.lastVoteTime = LocalDateTime.now();
    }
    
    public static void setNight() {
        VoteManager.world.setTime(15000L);
        Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
        VoteManager.onplD.clear();
        VoteManager.onplN.clear();
        VoteManager.lastVoteTime = LocalDateTime.now();
    }
    
    public static void setClear() {
        VoteManager.world.setThundering(false);
        VoteManager.world.setStorm(false);
        Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
        VoteManager.onplC.clear();
        VoteManager.onplR.clear();
        VoteManager.lastVoteWeather = LocalDateTime.now();
    }
    
    public static void setRain() {
        VoteManager.world.setThundering(true);
        VoteManager.world.setStorm(true);
        Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Anscheinend haben genug Deppen gevoted.");
        VoteManager.onplC.clear();
        VoteManager.onplR.clear();
        VoteManager.lastVoteWeather = LocalDateTime.now();
    }
    
    public static void checkVotePass() {
        final int onlinePlayers = Bukkit.getOnlinePlayers().size();
        final int min = Math.round((onlinePlayers - VoteManager.afkPlayers) * VoteManager.minPlayerPercent);
        if (VoteManager.onplD.size() >= min && onlinePlayers != 0) {
            setDay();
        }
        else if (VoteManager.onplN.size() >= min && onlinePlayers != 0) {
            setNight();
        }
        else if (VoteManager.onplC.size() >= min && onlinePlayers != 0) {
            setClear();
        }
        else if (VoteManager.onplR.size() >= min && onlinePlayers != 0) {
            setRain();
        }
    }
}
