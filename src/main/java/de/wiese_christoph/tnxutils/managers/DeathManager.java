package de.wiese_christoph.tnxutils.managers;

import de.wiese_christoph.tnxutils.TnxUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class DeathManager {
    private TnxUtils plugin;
    private HashMap<String, ItemStack[]> invList = new HashMap<>();
    public final String INV_NAME = ChatColor.DARK_RED + "Death Inventory";

    public DeathManager(TnxUtils plugin) {
        this.plugin = plugin;
    }
    
    public void addInv(final Player p) {
        final String playerName = p.getName();

        final ArrayList<ItemStack> itemList = new ArrayList<>();
        itemList.addAll(Arrays.asList(p.getInventory().getContents()));
        itemList.addAll(Arrays.asList(p.getInventory().getExtraContents()));
        final ItemStack[] itemsArray = itemList.toArray(new ItemStack[0]);

        if (invList.containsKey(playerName)) {
            invList.replace(playerName, itemsArray);
        } else {
            invList.put(playerName, itemsArray);
        }
    }
    
    public void showInv(final Player reqestPlayer, final Player targetPlayer) {
        if (invList.containsKey(targetPlayer.getName())) {
            final Inventory targetInv = Bukkit.createInventory(null, 45, INV_NAME);
            targetInv.setContents(invList.get(targetPlayer.getName()));
            reqestPlayer.openInventory(targetInv);
        } else {
            reqestPlayer.sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "No inventory saved!");
        }
    }
    
    public void playDeathSound() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VEX_DEATH, 3.0f, 0.5f);
        }
    }
    
    public void broadcastDeathCoords(final Player p) {
        final Location pl = p.getLocation();
        Bukkit.broadcastMessage(
                TnxUtils.Name + ChatColor.GOLD + "Death point of " + ChatColor.RED + p.getDisplayName()
                        + ChatColor.GOLD + ": X: " + ChatColor.RED + Math.round(pl.getX())
                        + ChatColor.GOLD + " Y: " + ChatColor.RED + Math.round(pl.getY())
                        + ChatColor.GOLD + " Z: " + ChatColor.RED + Math.round(pl.getZ())
        );
    }
    
    public void deathRockets(final Player p, int count) {
        final Location pl = p.getLocation();

        new BukkitRunnable() {
            int i = count;
            
            public void run() {
                if (i < 0) this.cancel();
                else {
                    final Firework f = pl.getWorld().spawn(pl, Firework.class);
                    final FireworkMeta fm = f.getFireworkMeta();
                    fm.addEffect(
                            FireworkEffect.builder()
                                    .flicker(false)
                                    .trail(true)
                                    .with(FireworkEffect.Type.BALL_LARGE)
                                    .withColor(Color.ORANGE)
                                    .withColor(Color.BLUE)
                                    .withFade(Color.BLUE)
                                    .build()
                    );
                    fm.setPower(2);
                    f.setFireworkMeta(fm);

                    // Stop fireworks if a Player is nearby
                    final Collection<Entity> entities = pl.getWorld().getNearbyEntities(pl, 5.0, 5.0, 5.0);
                    for (final Entity entity : entities) {
                        if (entity instanceof Player && !entity.isDead()) {
                            this.cancel();
                        }
                    }

                    i -= 1;
                }
            }
        }.runTaskTimer(plugin, 0L, 40L);
    }
}
