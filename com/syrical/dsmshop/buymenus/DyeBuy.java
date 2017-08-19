package com.syrical.dsmshop.buymenus;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.AbstractFile;


public class DyeBuy extends AbstractFile implements Listener {
	
	private File playerFile = new File(plugin.getDataFolder(), "playerdata.yml");
	private FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
	
	private Inventory DyeBuy;
	private ItemStack black, white, red, orange, yellow, green, blue, purple, pink, gray, light_gray, brown, cyan, light_blue, lime_green;
	public DyeBuy (Plugin plugin) {
		super(plugin, "shopdata.yml");
		DyeBuy = Bukkit.getServer().createInventory(null, 18, "Buy Dyes");
		
		black = createItem("black", (byte) 0);
		white = createItem("white", (byte) 15);
		red = createItem("red", (byte) 1);
		orange = createItem("orange", (byte) 14);
		yellow = createItem("yellow", (byte) 11);
		green = createItem("green", (byte) 2);
		blue = createItem("blue", (byte) 4);
		purple = createItem("purple", (byte) 5);
		pink = createItem("pink", (byte) 9);
		gray = createItem("gray", (byte) 8);
		light_gray = createItem("light_gray", (byte) 7);
		brown = createItem("brown", (byte) 3);
		cyan = createItem("cyan", (byte) 6);
		light_blue = createItem("light_blue", (byte) 12);
		lime_green = createItem("lime_green", (byte) 10);
		
		DyeBuy.setItem(0, black);
		DyeBuy.setItem(1, white);
		DyeBuy.setItem(2, red);
		DyeBuy.setItem(3, orange);
		DyeBuy.setItem(4, yellow);
		DyeBuy.setItem(5, green);
		DyeBuy.setItem(6, blue);
		DyeBuy.setItem(7, purple);
		DyeBuy.setItem(8, pink);
		DyeBuy.setItem(9, gray);
		DyeBuy.setItem(10, light_gray);
		DyeBuy.setItem(11, brown);
		DyeBuy.setItem(12, cyan);
		DyeBuy.setItem(13, light_blue);
		DyeBuy.setItem(14, lime_green);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(DyeBuy);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item, Byte id) {
		ItemStack i = new ItemStack(351, 1, (byte) id);
		ItemMeta im = i.getItemMeta();
		im.setLore(Arrays.asList("Left Click to Buy 1", "Right Click to Buy 32"));
		i.setItemMeta(im);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		if(!e.getInventory().getName().equalsIgnoreCase(DyeBuy.getName())) return;
		
		Player p = (Player) e.getWhoClicked();
		String item = e.getCurrentItem().getType().name().toLowerCase();
		MaterialData itemData = e.getCurrentItem().getData();
		Byte color = itemData.getData();
		Material itemMat = e.getCurrentItem().getType();
		p.sendMessage(item);
		
		if(e.getCurrentItem().getType() == Material.WOOL) return;
		if(!e.getInventory().getName().equalsIgnoreCase(DyeBuy.getName())) return;
		if(config.get("default." + item.toLowerCase()) == null) {
			e.setCancelled(true);
			return;
		}
		
		Integer credits = (int) playerConfig.get(p.getUniqueId().toString() + ".credits");
		
		if(e.isRightClick()) {
			e.setCancelled(true);
			if(credits < 32) {
				p.sendMessage(ChatColor.RED + "You don't have enough credits");
				p.closeInventory();
				return;
			}
			p.chat("/removecredits " + 32);
			p.chat("/balance");
			ItemStack items = new ItemStack(itemMat, 33, color);
			p.getInventory().addItem(items);
		} else if (e.isLeftClick()){
			e.setCancelled(true);
			if(credits < 1) {
				p.sendMessage(ChatColor.RED + "You don't have enough credits");
				p.closeInventory();
				return;
			}
			p.chat("/removecredits " + 1);
			p.chat("/balance");
			ItemStack items = new ItemStack(itemMat, 2, color);
			p.getInventory().addItem(items);
		}
		
		if(e.isShiftClick()) {
			p.getInventory().removeItem(new ItemStack(itemMat,1,color));
		}
		
		p.getInventory().removeItem(new ItemStack(itemMat,1,color));
		p.updateInventory();
		p.openInventory(DyeBuy);
	}	
}

