package me.danjono.inventoryrollback.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.danjono.inventoryrollback.config.ConfigFile;
import me.danjono.inventoryrollback.data.LogType;
import me.danjono.inventoryrollback.inventory.SaveInventory;

public class EventLogs implements Listener {

	@EventHandler
	private void playerJoin(PlayerJoinEvent e) {
		if (!ConfigFile.enabled) return;

		Player player = e.getPlayer();
		if (player.hasPermission("inventoryrollback.joinsave")) {			
			new SaveInventory(e.getPlayer(), LogType.JOIN, null, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

	@EventHandler
	private void playerQuit(PlayerQuitEvent e) {
		if (!ConfigFile.enabled) return;

		Player player = e.getPlayer();

		if (player.hasPermission("inventoryrollback.leavesave")) {				
			new SaveInventory(e.getPlayer(), LogType.QUIT, null, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

	@EventHandler
	private void playerDeath(PlayerDeathEvent e) {
		if (!ConfigFile.enabled) return;
		Player player = e.getEntity();
		EntityDamageEvent ede = player.getLastDamageCause();
		DamageCause cause = ede != null ? ede.getCause() : null;

		if (player.hasPermission("inventoryrollback.deathsave")) {
			new SaveInventory(player, LogType.DEATH, cause, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

	@EventHandler
	private void playerChangeWorld(PlayerChangedWorldEvent e) {
		if (!ConfigFile.enabled) return;

		Player player = e.getPlayer();

		if (player.hasPermission("inventoryrollback.worldchangesave")) {				
			new SaveInventory(e.getPlayer(), LogType.WORLD_CHANGE, null, player.getInventory(), player.getEnderChest()).createSave();
		}
	}

}
