package com.syrical.dsmshop.buymenus;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.AbstractFile;

public class OreBuy extends AbstractFile implements Listener {
	
	private File playerFile = new File(plugin.getDataFolder(), "playerdata.yml");
	private FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
	
	private Inventory oreBuy;
	private ItemStack diamond;
	//coal, charcoal, clay, flint, ironingot, goldingot, diamond, emerald, quartz
	public OreBuy (Plugin plugin) {
		super(plugin, "shopdata.yml");
		oreBuy = Bukkit.getServer().createInventory(null, 54, "Buy Ores");
		diamond = createItem("diamond");
		
		oreBuy.setItem(0, diamond);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(oreBuy);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item) {
		Integer itemID = (Integer) config.get("default." + item + ".id");
		ItemStack i = new ItemStack(itemID);
		ItemMeta im = i.getItemMeta();
		im.setLore(Arrays.asList("Left click to buy 1", "Right click to buy 32"));
		return i;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String world = p.getWorld().getName();
		String item = e.getCurrentItem().getType().name().toLowerCase();
		Integer buyPrice = (int) config.get(world + "." + item.toLowerCase() + ".buyprice");
		Integer credits = (int) playerConfig.get(p.getUniqueId().toString() + ".credits");
		if (e.getCurrentItem() == null) return;
		if(!e.getInventory().getName().equalsIgnoreCase(oreBuy.getName())) return;
		
		if(e.getCurrentItem().getType() == Material.DIAMOND) {
			if(e.isRightClick()) {
				e.setCancelled(true);
				if(credits < buyPrice) {
					p.sendMessage("You don't have enough credits");
					p.closeInventory();
					return;
				}
				p.chat("/removecredits " + (buyPrice * 32));
				p.chat("/balance");
				ItemStack diamonds = new ItemStack(Material.DIAMOND, 33);
				p.getInventory().addItem(diamonds);
			} else if (e.isLeftClick()){
				e.setCancelled(true);
				if(credits < buyPrice) {
					p.sendMessage("You don't have enough credits");
					p.closeInventory();
					return;
				}
				p.chat("/removecredits " + buyPrice);
				p.chat("/balance");
				ItemStack diamonds = new ItemStack(Material.DIAMOND, 2);
				p.getInventory().addItem(diamonds);
			}
			if(e.isShiftClick()) {
				p.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
			}
			p.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
			p.updateInventory();
			p.openInventory(oreBuy);
		}
		
	}
	
}
