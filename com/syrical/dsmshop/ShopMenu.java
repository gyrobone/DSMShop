package com.syrical.dsmshop;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

public class ShopMenu implements Listener {

	private Inventory inv;
	private ItemStack buy, sell;
	private SellMenu sellMenu;
	private BuyMenu buyMenu;
	
	public ShopMenu(Plugin plugin) {
		inv = Bukkit.getServer().createInventory(null, 9, "Buy or Sell Items");
		buy = createItem(DyeColor.GREEN, "Buy");
		sell = createItem(DyeColor.YELLOW, "Sell");
		
		buyMenu = new BuyMenu(plugin);
		sellMenu = new SellMenu(plugin);
		
		inv.setItem(3, buy);
		inv.setItem(5, sell);
		
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
		p.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		if(!e.getInventory().getName().equalsIgnoreCase(inv.getName())) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy")) {
			//Buy Menu
			e.setCancelled(true);
			buyMenu.show(p);
		}
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell")) {
			//Sell Menu
			e.setCancelled(true);
			sellMenu.show(p);
		} else {
			e.setCancelled(true);
		}
	}
	
}
