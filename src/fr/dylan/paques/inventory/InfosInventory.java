package fr.dylan.paques.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.dylan.paques.inventory.type.InventoryType;
import fr.dylan.paques.utils.inventory.FastInv;
import fr.dylan.paques.utils.inventory.ItemBuilder;

public class InfosInventory extends FastInv
{

	public InfosInventory(Player player) {
		super((9*3), "§dPaques Informations");
		
		setItems(getBorders(), new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").build());
		
		setItem(12, new ItemBuilder(Material.DIAMOND).name("§6Loots Informations").build(), e ->{
			new RarityChoiceInventory(player, InventoryType.LOOTS).open(player);
		});
		setItem(14, new ItemBuilder(Material.CHEST).name("§dEggs Informations").build(), e ->{
			new RarityChoiceInventory(player, InventoryType.EGGS).open(player);
		});
	}
	
}
