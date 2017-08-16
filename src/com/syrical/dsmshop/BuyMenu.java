package com.syrical.dsmshop;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.buymenus.OreBuy;

public class BuyMenu implements Listener {

	private Inventory buyInv;
	private ItemStack ores;
	private OreBuy oreBuy;
	
	public BuyMenu(Plugin plugin) {
		buyInv = Bukkit.getServer().createInventory(null, 9, "Buy Menu");
		ores = createItem(DyeColor.GREEN, "Buy Ores");
		
		oreBuy = new OreBuy(plugin);
		
		buyInv.setItem(0, ores);

		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack createItem(DyeColor dc, String name) {
		ItemStack i = new Wool(dc).toItemStack(1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList("Open " + name + " Menu"));
		i.setItemMeta(im);
		return i;
		
	}
	
	public void show (Player p) {
		p.openInventory(buyInv);
	}
	
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(!e.getInventory().getName().equalsIgnoreCase(buyInv.getName())) return;
		if(e.getCurrentItem().getItemMeta() == null) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Ores")) {
			//Buy Menu
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();
			oreBuy.show(p);
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell")) {
			//Sell Menu
			e.setCancelled(true);
			e.getWhoClicked().closeInventory();
		}
	}
	
}