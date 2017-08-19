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


public class OreBuy extends AbstractFile implements Listener {
	
	private File playerFile = new File(plugin.getDataFolder(), "playerdata.yml");
	private FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
	
	private Inventory oreBuy;
	private ItemStack coal, clay_ball, flint, redstone, iron_ingot, gold_ingot, diamond, emerald, quartz;
	
	public OreBuy (Plugin plugin) {
		super(plugin, "shopdata.yml");
		oreBuy = Bukkit.getServer().createInventory(null, 9, "Buy Ores");
		
		flint = createItem("flint");
		coal = createItem("coal");
		clay_ball = createItem("clay_ball");
		iron_ingot = createItem("iron_ingot");
		gold_ingot = createItem("gold_ingot");
		diamond = createItem("diamond");
		emerald = createItem("emerald");
		quartz = createItem("quartz");
		redstone = createItem("redstone");
		
		oreBuy.setItem(0, flint);
		oreBuy.setItem(1, coal);
		oreBuy.setItem(2, clay_ball);
		oreBuy.setItem(3, iron_ingot);
		oreBuy.setItem(4, gold_ingot);
		oreBuy.setItem(5, diamond);
		oreBuy.setItem(6, emerald);
		oreBuy.setItem(7, quartz);
		oreBuy.setItem(8, redstone);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(oreBuy);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item) {
		Integer itemID = (Integer) config.get("default." + item + ".id");
		
		Integer priceCarinus = (int) config.get("world_carinus." + item.toLowerCase() + ".buyprice");
		Integer priceDamara = (int) config.get("world_damara." + item.toLowerCase() + ".buyprice");
		Integer priceOpia3 = (int) config.get("world_opia3." + item.toLowerCase() + ".buyprice");
		Integer priceSirona = (int) config.get("world_sirona." + item.toLowerCase() + ".buyprice");
		
		ItemStack i = new ItemStack(itemID);
		ItemMeta im = i.getItemMeta();
		im.setLore(Arrays.asList("Carinus: " + priceCarinus + " credits", "Damara: " + priceDamara + " credits", "Opia 3: " + priceOpia3 + " credits", "Sirona: " + priceSirona + " credits"));
		i.setItemMeta(im);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		if(!e.getInventory().getName().equalsIgnoreCase(oreBuy.getName())) return;
		
		Player p = (Player) e.getWhoClicked();
		String world = p.getWorld().getName();
		String item = e.getCurrentItem().getType().name().toLowerCase();
		Material itemMat = e.getCurrentItem().getType();
		
		if(e.getCurrentItem().getType() == Material.WOOL) return;
		if(!e.getInventory().getName().equalsIgnoreCase(oreBuy.getName())) return;
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
		p.openInventory(oreBuy);
	}	
}

