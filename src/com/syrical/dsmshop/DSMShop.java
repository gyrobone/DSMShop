package com.syrical.dsmshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

//import com.syrical.dsmecon.PlayerData;
//import com.syrical.dsmshop.buymenus.OreBuy;

public class DSMShop extends JavaPlugin implements Listener {

	private ShopMenu shopMenu;
	private ShopData shopData;
	//private BuyMenu buyMenu;
	//private OreBuy oreBuy;
	//private SellMenu sellMenu;
	//private PlayerData pData;
	
	@Override
	public void onEnable() {
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "DSMShop has been enabled");
		shopMenu = new ShopMenu(this);
		this.shopData = new ShopData(this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "DSMShop has been enabled");
	}
	
public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			String lowerCmd = cmd.getName().toLowerCase();
			
			switch (lowerCmd) {
			
				case "shop":
					shopMenu.show(player);
					return true;
				
				case "checkprice":
					if(args.length == 0) {
						player.sendMessage("Invalid Arguments");
						return false;
					}
					String world = args[0];
					String item = args[1].toLowerCase();
					shopData.pullSellPrice(world, item, player);
					shopData.pullBuyPrice(world, item, player);
					return true;
					
				default:
					return true;
			}
			
		}
		return true;
	}
	
}
