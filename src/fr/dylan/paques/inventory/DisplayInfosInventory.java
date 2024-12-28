package fr.dylan.paques.inventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import fr.dylan.paques.api.event.eggEvent.EasterRemoveEggEvent;
import fr.dylan.paques.api.event.lootEvent.EasterRemoveLootEvent;
import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.inventory.type.InventoryType;
import fr.dylan.paques.manager.EasterManager;
import fr.dylan.paques.objects.EggObject;
import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.utils.Utils;
import fr.dylan.paques.utils.inventory.FastInv;
import fr.dylan.paques.utils.inventory.ItemBuilder;

public class DisplayInfosInventory extends FastInv{

	private EasterManager paquesManager = EasterManager.getINSTANCE();
	
	public DisplayInfosInventory(Player player, InventoryType type, EggRarity rarity) {
		super((9*5), "§dDisplay Informations Inventory");
		
		if(type == InventoryType.EGGS)
		{
			for(int i = 0; i< Utils.getRarityEgg(rarity).size(); i++)
			{
				EggObject egg = Utils.getRarityEgg(rarity).get(i);
				setItem(i, new ItemBuilder(Material.CHEST)
						.name("§aLocation§7:§2 §6X§7:§e" + egg.getEggLocation().getBlockX() + "§7, §6Y§7:§e " + egg.getEggLocation().getBlockY() + "§7, §6Z§7:§e " + egg.getEggLocation().getBlockZ())
						.addLore("", "§cRight click to delete this §dEggs")
						.build(), e ->{
							EasterRemoveEggEvent removeEggEvent = new EasterRemoveEggEvent(player, egg.getEggLocation(), egg.getEggRarity());
							removeEggEvent.call();
							if(removeEggEvent.isCancelled()) {return;}
							
							paquesManager.getEggsList().remove(egg);
							player.sendMessage("§cYou have just deleted an "+egg.getEggRarity().rarityName+" §c​​rarity egg");
							
							player.closeInventory();
							new DisplayInfosInventory(player, type, rarity).open(player);

						});
			}
		}
		else if(type == InventoryType.LOOTS)
		{
			for(int i = 0; i< Utils.getRarityLoots(rarity).size(); i++)
			{
				LootsObject loot = Utils.getRarityLoots(rarity).get(i);
				
				if(loot.value == 0)
				{
					setItem(i, new ItemBuilder(Material.PAPER)
							.name("§aCommand§7:§2 " + loot.getCommand())
							.addLore("", "§7§lPercentage§7:§e " + loot.getPourcent(),"§cRight click to delete this §7Loots")
							.build(), e ->{
								
								EasterRemoveLootEvent removeLootEvent = new EasterRemoveLootEvent(player, loot);
								removeLootEvent.call();
								if(removeLootEvent.isCancelled()) {return;}
								
								paquesManager.loots.remove(loot);
								player.sendMessage("§cYou have just deleted a loot of rarity " + loot.getRarity().rarityName);
								
								player.closeInventory();
								new DisplayInfosInventory(player, type, rarity).open(player);
							});
				}
				else if(loot.value == 1)
				{
					String displayName = loot.getItemStack().toString();
					List<String> lores = Lists.newArrayList();
					
					if(loot.getItemStack().hasItemMeta() && loot.getItemStack().getItemMeta().hasDisplayName())
						displayName = loot.getItemStack().getItemMeta().getDisplayName();
					
					setItem(i, new ItemBuilder(loot.getItemStack().getType())
							.name("§aName§7:§2 " + displayName)
							.addLore("", "§7§lPercentage§7:§e " + loot.getPourcent(),"§cRight click to delete this §7Loots")
							.build(), e ->{
								paquesManager.loots.remove(loot);
								player.sendMessage("§cYou have just deleted a loot of rarity " + loot.getRarity().rarityName);
								
								player.closeInventory();
								new DisplayInfosInventory(player, type, rarity).open(player);
							});
				}
			}
		}
		
	}

}
