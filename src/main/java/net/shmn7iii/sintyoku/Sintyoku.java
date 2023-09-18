package net.shmn7iii.sintyoku;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Collection;
import java.util.Iterator;

public final class Sintyoku extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, true);
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            SyncAwardedCriteria(e.getPlayer(), player);
        }
    }

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            SyncAwardedCriteria(e.getPlayer(), player);
        }
    }

    @EventHandler
    public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            SyncAwardedCriteria(e.getPlayer(), player);
        }
    }

    public static void SyncAwardedCriteria(Player from, Player to) {
        Iterator<Advancement> it = Bukkit.getServer().advancementIterator();
        while (it.hasNext()) {
            Advancement advancement = it.next();
            AdvancementProgress fromAdvancementProgress = from.getAdvancementProgress(advancement);
            AdvancementProgress toAdvancementProgress = to.getAdvancementProgress(advancement);

            Collection<String> awardedCriteria = fromAdvancementProgress.getAwardedCriteria();
            for (String criteria : awardedCriteria) {
                toAdvancementProgress.awardCriteria(criteria);
            }
        }
    }
}
