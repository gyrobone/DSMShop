package com.syrical.dsmshop;

import org.bukkit.entity.Player;



public class ShopData extends AbstractFile {
	
	public ShopData(DSMShop main) {
		super(main, "shopdata.yml");
	}

	public void pullSellPrice (String world, String item, Player p) {
		p.sendMessage("The sell price of " + item + " on " + world + " is " + config.get(world + "." + item + ".sellprice"));
	}
	
	public void pullBuyPrice (String world, String item, Player p) {
		p.sendMessage("The buy price of " + item + " on " + world + " is " + config.get(world + "." + item + ".buyprice"));
	}
	
}
