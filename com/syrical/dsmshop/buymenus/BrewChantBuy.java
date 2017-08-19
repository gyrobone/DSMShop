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
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.AbstractFile;


public class BrewChantBuy extends AbstractFile implements Listener {
	
	private File playerFile = new File(plugin.getDataFolder(), "playerdata.yml");
	private FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
	
	private Inventory BrewChantBuy;
	private ItemStack obsidian, book, blaze_rod, glowstone_dust, sulphur, nether_stalk, ender_pearl, spider_eye, ghast_tear;
	public BrewChantBuy (Plugin plugin) {
		super(plugin, "shopdata.yml");
		BrewChantBuy = Bukkit.getServer().createInventory(null, 9, "Buy Brewing and Enchanting Items");
		
		obsidian = createItem("obsidian");
		book = createItem("book");
		glowstone_dust = createItem("glowstone_dust");
		sulphur = createItem("sulphur");
		nether_stalk = createItem("nether_stalk");
		ender_pearl = createItem("ender_pearl");
		spider_eye = createItem("spider_eye");
		ghast_tear = createItem("ghast_tear");
		blaze_rod = createItem("blaze_rod");
		
		BrewChantBuy.setItem(0, obsidian);
		BrewChantBuy.setItem(1, book);
		BrewChantBuy.setItem(2, glowstone_dust);
		BrewChantBuy.setItem(3, sulphur);
		BrewChantBuy.setItem(4, nether_stalk);
		BrewChantBuy.setItem(5, ender_pearl);
		BrewChantBuy.setItem(6, spider_eye);
		BrewChantBuy.setItem(7, ghast_tear);
		BrewChantBuy.setItem(8, blaze_rod);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(BrewChantBuy);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item) {
		Integer itemID = (Integer) config.get("default." + item + ".id");
		ItemStack i = new ItemStack(itemID);
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
		if(!e.getInventory().getName().equalsIgnoreCase(BrewChantBuy.getName())) return;
		
		Player p = (Player) e.getWhoClicked();
		String world = p.getWorld().getName();
		String item = e.getCurrentItem().getType().name().toLowerCase();
		Material itemMat = e.getCurrentItem().getType();
		
		if(e.getCurrentItem().getType() == Material.WOOL) return;
		if(!e.getInventory().getName().equalsIgnoreCase(BrewChantBuy.getName())) return;
		if(config.get("default." + item.toLowerCase()) == null) {
			e.setCancelled(true);
			return;
		}
		Integer buyPrice = (int) config.get(world + "." + item.toLowerCase() + ".buyprice");
		Integer credits = (int) playerConfig.get(p.getUniqueId().toString() + ".credits");
		
		if(e.isRightClick()) {
			e.setCancelled(true);
			if(credits < buyPrice * 32) {
				p.sendMessage(ChatColor.RED + "You don't have enough credits");
				p.closeInventory();
				return;
			}
			p.chat("/removecredits " + (buyPrice * 32));
			p.chat("/balance");
			ItemStack items = new ItemStack(itemMat, 33);
			p.getInventory().addItem(items);
		} else if (e.isLeftClick()){
			e.setCancelled(true);
			if(credits < buyPrice) {
				p.sendMessage(ChatColor.RED + "You don't have enough credits");
				p.closeInventory();
				return;
			}
			p.chat("/removecredits " + buyPrice);
			p.chat("/balance");
			ItemStack items = new ItemStack(itemMat, 2);
			p.getInventory().addItem(items);
		}
		
		if(e.isShiftClick()) {
			p.getInventory().removeItem(new ItemStack(itemMat,1));
		}
		
		p.getInventory().removeItem(new ItemStack(itemMat,1));
		p.updateInventory();
		p.openInventory(BrewChantBuy);
	}	
}

