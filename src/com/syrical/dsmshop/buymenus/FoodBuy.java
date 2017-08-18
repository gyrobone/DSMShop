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


public class FoodBuy extends AbstractFile implements Listener {
	
	private File playerFile = new File(plugin.getDataFolder(), "playerdata.yml");
	private FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
	
	private Inventory FoodBuy;
	private ItemStack cooked_beef, grilled_pork, bread, wheat, apple, cookie, carrot_item, potato_item, melon, pumpkin, sugar, seeds, melon_seeds, pumpkin_seeds, egg;
	public FoodBuy (Plugin plugin) {
		super(plugin, "shopdata.yml");
		FoodBuy = Bukkit.getServer().createInventory(null, 18, "Buy Food");
		
		cooked_beef = createItem("cooked_beef");
		grilled_pork = createItem("grilled_pork");
		bread = createItem("bread");
		wheat = createItem("wheat");
		apple = createItem("apple");
		cookie = createItem("cookie");
		carrot_item = createItem("carrot_item");
		potato_item = createItem("potato_item");
		melon = createItem("melon");
		pumpkin = createItem("pumpkin");
		sugar = createItem("sugar");
		seeds = createItem("seeds");
		melon_seeds = createItem("melon_seeds");
		pumpkin_seeds = createItem("pumpkin_seeds");
		egg = createItem("egg");
		
		FoodBuy.setItem(0, cooked_beef);
		FoodBuy.setItem(1, grilled_pork);
		FoodBuy.setItem(2, bread);
		FoodBuy.setItem(3, wheat);
		FoodBuy.setItem(4, apple);
		FoodBuy.setItem(5, cookie);
		FoodBuy.setItem(6, carrot_item);
		FoodBuy.setItem(7, potato_item);
		FoodBuy.setItem(8, melon);
		FoodBuy.setItem(9, pumpkin);
		FoodBuy.setItem(10, sugar);
		FoodBuy.setItem(11, seeds);
		FoodBuy.setItem(12, melon_seeds);
		FoodBuy.setItem(13, pumpkin_seeds);
		FoodBuy.setItem(14, egg);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(FoodBuy);
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
		if(!e.getInventory().getName().equalsIgnoreCase(FoodBuy.getName())) return;
		
		Player p = (Player) e.getWhoClicked();
		String world = p.getWorld().getName();
		String item = e.getCurrentItem().getType().name().toLowerCase();
		Material itemMat = e.getCurrentItem().getType();
		
		if(e.getCurrentItem().getType() == Material.WOOL) return;
		if(!e.getInventory().getName().equalsIgnoreCase(FoodBuy.getName())) return;
		
		Integer buyPrice = (int) config.get(world + "." + item.toLowerCase() + ".buyprice");
		Integer credits = (int) playerConfig.get(p.getUniqueId().toString() + ".credits");
		
		if(e.isRightClick()) {
			e.setCancelled(true);
			if(credits < buyPrice) {
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
		p.openInventory(FoodBuy);
	}	
}

