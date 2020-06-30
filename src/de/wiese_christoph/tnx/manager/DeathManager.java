package de.wiese_christoph.tnx.manager;

import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.entity.Entity;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Location;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import de.wiese_christoph.tnx.Main;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class DeathManager
{
    private static HashMap<String, ItemStack[]> invList;
    public static String invName;
    
    static {
        DeathManager.invList = new HashMap<String, ItemStack[]>();
        DeathManager.invName = "§4Death Inventory";
    }
    
    public static void addInv(final Player p) {
        final String playerName = p.getName();
        final ArrayList<ItemStack> itemsList = new ArrayList<ItemStack>(Arrays.asList(p.getInventory().getContents()));
        itemsList.addAll(Arrays.asList(p.getInventory().getExtraContents()));
        final ItemStack[] itemsArray = itemsList.toArray(new ItemStack[0]);
        if (DeathManager.invList.containsKey(playerName)) {
            DeathManager.invList.replace(playerName, itemsArray);
        }
        else {
            DeathManager.invList.put(playerName, itemsArray);
        }
    }
    
    public static void showInv(final Player reqestPlayer, final Player targetPlayer) {
        if (DeathManager.invList.containsKey(targetPlayer.getName())) {
            final Inventory targetInv = Bukkit.createInventory((InventoryHolder)null, 45, DeathManager.invName);
            targetInv.setContents((ItemStack[])DeathManager.invList.get(targetPlayer.getName()));
            reqestPlayer.openInventory(targetInv);
        }
        else {
            reqestPlayer.sendMessage(String.valueOf(Main.Name) + "§4Kein Inventar gespeichert!");
        }
    }
    
    public void deathSound() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VEX_DEATH, 3.0f, 0.5f);
        }
    }
    
    public void deathCoords(final Player p) {
        final Location pl = p.getLocation();
        Bukkit.broadcastMessage(String.valueOf(Main.Name) + ChatColor.GOLD + "Todespunkt von " + ChatColor.RED + p.getDisplayName() + "§6: X: §c" + Math.round(pl.getX()) + "§6 Y: §c" + Math.round(pl.getY()) + "§6 Z: §c" + Math.round(pl.getZ()));
    }
    
    public void deathRockets(final Player p) {
        final Location pl = p.getLocation();
        new BukkitRunnable() {
            int i = 0;
            
            public void run() {
                ++this.i;
                final Firework f = (Firework)pl.getWorld().spawn(pl, (Class)Firework.class);
                final FireworkMeta fm = f.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.ORANGE).withColor(Color.BLUE).withFade(Color.BLUE).build());
                fm.setPower(2);
                f.setFireworkMeta(fm);
                if (this.i >= 30) {
                    this.cancel();
                }
                else if (pl.getWorld().getNearbyEntities(pl, 15.0, 15.0, 15.0).size() > 0) {
                    final Collection<Entity> entities = (Collection<Entity>)pl.getWorld().getNearbyEntities(pl, 10.0, 10.0, 10.0);
                    for (final Entity entity : entities) {
                        if (entity instanceof Player && !entity.isDead()) {
                            this.cancel();
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 40L);
    }
}
